package edu.ktlab.ontology.classification;

import com.wcohen.ss.SoftTFIDF;

import edu.ktlab.ontology.io.OntologyLoader;
import edu.ktlab.ontology.io.PairLoader;
import edu.ktlab.ontology.paring.Pair;
import edu.ktlab.ontology.utils.StatisticMap;

public class BaselineClassifier implements Classifier{
	
	private static String directoryPair = "data";
	private static SoftTFIDF classifier;
	private static double THRESHOLD = 0.85;

	public BaselineClassifier(){
		this(directoryPair);
	}
	
	public BaselineClassifier(String directory){
		try {
			init(directory);
		} catch (Exception e) {
			System.err.println("Cannot load directory");
		}
	}
	
	public void init(String directory) throws Exception{
		Pair[] pairs = PairLoader.loadDirectory(directory);
		init(pairs);
	}
	
	public void init(Pair[] pairs){
		SoftTFIDFBuilder.getIntance().build(pairs);
		classifier = SoftTFIDFBuilder.getIntance().getSoftTFIDF();
	}

	public double predict(Pair p){
		String hpName = OntologyLoader.getHPTermName(p.getHPId());
		String mpName = OntologyLoader.getMPTermName(p.getMPId());
		
		double score = classifier.score(hpName, mpName);
		return score;
	}

	public String classify(Pair p){
		double output = predict(p);
		return (output >= THRESHOLD) ? "1" : "0";
	}

	public static void main(String[] args) throws Exception{
		BaselineClassifier classifier = new BaselineClassifier();
		Pair[] pairs = PairLoader.loadDirectory("data");

		StatisticMap statistic = new StatisticMap();
		
		for(Pair p: pairs){
			String predictLabel = classifier.classify(p);
			statistic.add(p.getLabel(), predictLabel);
		}
		statistic.shortGeneralReport();
	}
	

}
