import org.apache.hadoop.io.*;
import java.util.Vector;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class VectorWritable implements Writable {

    public Vector<Text> vector;

    public VectorWritable() {
        this.vector = new Vector<Text>();
    }

    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(vector.size());
        for (Text value : vector) {
            value.write(dataOutput);
        }
    }

    public void readFields(DataInput dataInput) throws IOException {
        int vectorSize = dataInput.readInt();
        vector = new Vector<Text>(size);
        for (int i = 0; i < size; i++) {
            Text value = new Text();
            value.readFields(dataInput);
            vector.add(value);
        }
    }

    public String toString(){
        String string = "<";
        for (Text value: vector) {
            string += value.toString();
        }
        return string + ">";
    }
}
