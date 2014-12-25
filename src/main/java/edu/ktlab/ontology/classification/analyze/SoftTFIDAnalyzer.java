package edu.ktlab.ontology.classification.analyze;

import com.wcohen.ss.SoftTFIDF;

import edu.ktlab.ontology.classification.Feature;
import edu.ktlab.ontology.classification.SoftTFIDFBuilder;


public class SoftTFIDAnalyzer implements FeatureAnalyzer{
	public static final String PREFIX_FEATURE = "stfidf:";
	public static final double THRESHOLD = 0.85;
	
	private SoftTFIDF distance;
	
	@Override
	public Feature[] analyze(String n1, String n2) {
		double score = distance.score(n1, n2);
		
		if(score >= THRESHOLD) return new Feature[]{new Feature(PREFIX_FEATURE + "high")};
		else return new Feature[]{new Feature(PREFIX_FEATURE + "low")};
	}
	
	public SoftTFIDAnalyzer(SoftTFIDFBuilder builder){
		this.distance = builder.getSoftTFIDF();
	}


}
