package edu.ktlab.phenominer.ontologymatching.classifier;

import java.util.ArrayList;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public interface FeatureGenerator {
	public ArrayList<String> extractFeatures(Pair<Frame, Frame> pair);
}
