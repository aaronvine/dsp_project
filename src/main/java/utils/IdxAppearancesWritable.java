package utils;
import org.apache.hadoop.io.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IdxAppearancesWritable implements Writable {

    public IntWritable idx;
    public IntWritable numOfAppearances;

    public IdxAppearancesWritable() {
        this.idx = new IntWritable();
        this.numOfAppearances = new IntWritable();
    }

    public IdxAppearancesWritable(IntWritable idx, IntWritable numOfAppearances) {
        this.idx = idx;
        this.numOfAppearances = numOfAppearances;
    }

    public IdxAppearancesWritable(int idx, int numOfAppearances) {
        this(new IntWritable(idx), new IntWritable(numOfAppearances));
    }

    public void write(DataOutput dataOutput) throws IOException {
        idx.write(dataOutput);
        numOfAppearances.write(dataOutput);
    }

    public void readFields(DataInput dataInput) throws IOException {
        idx.readFields(dataInput);
        numOfAppearances.readFields(dataInput);
    }

    public String toString() {
        return "(" + this.idx + "," + this.numOfAppearances + ")";
    }
}
