package edu.ktlab.phenominer.ontologymatching.experiments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import de.bwaldvogel.liblinear.Train;
import edu.ktlab.phenominer.ontologymatching.classifier.FeatureCollector;
import edu.ktlab.phenominer.ontologymatching.classifier.FeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.classifier.FeatureSet;
import edu.ktlab.phenominer.ontologymatching.feature.CommonTokenFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.DefineSimilarityFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.LengthFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.SoftTFIDFFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.SynonymPairFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.OBOOntologyFactory;
import edu.ktlab.phenominer.ontologymatching.ontology.OntologyLoader;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;
import edu.ktlab.phenominer.ontologymatching.paring.TermParing;

public class OMMLCrossValidation {
	static String fileSynPair = "data/mapping/syn_pair.txt";
	static String fileNonSynPair = "data/mapping/nonsyn_pair.txt";
	static String fileNegativePair = "data/mapping/neg_pair.0.3_10.txt";
	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";

	static String fileTraining = "models/ontalign.training";
	static String fileWordlist = "models/ontalign.wordlist";
	static String fileModel = "models/ontalign.model";

	static FeatureGenerator[] featureGenerators = new FeatureGenerator[] {
		new CommonTokenFeatureGenerator(),
		new LengthFeatureGenerator(),
		new SoftTFIDFFeatureGenerator(0.7),
		//new SoftTFIDFFeatureGenerator(0.8),
		//new SoftTFIDFFeatureGenerator(0.9),
		new SynonymPairFeatureGenerator(0.7),
		//new SynonymPairFeatureGenerator(0.8),
		//new SynonymPairFeatureGenerator(0.9),
		new DefineSimilarityFeatureGenerator(0.5),
		//new DefineSimilarityFeatureGenerator(0.6),
		//new DefineSimilarityFeatureGenerator(0.7)
		};

	static FeatureCollector featureCollector;
	static FeatureSet featureSet;
	static OBOOntologyFactory hpoFactory;
	static OBOOntologyFactory mpoFactory;

	public static void init() throws Exception {
		featureCollector = new FeatureCollector(featureGenerators);
		featureSet = new FeatureSet();
		hpoFactory = OntologyLoader.loadOBOOntology(fileHPO);
		mpoFactory = OntologyLoader.loadOBOOntology(fileMPO);
	}

	private static void createVectorTrainingFile() throws Exception {
		List<Pair<Frame, Frame>> posPairs = TermParing.loadPairs(hpoFactory, mpoFactory,
				fileSynPair);
		List<Pair<Frame, Frame>> negPairs = TermParing.loadPairs(hpoFactory, mpoFactory,
				fileNegativePair);

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileTraining)));
		for (Pair<Frame, Frame> negPair : negPairs) {
			ArrayList<String> features = featureCollector.getFeatures(negPair);
			String vector = featureSet.addprintVector(features, "SYN", false);
			if (vector.equals("")) {
				continue;
			}
			writer.append(vector).append("\n");
		}
		
		for (Pair<Frame, Frame> posPair : posPairs) {
			ArrayList<String> features = featureCollector.getFeatures(posPair);
			String vector = featureSet.addprintVector(features, "NON-SYN", false);
			if (vector.equals("")) {
				continue;
			}
			writer.append(vector).append("\n");
		}
		
		writer.close();

		FileOutputStream fileOut = new FileOutputStream(fileWordlist);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(featureSet);
		out.close();
		fileOut.close();
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		long current = System.currentTimeMillis();
		init();
		createVectorTrainingFile();

		// Cross-validation
		new Train().main(new String[] { "-v", "10", "-s", "0", fileTraining, fileModel });

		System.out.println("Processing time: " + (System.currentTimeMillis() - current) + " ms");

	}
}
