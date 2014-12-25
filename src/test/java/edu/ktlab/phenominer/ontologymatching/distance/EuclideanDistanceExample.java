package edu.ktlab.phenominer.ontologymatching.distance;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;

public class EuclideanDistanceExample {

	public static void main(String[] args) {
		AbstractStringMetric metric = new CosineSimilarity();
		String text1 = "hanoi cua toi";
		String text2 = "vietnam cua toi";
		System.out.println(metric.getSimilarity(text1, text2));		
	}

}
