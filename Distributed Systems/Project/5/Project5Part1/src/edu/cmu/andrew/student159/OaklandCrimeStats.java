// Qinzhi Peng, qinzhip
package edu.cmu.andrew.student159;

import java.io.IOException;
import java.lang.Math;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.*;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class OaklandCrimeStats extends Configured implements Tool {

    public static class OaklandCrimeStatsMap extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            //use \t to split the line
            String[] tokenizer = line.split("\t");
            if (tokenizer[4].equals("AGGRAVATED ASSAULT")) {
                // use Pythagorean theorem to decide if aggravated assault occurred within 200 meters of 3803 Forbes Avenue
                double distance_pow = Math.pow(Double.parseDouble(tokenizer[0]) - 1354326.897, 2) + Math.pow(Double.parseDouble(tokenizer[1]) - 411447.7828, 2);
                // convert feet to meters
                if ((Math.sqrt(distance_pow) * 0.3048) <= 200) {
                    context.write(new Text("count"), one);
                }
            }
        }
    }

    public static class OaklandCrimeStatsReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            context.write(key, new IntWritable(sum));
        }

    }

    public int run(String[] args) throws Exception {

        Job job = new Job(getConf());
        job.setJarByClass(OaklandCrimeStats.class);
        job.setJobName("Oakland Crime Stats");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(OaklandCrimeStatsMap.class);
        job.setReducerClass(OaklandCrimeStatsReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new OaklandCrimeStats(), args);
        System.exit(result);
    }

}