import weka.core.Instance;
import weka.core.SparseInstance;

class WekaTuple {
    public String pair;
    public String isHypernym;
    public Instance patterns;
    public int maxIdx = 0;

    WekaTuple(String tuple) {
        String[] splitted = tuple.split("\t");
        this.pair = splitted[0];
        this.isHypernym = splitted[1];
        this.patterns = new SparseInstance(24000);

        if (this.isHypernym.equals("true")) {
            this.patterns.setValue(0, 1);
        } else {
            this.patterns.setValue(0, 0);
        }

        String[] idxAppearances = splitted[2].replaceAll("(", "").split(")");

        for (String ia : idxAppearances) {
            String[] idxAppearance = ia.split(",");
            int idx = Integer.parseInt(idxAppearance[0] + 1);
            int appearances = Integer.parseInt(idxAppearance[1]);
            this.maxIdx = Math.max(maxIdx, idx);
            patterns.setValue(idx, appearances);
        }
    }
}
