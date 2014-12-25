package edu.ktlab.ontology.classification;

import edu.ktlab.ontology.paring.Pair;

public interface Classifier {
	public void init(Pair[] pairs);
	public double predict(Pair p);
	public String classify(Pair p);
}
