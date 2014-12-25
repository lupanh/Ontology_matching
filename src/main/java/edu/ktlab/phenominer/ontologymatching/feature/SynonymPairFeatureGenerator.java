package edu.ktlab.phenominer.ontologymatching.feature;

import java.util.ArrayList;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import com.wcohen.secondstring.JaroWinkler;
import com.wcohen.secondstring.SoftTFIDF;

import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.FrameUtil;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class SynonymPairFeatureGenerator implements FeatureGenerator {
	SoftTFIDF softTFIDF = new SoftTFIDF(new JaroWinkler(), 0.9);
	double threshold;
	
	public SynonymPairFeatureGenerator(double threshold) {
		this.threshold = threshold;
	}
	
	public ArrayList<String> extractFeatures(Pair<Frame, Frame> pair) {
		ArrayList<String> featureCollector = new ArrayList<String>();
		
		List<String> leftSynonyms = FrameUtil.getSynonyms(pair.getLeft());
		leftSynonyms.add(FrameUtil.getTagName(pair.getLeft()).toLowerCase());
		List<String> rightSynonyms = FrameUtil.getSynonyms(pair.getRight());
		rightSynonyms.add(FrameUtil.getTagName(pair.getRight()).toLowerCase());
		
		int countSyns = 0;
		int countSure = 0;
		for (String leftSyn : leftSynonyms)
			for (String rightSyn : rightSynonyms) {				
				double score = softTFIDF.score(leftSyn, rightSyn);				
				if (Math.round(score) == 1)
					countSure++;
				if (score > threshold)
					countSyns++;
			}
		
		//if (countSure > 0) {
		//	featureCollector.add("syns:sure");
		//	return featureCollector;
		//}
			
		if (countSyns >= 2)
			featureCollector.add("syns:high:" + threshold);
		else if (countSyns >= 1)
			featureCollector.add("syns:medium:" + threshold);
		else
			featureCollector.add("syns:none:" + threshold);
		return featureCollector;
	}
}
