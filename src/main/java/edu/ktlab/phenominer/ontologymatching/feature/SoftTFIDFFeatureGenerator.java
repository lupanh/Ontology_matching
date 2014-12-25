package edu.ktlab.phenominer.ontologymatching.feature;

import java.util.ArrayList;

import org.obolibrary.oboformat.model.Frame;

import com.wcohen.secondstring.JaroWinkler;
import com.wcohen.secondstring.SoftTFIDF;

import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.FrameUtil;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;

public class SoftTFIDFFeatureGenerator implements FeatureGenerator {
	SoftTFIDF softTFIDF = new SoftTFIDF(new JaroWinkler(), 0.9);
	double threshold;
	
	public SoftTFIDFFeatureGenerator(double threshold) {
		this.threshold = threshold;
	}
	
	public ArrayList<String> extractFeatures(Pair<Frame, Frame> pair) {
		ArrayList<String> featureCollector = new ArrayList<String>();
		
		double score = softTFIDF.score(FrameUtil.getTagName(pair.getLeft()).toLowerCase(), FrameUtil.getTagName(pair.getRight()).toLowerCase());
		
		//if (Math.round(score) == 1) {
		//	featureCollector.add("softtfidf:sure");
		//	return featureCollector;
		//}			
		
		if (score > threshold)
			featureCollector.add("softtfidf:high:" + threshold);
		else
			featureCollector.add("softtfidf:low:" + threshold);
		return featureCollector;
	}
}
