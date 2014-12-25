package edu.ktlab.phenominer.ontologymatching.findneg;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.ontology.OntologyLoader;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;
import edu.ktlab.phenominer.ontologymatching.paring.TermParing;
import edu.ktlab.phenominer.ontologymatching.util.FileHelper;

public class UnlabeledDataGenerator {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";
	static String fileUnlabeled = "data/mapping/unlabeled_pair.txt";
	
	public static void main(String[] args) throws IOException {
		List<Pair<Frame, Frame>> pairs = TermParing.getRandomPairs(
				OntologyLoader.loadOBOOntology(fileHPO), OntologyLoader.loadOBOOntology(fileMPO),
				100000);
		String content = "";
		for (Pair<Frame, Frame> pair : pairs) {
			content += pair.getLeft().getId() + "\t" + pair.getRight().getId() + "\n";
		}
		System.out.println(pairs.size());

		FileHelper.writeToFile(content, new File(fileUnlabeled), Charset.defaultCharset());
	}

}
