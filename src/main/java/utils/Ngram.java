package utils;
public class Ngram {

    public String word;
    public String posTag;
    public String depLabel;
    public int headIdx;
    public String label;

    public Ngram(String str) {
        String[] splitted = str.split("/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < splitted.length - 3; i++) {
            sb.append(splitted[i]).append("/");
        }
        sb.setLength(sb.length() - 1);
        this.word = sb.toString();
        this.posTag = splitted[splitted.length - 3];
        this.depLabel = splitted[splitted.length - 2];
        this.headIdx = Integer.parseInt(splitted[splitted.length - 1]);
        this.label = posTag + "-" + depLabel;
    }

    public String toString() {
        return word + "," + posTag + "," + depLabel + "," + headIdx;
    }
}
