package edu.ktlab.phenominer.ontologymatching.findneg;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;
import edu.ktlab.phenominer.ontologymatching.ontology.OBOOntologyFactory;
import edu.ktlab.phenominer.ontologymatching.ontology.OntologyLoader;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;
import edu.ktlab.phenominer.ontologymatching.paring.TermParing;
import edu.ktlab.phenominer.ontologymatching.util.FileHelper;

public class NegativeDataGeneratorExample {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";
	static String fileSynPair = "data/mapping/syn_pair.txt";
	static String fileUnlabeledPair = "data/mapping/unlabeled_pair.txt";
	static String fileNegativePair = "data/mapping/neg_pair.0.4_5.txt";

	public static void main(String[] args) throws IOException {
		OBOOntologyFactory hpoFactory = OntologyLoader.loadOBOOntology(fileHPO);
		OBOOntologyFactory mpoFactory = OntologyLoader.loadOBOOntology(fileMPO);

		List<Pair<Frame, Frame>> posPairs = TermParing.loadPairs(hpoFactory, mpoFactory,
				fileSynPair);
		List<Pair<Frame, Frame>> unlabeledPairs = TermParing.loadPairs(hpoFactory, mpoFactory,
				fileUnlabeledPair);
		List<Pair<Frame, Frame>> negPairs = NegativeDataGenerator.findNegativePairs(posPairs,
				unlabeledPairs, new CosineSimilarity(), 0.4, 5);

		String content = "";
		for (Pair<Frame, Frame> pair : negPairs) {
			content += pair.getLeft().getId() + "\t" + pair.getRight().getId() + "\n";
		}
		System.out.println(negPairs.size());

		FileHelper.writeToFile(content, new File(fileNegativePair), Charset.defaultCharset());
	}

}
