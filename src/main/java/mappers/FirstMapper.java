package mappers;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
public class FirstMapper extends Mapper<LongWritable, Text, IntWritable, NounPair> {
	
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            BiarcTree tree = new BiarcTree(value);
            if (tree.getPatterns() == null) {
                return;
            }

            for (Pattern pattern : tree.getPatterns()) {
                context.write(pattern.getHashCode(), pattern.getNp());
            }
        }
    }
}
