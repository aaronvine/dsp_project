package mappers;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.BiarcGraph;
import utils.PairWritable;
import utils.PatternWritable;
public class FirstMapper extends Mapper<LongWritable, Text, IntWritable, PairWritable> {
	
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        	BiarcGraph tree = new BiarcGraph(value);
            if (tree.getPatterns() == null) {
                return;
            }

            for (PatternWritable pattern : tree.getPatterns()) {
                context.write(new IntWritable(pattern.hashCode()), pattern.pair);
            }
        }
    }

