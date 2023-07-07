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


public class OaklandCrimeStatsKml extends Configured implements Tool {

    public static class OaklandCrimeStatsKmlMap extends Mapper<LongWritable, Text, Text, Text> {


        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            //use \t to split the line
            String[] tokenizer = line.split("\t");
            if (tokenizer[4].equals("AGGRAVATED ASSAULT")) {
                // use Pythagorean theorem to decide if aggravated assault occurred within 200 meters of 3803 Forbes Avenue
                double distance_pow = Math.pow(Double.parseDouble(tokenizer[0]) - 1354326.897, 2) + Math.pow(Double.parseDouble(tokenizer[1]) - 411447.7828, 2);
                // convert feet to meters
                if ((Math.sqrt(distance_pow) * 0.3048) <= 300) {
                    // set key as concat of latitude and longitude
                    context.write(new Text(), new Text(tokenizer[8] + "," + tokenizer[7]));
                }
            }
        }
    }

    public static class OaklandCrimeStatsKmlReducer extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            // output kml format
            context.write(new Text("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<kml xmlns=\"http://www.opengis.net/kml/2.2\">"), new Text());
            for (Text value : values) {
                context.write(new Text(" <Placemark><name>aggravated assault crimes</name><Point><coordinates>"), new Text());
                context.write(value, new Text());
                context.write(new Text(" </coordinates></Point></Placemark>"), new Text());
            }
            context.write(new Text("</kml>"), new Text());
        }
    }

    public int run(String[] args) throws Exception {

        Job job = new Job(getConf());
        job.setJarByClass(OaklandCrimeStatsKml.class);
        job.setJobName("Oakland Crime Stats Kml");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(OaklandCrimeStatsKmlMap.class);
        job.setReducerClass(OaklandCrimeStatsKmlReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new OaklandCrimeStatsKml(), args);
        System.exit(result);
    }

}