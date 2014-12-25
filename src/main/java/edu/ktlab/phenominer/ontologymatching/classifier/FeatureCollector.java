package edu.ktlab.phenominer.ontologymatching.classifier;

import java.util.ArrayList;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class FeatureCollector {
	private FeatureGenerator[] mFeatureGenerators;

	public FeatureCollector(FeatureGenerator[] mFeatureGenerators) {
		this.mFeatureGenerators = mFeatureGenerators;
	}

	public ArrayList<String> getFeatures(Pair<Frame, Frame> pair) {
		ArrayList<String> context = new ArrayList<String>();
		for (FeatureGenerator generator : mFeatureGenerators) {
			ArrayList<String> extractedFeatures = generator.extractFeatures(pair);
			context.addAll(extractedFeatures);
		}
		return context;
	}
}
