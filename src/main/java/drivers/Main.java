package drivers;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;

import mappers.FirstMapper;
import mappers.SecondMapper;
import utils.PairWritable;
import utils.VectorWritable;

public class Main {
	public static int NUM_OF_REDUCERS = 1;

	public static void main(String[] args) throws Exception {
		System.out.println("hi! =]");
		Job firstJob = initFirstJob(args[0], args[1] + "tmp1");
		firstJob.waitForCompletion(true);
		Job secondJob = initSecondJob(args[1] + "tmp1", args[1]);
		secondJob.waitForCompletion(true);
		System.exit(0);
	}
	
	
	public static Job initFirstJob(String in, String out) throws IllegalArgumentException, IOException {
		System.out.println("initializing first job..");
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job1");
		job.setJarByClass(Main.class);
		job.setMapperClass(FirstMapper.class);
		job.setCombinerClass(LongSumReducer.class);
		job.setReducerClass(LongSumReducer.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(PairWritable.class);
		job.setOutputKeyClass(PairWritable.class);
		job.setOutputValueClass(LongWritable.class);
		System.out.println("first job created!");
		return job;
	}
	public static Job initSecondJob(String in, String out) throws IllegalArgumentException, IOException {
		System.out.println("initializing first job..");
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job1");
		job.setJarByClass(Main.class);
		job.setMapperClass(SecondMapper.class);
		job.setMapOutputKeyClass(PairWritable.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(VectorWritable.class);
		System.out.println("Second job created!");
		return job;
	}
}