package edu.ktlab.ontology.classification.analyze;

import edu.ktlab.ontology.classification.Feature;
import edu.ktlab.ontology.classification.SoftTFIDFBuilder;
import edu.ktlab.ontology.io.OntologyLoader;


public class SynonymsAnalyzer implements FeatureAnalyzer {
	public static String PREFIX_FEATURE = "syns:";
	public static double THRESHOLD = 0.8;

	@Override
	public Feature[] analyze(String hpId, String mpId) {
		String[] hpSyns = OntologyLoader.getHPSynonyms(hpId);
		if(hpSyns.length == 0) return null;

		String[] mpSyns = OntologyLoader.getMPSynonyms(mpId);
		if(mpSyns.length == 0) return null;

		int count = 0;
		for(String hpSyn: hpSyns){
			for(String mpSyn: mpSyns){
				double score = SoftTFIDFBuilder.getIntance().score(hpSyn, mpSyn);
				if(score >= THRESHOLD) count++; 
			}
		}
		
		if(count >= 2) return new Feature[]{new Feature(PREFIX_FEATURE + ":high")};
		else if(count >= 1) return new Feature[]{new Feature(PREFIX_FEATURE + ":normal")};
		else return new Feature[]{new Feature(PREFIX_FEATURE + ":none")};
	}



}
