package edu.ktlab.ontology.classification.analyze;

import java.util.ArrayList;
import java.util.List;

import edu.ktlab.ontology.classification.Feature;


public class CommonTokenAnalyzer implements FeatureAnalyzer{

	public static final double SIMILAR_THRESHOLD = 0.8;
	public static final String PREFIX_FEATURE = "cToken:";
	
	@Override
	public Feature[] analyze(String n1, String n2) {
		String[] commonTks = getCommonTokens(n1, n2);
		if(commonTks == null || commonTks.length == 0) return null;
		
		Feature[] features = new Feature[commonTks.length];
		for(int i = 0; i < commonTks.length; i++){
			String str = commonTks[i];
			features[i] = new Feature(PREFIX_FEATURE + str);
		}
		return features;
	}

	private String[] getCommonTokens(String n1, String n2){
		List<String> holder = new ArrayList<String>();

		String[] tks1 = n1.split(" ");
		String[] tks2 = n2.split(" ");

		if(tks1.length == 0 || tks2.length == 0) return null;

		for(String tk1: tks1)
			for(String tk2: tks2)
				if(tk1.equals(tk2)) holder.add(tk1);

		return holder.toArray(new String[holder.size()]);
	}

	public boolean isSimilar(String tk1, String tk2){
		int comparedLength = 0;

		if(tk1.length() < tk2.length()) 
			comparedLength = (int) (SIMILAR_THRESHOLD*tk1.length());
		else comparedLength = (int) (SIMILAR_THRESHOLD*tk2.length());

		String prefix1 = tk1.substring(0, comparedLength - 1);
		String prefix2 = tk2.substring(0, comparedLength - 1);

		if(prefix1.equals(prefix2)) return true;
		return false;
	}
}
