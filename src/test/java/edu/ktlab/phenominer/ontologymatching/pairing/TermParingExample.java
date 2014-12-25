package edu.ktlab.phenominer.ontologymatching.pairing;

import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;
import edu.ktlab.phenominer.ontologymatching.ontology.OntologyLoader;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;
import edu.ktlab.phenominer.ontologymatching.paring.TermParing;

public class TermParingExample {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";

	public static void main(String... strings) {
		List<Pair<Frame, Frame>> pairs = TermParing.getRandomPairs(
				OntologyLoader.loadOBOOntology(fileHPO), OntologyLoader.loadOBOOntology(fileMPO),
				10);
		for (Pair<Frame, Frame> pair : pairs) {
			System.out.println(pair);
		}
		System.out.println(pairs.size());

		pairs = TermParing.getPairByThreshold(OntologyLoader.loadOBOOntology(fileHPO),
				OntologyLoader.loadOBOOntology(fileMPO), new CosineSimilarity(), 0.6);
		System.out.println(pairs.size());

		pairs = TermParing.getPairBySoftTFIDF(OntologyLoader.loadOBOOntology(fileHPO),
				OntologyLoader.loadOBOOntology(fileMPO), 0.6);
		System.out.println(pairs.size());
	}
}
