package edu.ktlab.ontology.classification.analyze;

import edu.ktlab.ontology.classification.Feature;


public class LengthAnalyzer implements FeatureAnalyzer {
	public static String PREFIX_FEATURE = "length:";
	public static int LONG_TOKEN_NUM = 4;
	
	@Override
	public Feature[] analyze(String n1, String n2) {
		String[] tks1 = n1.split(" ");
		String[] tks2 = n2.split(" ");

		if(tks1.length >= LONG_TOKEN_NUM 
				&& tks2.length >= LONG_TOKEN_NUM)
			return new Feature[]{new Feature(PREFIX_FEATURE + ":same:long")};
		else if(tks1.length < LONG_TOKEN_NUM 
				&& tks2.length < LONG_TOKEN_NUM)
			return new Feature[]{new Feature(PREFIX_FEATURE + ":same:short")};
		else return new Feature[]{new Feature(PREFIX_FEATURE + ":diff")};
	}

	

}
