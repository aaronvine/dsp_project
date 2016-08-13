package utils;
import org.apache.hadoop.io.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PairWritable implements Writable, WritableComparable<PairWritable> {

    public Text noun1;
    public Text noun2;
    public IntWritable numOfAppearances;
    public BooleanWritable value;

    public PairWritable() {
        this.noun1 = new Text();
        this.noun2 = new Text();
        this.numOfAppearances = new IntWritable(0);
        this.value = new BooleanWritable(false);
    }

    public PairWritable(String noun1, String noun2, int numOfAppearances) {
        this.noun1 = new Text(noun1);
        this.noun2 = new Text(noun2);
        this.numOfAppearances = new IntWritable(numOfAppearances);
        this.value = new BooleanWritable(false);
    }

    public PairWritable(String noun1, String noun2, Boolean value) {
        this.noun1 = new Text(noun1);
        this.noun2 = new Text(noun2);
        this.numOfAppearances = new IntWritable(-1);
        this.value = new BooleanWritable(value);
    }

    public void write(DataOutput dataOutput) throws IOException {
        noun1.write(dataOutput);
        noun2.write(dataOutput);
        numOfAppearances.write(dataOutput);
        value.write(dataOutput);
    }

    public void readFields(DataInput dataInput) throws IOException {
        noun1.readFields(dataInput);
        noun2.readFields(dataInput);
        numOfAppearances.readFields(dataInput);
        value.readFields(dataInput);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PairWritable pair = (PairWritable) o;
        if (numOfAppearances != pair.numOfAppearances) {
            return false;
        }
        if (noun1 != null ? !noun1.equals(pair.noun1) : pair.noun1 != null) {
            return false;
        }
        if (noun2 != null ? !noun2.equals(pair.noun2) : pair.noun2 != null) {
            return false;
        }
        return value == pair.value;
    }

    @Override
    public int hashCode() {
        int result = numOfAppearances.hashCode();
        result = 31 * result + (noun1 != null ? noun1.hashCode() : 0);
        result = 31 * result + (noun2 != null ? noun2.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public String toString() {
        return noun1 + "," + noun2;
    }


    public IntWritable getType() {
        if (numOfAppearances.get() < 0)
            return new IntWritable(1);
        else
            return new IntWritable(2);
    }
    @Override
    public int compareTo(PairWritable o) {
        int compareNoun1 = this.noun1.compareTo(o.noun1);
        if (compareNoun1 == 0) {
            int compareNoun2 = this.noun2.compareTo(o.noun2);
            if (compareNoun2 == 0) {
                if (this.getType().compareTo(o.getType()) == 0) {
                    return 0;
                } else {
                    if (this.getType().get() == 1) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
            return compareNoun2;
        } else {
            return compareNoun1;
        }
    }
}
