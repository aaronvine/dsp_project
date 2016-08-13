package reducers;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import drivers.Main;
import utils.IdxAppearancesWritable;
import utils.PairWritable;
public class FirstReducer extends Reducer<IntWritable, PairWritable, PairWritable, IdxAppearancesWritable> {

    int dpmin = 10;
    int reducerNum = -1;
    int index = -1;

    public void reduce(IntWritable key, Iterable<PairWritable> values, Context context) throws IOException, InterruptedException {
        if (reducerNum == -1){
            reducerNum = key.get() % Main.NUM_OF_REDUCERS;
            index = reducerNum;
        }


        ArrayList<PairWritable> cache = new ArrayList<PairWritable>();

        int patternTimes = 0;

        // Start iterate over the iterator

        for (PairWritable value : values) {
            // write values to cache and count occurrences
            cache.add(value);
            patternTimes += value.numOfAppearances.get();
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
        for (PairWritable np: cache){
            context.write(np, new IdxAppearancesWritable(index, np.numOfAppearances.get()));
        }

        // Write the rest of the iterator
        for (PairWritable np : values) {
            context.write(np, new IdxAppearancesWritable(index, np.numOfAppearances.get()));
        }

        // increment the index of the pattern
        index += Main.NUM_OF_REDUCERS;
    }
}
