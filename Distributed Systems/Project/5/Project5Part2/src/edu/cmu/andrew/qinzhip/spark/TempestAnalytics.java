// Qinzhi Peng, qinzhip
package edu.cmu.andrew.qinzhip.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.Scanner;

public class TempestAnalytics {

    private static void tempestAnalytics(String fileName) {

        // configure spark
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("JD Tempest Analytics");

        // create a JavaSparkContext that loads settings from system properties
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        // read an input text file to RDD
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);

        // split each line
        JavaRDD<String> linesFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("\n")));

        // count number of lines
        long countLines = linesFromFile.count();
        System.out.println("Number of lines: " + countLines);

        // flatMap each line to words in the line
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("[^a-zA-Z]+")));

        // filter the words which are empty
        Function<String, Boolean> filter = k -> (!k.isEmpty());

        // count number of words
        long countWords = wordsFromFile.filter(filter).count();
        System.out.println("Number of words: " + countWords);

        // count number of distinct words
        long countDistinctWords = wordsFromFile.distinct().filter(filter).count();
        System.out.println("Number of distinct words: " + countDistinctWords);

        // flatMap each line to symbols in the line
        JavaRDD<String> symbolsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("")));

        // count number of symbols
        long countSymbols = symbolsFromFile.count();
        System.out.println("Number of symbols: " + countSymbols);

        // count number of distinct symbols
        long countDistinctSymbols = symbolsFromFile.distinct().count();
        System.out.println("Number of distinct symbols: " + countDistinctSymbols);

        // filter the symbols which are not letter
        Function<String, Boolean> filter2 = k -> (k.toLowerCase().matches("[a-z]"));

        // count number of distinct letters
        long countDistinctLetters = symbolsFromFile.distinct().filter(filter2).count();
        System.out.println("Number of distinct letters: " + countDistinctLetters);

        // ask the user to enter a word
        System.out.println("Please input a word: ");
        Scanner token = new Scanner(System.in);
        String input = token.nextLine();

        // filter the lines which do not contain the word
        Function<String, Boolean> filter3 = k -> (k.contains(input));

        // show all of the lines of The Tempest that contain that word
        JavaRDD<String> searchFromFile = linesFromFile.filter(filter3);

        for (String line : searchFromFile.collect()) {
            System.out.println(line);
        }

    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No files provided.");
            System.exit(0);
        }

        tempestAnalytics(args[0]);
    }
}