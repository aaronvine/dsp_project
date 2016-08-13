package utils;
import org.apache.hadoop.io.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PatternWritable implements Writable {

    public IntWritable hashCode;
    public PairWritable pair;
    public int appearances;
    public int idx;

    public PatternWritable(IntWritable hashCode, PairWritable pair) {
        this.hashCode = hashCode;
        this.pair = pair;
    }

    public PatternWritable(IntWritable hashCode, int appearances) {
        this.hashCode = hashCode;
        this.appearances = appearances;
    }


    public String toString(){
        String string = hashCode.toString() + ",";
        if (pair != null) {
            string += pair.toString();
        }
        string += "," + appearances + "," + idx;
        return string;
    }

    public void write(DataOutput dataOutput) throws IOException {
    }

    public void readFields(DataInput dataInput) throws IOException {
    }
}
