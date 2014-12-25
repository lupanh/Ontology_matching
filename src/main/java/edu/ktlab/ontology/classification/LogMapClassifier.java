package edu.ktlab.ontology.classification;

import java.util.HashMap;
import java.util.Map;

import edu.ktlab.ontology.io.PairLoader;
import edu.ktlab.ontology.paring.Pair;

public class LogMapClassifier implements Classifier{
	private Map<String, Double> maps;
	private static double THRESHOLD = 0.8;

	public LogMapClassifier(){
		try {
			loadLogMapMatchingPairs();
		} catch (Exception e) {	
			System.out.println("LogMapClassifier Exception: " + e.getMessage());
		}
	}
	
	private void loadLogMapMatchingPairs() throws Exception{
		Pair[] pairs = PairLoader.loadFile("model/logmap_pair.txt");
		init(pairs);
	}
	
	
	@Override
	public void init(Pair[] pairs) {
		maps = new HashMap<String, Double>();
		
		for(Pair p: pairs){
			double score = Double.parseDouble(p.getLabel());
			maps.put(p.getHPId() + "_" + p.getMPId(), score);
		}		
	}

	@Override
	public double predict(Pair p) {
		Double value = maps.get(p.getHPId() + "_" + p.getMPId());
		if(value == null) return -1;
		return value;
	}

	@Override
	public String classify(Pair p) {
		double output = predict(p);
		return (output != -1) ? "1" : "0";
	}
}
