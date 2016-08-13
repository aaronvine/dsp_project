package mappers;
import org.apache.commons.codec.language.Soundex;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class SecondMapper extends Mapper<LongWritable, Text, NounPair, Text> {
   
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (value.toString().matches("([^\\t]*)\\t([^\\t]*)\\t(True|False)")) {
            String[] splitted = value.toString().split("\t");
            NounPair np = new NounPair(splitted[0], splitted[1], Boolean.valueOf(splitted[2]));

            context.write(np, new Text(splitted[2]));
        } else {
            // Does nothing - parse and pass to reducer
            String[] splitted = value.toString().split("\t");
            String[] words = splitted[0].split(",");

            NounPair np = new NounPair(words[0], words[1], 0);

            context.write(np, new Text(splitted[1]));
        }
    }
}
