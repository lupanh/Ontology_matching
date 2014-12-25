package edu.ktlab.ontology.utils;

import java.util.HashMap;
import java.util.Map;

public class StatisticMap {
	private Map<String, Integer> statistic = new HashMap<String, Integer>();

	public void add(String key, Integer value){
		increase(key);
	}
	
	public void add(String actualLabel, String predictLabel){
		if(actualLabel.equals("1") && predictLabel.equals("1")){
//			increase("TP");
			increase("actualSyn");
			increase("predictSyn");
			increase("totalSyn");
		} else if(actualLabel.equals("1") && predictLabel.equals("0")){
//			increase("FN");
			increase("predictNonSyn");
			increase("totalSyn");
		} else if(actualLabel.equals("0") && predictLabel.equals("1")){
//			increase("FP");
			increase("predictSyn");
			increase("totalNonSyn");
		} else if(actualLabel.equals("0") && predictLabel.equals("0")){
//			increase("TN");
			increase("actualNonSyn");
			increase("predictNonSyn");
			increase("totalNonSyn");
		}
	}

	public int getValue(String type){
		Integer value = statistic.get(type);
		if(value == null) return -1;
		return value;
	}

	public void increase(String type){
		Integer count = statistic.get(type);
		if(count == null) statistic.put(type, 1);
		else statistic.put(type, ++count);
	}

	public double getPrecision(){
		double score = (double) getValue("TP")/(getValue("TP")+getValue("FP"));
		return score;
	}

	public double getRecall(){
		double score = (double) getValue("TP")/(getValue("TP") + getValue("FN"));
		return score;
	}

	public double getF1(){
		double precision = getPrecision();
		double recall = getRecall();
		double f1 = (2*precision*recall)/(precision+recall);
		return f1;
	}
	
	public void shortReportByClazz(String clazz){
		double precision = (double) getValue("actual"+clazz)/getValue("predict"+clazz);
		double recall = (double) getValue("actual"+clazz)/getValue("total"+clazz);
		double f1 = (2*precision*recall)/(precision+recall);
		
		System.out.println("\n------------------------------------"
				+ "\nReport by class " + clazz );
		System.out.println("Precision = " + precision);
		System.out.println("Recall = " + recall);
		System.out.println("F = " + f1);
	}
	
	public void shortGeneralReport(){
		double precision = getPrecision();
		double recall = getRecall();
		double f1 = getF1();
		
		System.out.println("Precision = " + precision);
		System.out.println("Recall = " + recall);
		System.out.println("F = " + f1);
	}
	
	public void detailedReport(){
		System.out.println("\nReport: ");
		for(String key: statistic.keySet()){
			System.out.println(key + " = " + statistic.get(key));
		}
		System.out.println("****************************");
		shortReportByClazz("Syn");
		shortReportByClazz("NonSyn");
	}

}
