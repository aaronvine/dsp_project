//import org.apache.hadoop.io.*;
//import org.jgrapht.alg.DijkstraShortestPath;
//import org.jgrapht.graph.SimpleDirectedGraph;
//import java.util.List;
//
//public class BiarcGraph {
//
//    private List<Ngram> ngrams;
//    private SimpleDirectedGraph<Ngram, Edge> graph;
//    private List<List<Edge>> paths;
//    private List<Pattern> patterns;
//    private int appearances;
//
//    public BiarcGraph(Text text) {
//        // init ngrams
//        String[] splitted = text.toString().split("\t");
//        ngrams = Utils.parseNgrams(splitted[1]);
//
//        this.occurrences = Integer.parseInt(splitted[2]);
//
//        // init paths
//        this.paths = new ArrayList<List<MyEdge>>();
//
//
//        // build graph
//        buildGraph(ngrams);
//
//        //extract noun vertices
//        List<SyntacticNgram> nounsVertices = extractNounsVertices();
//
//        // if less than 2 nouns, return
//        if (nounsVertices.size() < 2) {
//            return;
//        }
//
//        // for each pair of noun find path if exists
//        for (int i = 0; i < nounsVertices.size(); i++) {
//            for (int j = 0; j < nounsVertices.size(); j++) {
//                // same vertex doesn't count, continue
//                if(i==j){
//                    continue;
//                }
//                // find paths if exists
//                List<MyEdge> path = DijkstraShortestPath.findPathBetween(graph, nounsVertices.get(i), nounsVertices.get(j));
//
//                // No path found
//                if (path == null) {
//                    continue;
//                }
//
//                // path found, add to paths
//                paths.add(path);
//            }
//        }
//
//        this.patterns = extractPatterns();
//    }
//
//    private List<Pattern> extractPatterns() {
//        List<Pattern> patterns = new ArrayList<Pattern>();
//        // for each path, represent it as pos-label, dep-label
//        for(List<MyEdge> path: paths){
//            extractNounsFromPath(path);
//            Pattern p = new Pattern(extractPathHashCode(path), extractNounsFromPath(path));
//            patterns.add(p);
//        }
//        return patterns;
//    }
//
//
//    /**
//     * Given a path return its hash code
//     * @param path
//     * @return
//     */
//    private IntWritable extractPathHashCode(List<MyEdge> path) {
//        StringBuilderWriter sb = new StringBuilderWriter();
//
//        for (MyEdge e: path){
//            sb.append(((SyntacticNgram)e.getSource()).getLabel());
//        }
//        sb.append(((SyntacticNgram)path.get(path.size() -1).getTarget()).getLabel());
//
//        return new IntWritable(sb.toString().hashCode());
//    }
//
//    /**
//     * Given path return noun pair of stemmed nouns
//     * @param path
//     * @return
//     */
//    private NounPair extractNounsFromPath(List<MyEdge> path) {
//        String firstNoun = ((SyntacticNgram)path.get(0).getSource()).getWord();
//        String secondNoun = ((SyntacticNgram)path.get(path.size() - 1).getTarget()).getWord();
//
//        Stemmer stemmer = new Stemmer();
//
//        stemmer.add(firstNoun.toCharArray(),  firstNoun.length());
//        stemmer.stem();
//        String stemmedFirstNoun = stemmer.toString();
//
//        stemmer.add(secondNoun.toCharArray(),  secondNoun.length());
//        stemmer.stem();
//        String stemmedSecondNoun = stemmer.toString();
//
//        return new NounPair(stemmedFirstNoun, stemmedSecondNoun, occurrences);
//    }
//
//
//    /**
//     * given ngrams build graph represent the dependencies
//     * @param ngrams
//     */
//    private void buildGraph(List<SyntacticNgram> ngrams) {
//        // init graph
//        graph = new SimpleDirectedGraph<SyntacticNgram, MyEdge>(MyEdge.class);
//
//        // add vertices
//        for (SyntacticNgram ngram : ngrams) {
//            graph.addVertex(ngram);
//        }
//
//        // add edges
//        for (SyntacticNgram ngram : ngrams) {
//            // root, not pointing to anywhere
//            if (ngram.getHeadIndex() == 0) {
//                continue;
//            }
//            // add edge
//            graph.addEdge(ngrams.get(ngram.getHeadIndex() - 1), ngram);
//        }
//    }
//
//
//    /**
//     * get vertices which represent a noun
//     * @return
//     */
//    private ArrayList<SyntacticNgram> extractNounsVertices() {
//        ArrayList<SyntacticNgram> allGrams = new ArrayList<SyntacticNgram>(graph.vertexSet());
//        ArrayList<SyntacticNgram> nounGrams = new ArrayList<SyntacticNgram>();
//
//        for (SyntacticNgram ngram : allGrams) {
//            if (Utils.nounTags.contains(ngram.getPosTag())) {
//                nounGrams.add(ngram);
//            }
//        }
//        return nounGrams;
//    }
//
//    public List<Pattern> getPatterns() {
//        return patterns;
//    }
//}
