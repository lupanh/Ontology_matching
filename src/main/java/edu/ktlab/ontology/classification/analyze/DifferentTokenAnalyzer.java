package edu.ktlab.ontology.classification.analyze;

import java.util.ArrayList;
import java.util.List;

import edu.ktlab.ontology.classification.Feature;


public class DifferentTokenAnalyzer implements FeatureAnalyzer {
	public static final String PREFIX_FEATURE = "dToken:";
	
	@Override
	public Feature[] analyze(String n1, String n2) {
		String[] differentTks = getDifferentTokens(n1, n2);
		if(differentTks == null || differentTks.length == 0) return null;
		
		Feature[] features = new Feature[differentTks.length];
		for(int i = 0; i < differentTks.length; i++){
			String str = differentTks[i];
			features[i] = new Feature(PREFIX_FEATURE + str);
		}
		return features;
	}

	private String[] getDifferentTokens(String n1, String n2){
		List<String> holder = new ArrayList<String>();

		String[] tks1 = n1.split(" ");
		String[] tks2 = n2.split(" ");

		if(tks1.length == 0 || tks2.length == 0) return null;

		for(String tk1: tks1)
			for(String tk2: tks2)
				if(tk1.equals(tk2)) holder.add(tk1);

		int dSize = tks1.length + tks2.length - 2*holder.size() + 2;
		String[] differentTokens = new String[dSize];

		int i = 0;
		for(String tk1: tks1) 
			if(!holder.contains(tk1)) differentTokens[i++] = tk1;
		for(String tk2: tks2) 
			if(!holder.contains(tk2)) differentTokens[i++] = tk2;

		return differentTokens;
	}
}
