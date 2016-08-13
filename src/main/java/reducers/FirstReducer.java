package reducers;
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
public class FirstReducer extends Reducer<IntWritable, NounPair, NounPair, IndexCount> {

    int dpmin = 10;
    int reducerNum = -1;
    int index = -1;

    public void reduce(IntWritable key, Iterable<NounPair> values, Context context) throws IOException, InterruptedException {
        if (reducerNum == -1){
            reducerNum = key.get() % Utils.NUM_OF_REDUCERS;
            index = reducerNum;
        }


        ArrayList<NounPair> cache = new ArrayList<NounPair>();

        int patternTimes = 0;

        // Start iterate over the iterator

        for (NounPair value : values) {
            // write values to cache and count occurrences
            cache.add(value);
            patternTimes += value.getOccurrences().get();
            // in case there are more than DPmin, break
            if(patternTimes > dpmin){
                break;
            }
        }

        // in case there is less than DPmin, return
        if(patternTimes<dpmin){
            return;
        }

        // If we got here, there are more than DPmin occurrences, write cache
        for (NounPair np: cache){
            context.write(np, new IndexCount(index, np.getOccurrences().get()));
        }

        // Write the rest of the iterator
        for (NounPair np : values) {
            context.write(np, new IndexCount(index, np.getOccurrences().get()));
        }

        // increment the index of the pattern
        index += Utils.NUM_OF_REDUCERS;
    }
}
