// ======================= WordSearch.java ==========================================
// Qinzhi Peng, qinzhip
package org.myorg;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.util.*;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class WordSearch extends Configured implements Tool {

    public static class WordSearchMap extends Mapper<Object, Text, Text, NullWritable> {
        private Text word = new Text();

        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                String singleWord = tokenizer.nextToken();
                if (singleWord.toLowerCase().contains("fact")) {
                    word.set(singleWord);
                    context.write(word, NullWritable.get());
                }
            }
        }
    }

    // Source: https://github.com/adamjshook/mapreducepatterns/blob/master/MRDP/src/main/java/mrdp/ch3/DistinctUserDriver.java
    public static class WordSearchReducer extends Reducer<Text, NullWritable, Text, NullWritable> {

        @Override
        public void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            context.write(key, NullWritable.get());
        }
    }

    public int run(String[] args) throws Exception {

        Job job = new Job(getConf());
        job.setJarByClass(WordSearch.class);
        job.setJobName("wordsearch");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setMapperClass(WordSearchMap.class);
        job.setReducerClass(WordSearchReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new WordSearch(), args);
        System.exit(result);
    }

}
