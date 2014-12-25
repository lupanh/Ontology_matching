package edu.ktlab.phenominer.ontologymatching.distance;

import com.wcohen.secondstring.JaroWinkler;
import com.wcohen.secondstring.SoftTFIDF;

public class SoftTFIDFExample {
	public static void main(String... strings) {
		SoftTFIDF softTFIDF = new SoftTFIDF(new JaroWinkler(), 0.9);
		String text1 = "hanoi1 cua toi";
		String text2 = "hanoi2 cua toi";
		System.out.println(softTFIDF.score(text1, text2));
	}
}
