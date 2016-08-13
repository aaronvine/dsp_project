package mappers;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.PairWritable;

public class SecondMapper extends Mapper<LongWritable, Text, PairWritable, Text> {
   
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (value.toString().matches("([^\\t]*)\\t([^\\t]*)\\t(True|False)")) {
            String[] splitted = value.toString().split("\t");
            PairWritable np = new PairWritable(splitted[0], splitted[1], Boolean.valueOf(splitted[2]));

            context.write(np, new Text(splitted[2]));
        } else {
            // Does nothing - parse and pass to reducer
            String[] splitted = value.toString().split("\t");
            String[] words = splitted[0].split(",");

            PairWritable np = new PairWritable(words[0], words[1], 0);

            context.write(np, new Text(splitted[1]));
        }
    }
}
