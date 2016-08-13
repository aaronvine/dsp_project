# dsp_project
Distributed System Programming: Hypernym Detection


### Steps

1. Extract patterns:

In the map part we're building a graph from the BIARCS, and extracting the patterns.
The output key is the pattern (hashcode) and the value is the pair of nouns.

We're using a regular partitioner: pattern hashcode % num of reducers.

In the reduce part, for each pair, we're extracting the pattern idx and and how many times it appears.
The output key is the pair and the value is the idx and num of appearances.


2. Build Vectors:

In the map part we're handling two inputs: 
first step output (goes straight to the comparator) and the annotated set (from which we're extracting a tagged pair key and a boolean value).

We're using a comparator which makes sure that the tagged pairs come before the output.

In the reduce part we're saving the tagged pair and its value, and building a vector from the following keys.
The output key is the pair and its value and the value is the vector.


3. Classifier:

We're using the provided WEKA package to extract a vector from each output (<pair, value, vector>).
After that, the data is split to ten and for each iteration (ten in total), one will be a test set and the other nine are training sets.
For each one of the ten iterations the analysis (f-measure, precision, recall) is extracted.