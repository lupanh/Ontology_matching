package edu.ktlab.phenominer.ontologymatching.feature;

import java.util.ArrayList;

import org.obolibrary.oboformat.model.Frame;

import com.wcohen.secondstring.JaroWinkler;
import com.wcohen.secondstring.SoftTFIDF;

import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.FrameUtil;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class DefineSimilarityFeatureGenerator implements FeatureGenerator {
	SoftTFIDF softTFIDF = new SoftTFIDF(new JaroWinkler(), 0.9);
	double threshold;

	public DefineSimilarityFeatureGenerator(double threshold) {
		this.threshold = threshold;
	}

	public ArrayList<String> extractFeatures(Pair<Frame, Frame> pair) {
		ArrayList<String> featureCollector = new ArrayList<String>();

		double score = softTFIDF.score(FrameUtil.getDefine(pair.getLeft()).toLowerCase(), FrameUtil
				.getDefine(pair.getRight()).toLowerCase());

		if (score > threshold)
			featureCollector.add("simdefine:high:" + threshold);
		else
			featureCollector.add("simdefine:low:" + threshold);
		
		return featureCollector;
	}
}
