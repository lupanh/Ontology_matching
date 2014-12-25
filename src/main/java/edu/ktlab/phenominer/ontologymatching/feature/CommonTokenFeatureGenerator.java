package edu.ktlab.phenominer.ontologymatching.feature;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.FrameUtil;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class CommonTokenFeatureGenerator implements FeatureGenerator {	
	public ArrayList<String> extractFeatures(Pair<Frame, Frame> pair) {
		ArrayList<String> featureCollector = new ArrayList<String>();
		
		String strLeft = FrameUtil.getTagName(pair.getLeft()).toLowerCase();
		String strRight = FrameUtil.getTagName(pair.getRight()).toLowerCase();
		String[] tokensLeft = strLeft.split(" ");
		String[] tokensRight = strRight.split(" ");
		
		Set<String> commonTokens = new HashSet<String>();
		for (String tokenLeft : tokensLeft)
			for (String tokenRight : tokensRight)
				if (tokenLeft.equals(tokenRight))
					commonTokens.add(tokenLeft);
		
		for (String token : commonTokens)
			featureCollector.add("common:" + token);
		
		for (String tokenLeft : tokensLeft)
			if (!commonTokens.contains(tokenLeft))
				featureCollector.add("left:diff:" + tokenLeft);
		
		for (String tokenRight : tokensRight)
			if (!commonTokens.contains(tokenRight))
				featureCollector.add("right:diff:" + tokenRight);
		
		return featureCollector;
	}
}
