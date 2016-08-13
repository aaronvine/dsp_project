package utils;

import org.apache.hadoop.io.*;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedGraph;
import java.util.List;
import java.util.ArrayList;


public class BiarcGraph {

    private List<Ngram> ngrams;
    private SimpleDirectedGraph<Ngram, Edge> graph;
    private List<List<Edge>> paths;
    private List<PatternWritable> patterns;
    private int appearances;

    public BiarcGraph(Text text) {
        String[] splitted = text.toString().split("\t");
        ngrams = parse(splitted[1]);
        this.appearances = Integer.parseInt(splitted[2]);
        this.paths = new ArrayList<List<Edge>>();
        buildGraph(ngrams);
        List<Ngram> nounsVertices = extractNounsVertices();
        if (nounsVertices.size() < 2) {
            return;
        }

        for (int i = 0; i < nounsVertices.size(); i++) {
            for (int j = 0; j < nounsVertices.size(); j++) {
                if (i==j) {
                    continue;
                }
                List<Edge> path = DijkstraShortestPath.findPathBetween(graph, nounsVertices.get(i), nounsVertices.get(j));
                if (path == null) {
                    continue;
                }
                paths.add(path);
            }
        }

        this.patterns = extractPatterns();
    }

    public List<Ngram> parse(String s) {
        List<Ngram> ngrams = new ArrayList<Ngram>();
        String[] splitted = s.split(" ");
        for (String ngram : splitted) {
            if (ngram == null) {
                continue;
            }
            ngrams.add(new Ngram(ngram));
        }

        return ngrams;
    }

    private List<PatternWritable> extractPatterns() {
        List<PatternWritable> patterns = new ArrayList<PatternWritable>();
        for(List<Edge> path: paths){
            extractNounsFromPath(path);
            PatternWritable pattern = new PatternWritable(extractPathHashCode(path), extractNounsFromPath(path));
            patterns.add(pattern);
        }

        return patterns;
    }

    private IntWritable extractPathHashCode(List<Edge> path) {
        String string = "";
        for (Edge edge: path){
            string += ((Ngram)edge.getSource()).getLabel();
        }
        string += ((Ngram)path.get(path.size() - 1).getTarget()).label;

        return new IntWritable(string.hashCode());
    }

    private PairWritable extractNounsFromPath(List<Edge> path) {
        String first = ((Ngram)path.get(0).getSource()).word;
        String second = ((Ngram)path.get(path.size() - 1).getTarget()).word;
        Stemmer stemmer = new Stemmer();
        stemmer.add(first.toCharArray(), first.length());
        stemmer.stem();
        String stemmedFirst = stemmer.toString();
        stemmer.add(second.toCharArray(), second.length());
        stemmer.stem();
        String stemmedSecond = stemmer.toString();

        return new PairWritable(stemmedFirst, stemmedSecond, appearances);
    }

    private void buildGraph(List<Ngram> ngrams) {
        graph = new SimpleDirectedGraph<Ngram, Edge>(Edge.class);
        for (Ngram ngram : ngrams) {
            graph.addVertex(ngram);
        }
        for (Ngram ngram : ngrams) {
            if (ngram.headIdx == 0) {
                continue;
            }
            graph.addEdge(ngrams.get(ngram.headIdx - 1), ngram);
        }
    }

    private List<Ngram> extractNounsVertices() {
        List<Ngram> allGrams = new ArrayList<Ngram>(graph.vertexSet());
        List<Ngram> nounGrams = new ArrayList<Ngram>();

        for (Ngram ngram : allGrams) {
            if (Common.nounTags.contains(ngram.posTag)) {
                nounGrams.add(ngram);
            }
        }

        return nounGrams;
    }

    public List<PatternWritable> getPatterns() {
        return patterns;
    }
}


