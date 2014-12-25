package edu.ktlab.phenominer.ontologymatching.pairing;

import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.ontology.OBOOntologyFactory;
import edu.ktlab.phenominer.ontologymatching.ontology.OntologyLoader;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;
import edu.ktlab.phenominer.ontologymatching.paring.TermParing;

public class LoadParingExample {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";
	static String fileSynPair = "data/mapping/syn_pair.txt";
	static String fileNonSynPair = "data/mapping/nonsyn_pair.txt";
	
	public static void main(String[] args) {
		OBOOntologyFactory hpoFactory = OntologyLoader.loadOBOOntology(fileHPO);
		OBOOntologyFactory mpoFactory = OntologyLoader.loadOBOOntology(fileMPO);
		
		List<Pair<Frame, Frame>> synPairs = TermParing.loadPairs(hpoFactory, mpoFactory, fileSynPair);
		System.out.println(synPairs.size());
		
		List<Pair<Frame, Frame>> nonsynPairs = TermParing.loadPairs(hpoFactory, mpoFactory, fileNonSynPair);
		System.out.println(nonsynPairs.size());
	}

}
