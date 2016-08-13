import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.rules.ZeroR;
import weka.core.Attribute;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class WekaClassifier {

    public static void main(String[] args) throws Exception {

        List<WekaTuple> wekaTupleList = new ArrayList<WekaTuple>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("output/part-r-00000")))) {
            String line;
            while ((line = br.readLine()) != null) {
                wekaTupleList.add(new WekaTuple(line));
            }
        }

        ArrayList splitTupleList = (ArrayList)splitData(wekaTupleList, wekaTupleList.size() / 10);
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("isHypernym", 0));
        int attributesNum = 0;

        for (WekaTuple wekaTuple: wekaTupleList) {
            attributesNum = Math.max(attributesNum, wekaTuple.getMaxIndex());
        }

        for (int i = 1; i < attributesNum; i++) {
            attributes.add(new Attribute("pattern" + i, i));
        }
        crossFold(splitTupleList, attributes);
    }

    private static <T> List<List<T>> splitData(List<T> wekaTupleList, int newDataSize) {
        List<List<T>> newDataList = new ArrayList<>();
        final int listSize = wekaTupleList.size();

        for (int i = 0; i < listSize; i += newDataSize) {
            if (newDataList.size() == 10) {
                break;
            }
            newDataList.add(new ArrayList<>(wekaTupleList.subList(i, Math.min(listSize, i + newDataSize)))
            );
        }
        return newDataList;
    }

    private static void crossFold(List<List<WekaTuple>> wekaTupleLists, ArrayList<Attribute> attributes) throws Exception {
        PrintWriter writer = new PrintWriter("output.txt", "UTF-8");

        for (List<WekaTuple> curWekaTupleList: wekaTupleLists) {
            Instances trainSet = new Instances("trainSet", attributes, curWekaTupleList.size() * 9);
            Instances testSet = new Instances("testSet", attributes, curWekaTupleList.size());
            trainSet.setClassIndex(0);
            testSet.setClassIndex(0);

            for (List<WekaTuple> wekaTupleList: wekaTupleLists) {
                if (wekaTupleList == curWekaTupleList) {
                    break;
                }

                for (WekaTuple wekaTuple : wekaTupleList) {
                    trainSet.add(wekaTuple.getPatterns());
                }
            }

            for (WekaTuple wekaTuple: curWekaTupleList) {
                testSet.add(wekaTuple.getPatterns());
            }

            Classifier classifier = new ZeroR();
            classifier.buildClassifier(trainSet);
            classifier.buildClassifier(testSet);

            Evaluation evaluation = new Evaluation(trainSet);
            evaluation.evaluateModel(classifier, testSet);

            String evaluationSummary = evaluation.toSummaryString();
            writer.write(evaluationSummary);
            writer.write("FMeasure: " + evaluation.fMeasure(0) + "Precision: " + evaluation.precision(0) + "Recall: " + evaluation.recall(0));
        }
        writer.close();
    }
}
