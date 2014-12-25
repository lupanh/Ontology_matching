package edu.ktlab.phenominer.ontologymatching.findneg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.obolibrary.oboformat.model.Frame;

import com.google.common.base.Joiner;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import edu.ktlab.phenominer.ontologymatching.classifier.FeatureCollector;
import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.CommonTokenFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.DefineSimilarityFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.LengthFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.SoftTFIDFFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.SynonymPairFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class NegativeDataGenerator {
	static FeatureGenerator[] featureGenerators = new FeatureGenerator[] {
		new CommonTokenFeatureGenerator(),
		new LengthFeatureGenerator(),
		new SoftTFIDFFeatureGenerator(0.7),
		//new SoftTFIDFFeatureGenerator(0.8),
		//new SoftTFIDFFeatureGenerator(0.9),
		new SynonymPairFeatureGenerator(0.7),
		//new SynonymPairFeatureGenerator(0.8),
		//new SynonymPairFeatureGenerator(0.9),
		new DefineSimilarityFeatureGenerator(0.5),
		//new DefineSimilarityFeatureGenerator(0.6),
		//new DefineSimilarityFeatureGenerator(0.7)
		};

	static FeatureCollector featureCollector = new FeatureCollector(featureGenerators);;

	public static List<Pair<Frame, Frame>> findNegativePairs(
			List<Pair<Frame, Frame>> positivePairs, List<Pair<Frame, Frame>> unlabeledPairs,
			AbstractStringMetric metric, double threshold, int deepLevel) {
		List<Pair<Frame, Frame>> negativePairs = new ArrayList<Pair<Frame, Frame>>();
		Map<Pair<Frame, Frame>, ArrayList<String>> positiveVectors = new HashMap<Pair<Frame, Frame>, ArrayList<String>>();
		Map<Pair<Frame, Frame>, ArrayList<String>> unlabeledVectors = new HashMap<Pair<Frame, Frame>, ArrayList<String>>();

		for (Pair<Frame, Frame> posPair : positivePairs) {
			ArrayList<String> posFeatures = featureCollector.getFeatures(posPair);
			positiveVectors.put(posPair, posFeatures);
		}

		for (Pair<Frame, Frame> unlabeledPair : unlabeledPairs) {
			ArrayList<String> unlabeledFeatures = featureCollector.getFeatures(unlabeledPair);
			unlabeledVectors.put(unlabeledPair, unlabeledFeatures);
		}

		for (Pair<Frame, Frame> unlabeledPair : unlabeledVectors.keySet()) {
			boolean negFlag = true;
			String unlabeledString = Joiner.on(" ").join(unlabeledVectors.get(unlabeledPair));

			for (Pair<Frame, Frame> posPair : positiveVectors.keySet()) {
				String posString = Joiner.on(" ").join(positiveVectors.get(posPair));
				double score = metric.getSimilarity(unlabeledString, posString);
				//System.out.println(score + "\t" + unlabeledString + "\t" + posString);
				if (score >= threshold + deepLevel * 0.01) {
					negFlag = false;
					break;
				}
			}

			if (negFlag)
				negativePairs.add(unlabeledPair);
		}

		return negativePairs;
	}
}
