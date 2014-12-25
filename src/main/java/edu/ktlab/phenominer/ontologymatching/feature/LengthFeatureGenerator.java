package edu.ktlab.phenominer.ontologymatching.feature;

import java.util.ArrayList;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.FrameUtil;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class LengthFeatureGenerator implements FeatureGenerator {

	public ArrayList<String> extractFeatures(Pair<Frame, Frame> pair) {
		ArrayList<String> featureCollector = new ArrayList<String>();
		
		int lengthLeft = FrameUtil.getTagName(pair.getLeft()).split(" ").length;
		if (lengthLeft > 4)
			featureCollector.add("left:length:long");
		else
			featureCollector.add("left:length:short");
		
		int lengthRight = FrameUtil.getTagName(pair.getRight()).split(" ").length;
		if (lengthRight > 4)
			featureCollector.add("right:length:long");
		else
			featureCollector.add("right:length:short");
		
		return featureCollector;
	}

}
