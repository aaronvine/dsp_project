package reducers;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import utils.PairWritable;
import utils.VectorWritable;

public class SecondReducer extends Reducer<PairWritable, Text, Text, VectorWritable> {
    private String currentPair = null;
    private String currentPairValue = null;


    public void reduce(PairWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        
    	VectorWritable vw = new VectorWritable();

        for (Text ic : values) {

            // If this is a noun pair with value true \ false
            if(isBooleanNP(ic)) {
                // this is a new pair, update current pair and its value
                this.currentPair = key.toString();
                this.currentPairValue = key.value.toString();
                return;
            }

            // Else, if it is not the same pair as the currentPair - return
            if(! key.toString().equals(currentPair)){
                return;
            }

            // Else, it is the same key as current pair, add its value to vector
            vw.vector.addElement(new Text(ic));
        }

        // Write to context the key, its value and the vector
        context.write(new Text(key.toString() + "\t" + currentPairValue), vw);
    }
    private  boolean isBooleanNP(Text ic) {
        return ic.toString().equals("False") || ic.toString().equals("True");
    }
}
