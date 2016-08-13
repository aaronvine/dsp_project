import org.apache.hadoop.io.*;
import java.io.File;
import java.util.*;

public class Common {

    public static final int NUM_OF_REDUCERS = 1;

    public static final Set<String> nounTags = new HashSet<String>(Arrays.asList(new String[]{"nn", "nns", "nnp", "nnps", "NN", "NNS", "NNP", "NNPS"}));

}
