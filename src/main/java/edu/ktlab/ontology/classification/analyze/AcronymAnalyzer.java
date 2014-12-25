package edu.ktlab.ontology.classification.analyze;

import edu.ktlab.ontology.classification.Feature;


public class AcronymAnalyzer implements FeatureAnalyzer {
	public static final String PREFIX_FEATURE = "acronym:";

	@Override
	public Feature[] analyze(String n1, String n2) {
		String a1 = getAcronym(n1);
		String a2 = getAcronym(n2);
		
		if(a1 == null || a2 == null)
			return null;
		if(a1.equals(a2)) return new Feature[]{new Feature(PREFIX_FEATURE + "true")};
		return new Feature[]{new Feature(PREFIX_FEATURE + "false")};
	}
	
	private String getAcronym(String n){
		String[] tks = n.split(" ");

		if(tks.length == 0) return null;
		
		String acronym = "";
		for(String tk: tks){
			if(tk == null || tk.isEmpty() || "of".equals(tk)) continue;
			acronym += tk.charAt(0) + "";
		}
		return acronym;
	}


}
