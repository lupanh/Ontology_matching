package edu.ktlab.ontology.classification.analyze;

import edu.ktlab.ontology.classification.Feature;


public interface FeatureAnalyzer {
	public Feature[] analyze(String n1, String n2);
}
