package drivers;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;

public class Main {
	

	public static void main(String[] args) throws Exception {
		System.out.println("hi! =]");
	}
	public static Job initFirstJob(String in, String out) throws IllegalArgumentException, IOException {
//		System.out.println("initializing first job..");
		Configuration conf = new Configuration();
		Job job = new Job(conf, "job1");
//		job.setJarByClass(Main.class);
//		job.setMapperClass(WordCountMapper.class);
//		job.setCombinerClass(LongSumReducer.class);
//		job.setReducerClass(LongSumReducer.class);
//		job.setMapOutputKeyClass(WordsInDecadeWritable.class);
//		job.setMapOutputValueClass(LongWritable.class);
//		job.setOutputKeyClass(WordsInDecadeWritable.class);
//		job.setOutputValueClass(LongWritable.class);
//		job.setInputFormatClass(SequenceFileInputFormat.class);
//		FileInputFormat.addInputPath(job, new Path(in));
//		FileOutputFormat.setOutputPath(job, new Path(out));
//		System.out.println("first job created!");
		return job;
	}
}