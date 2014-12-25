package edu.ktlab.phenominer.ontologymatching.classifier;

import java.util.ArrayList;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;

import edu.ktlab.phenominer.ontologymatching.feature.CommonTokenFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.DefineSimilarityFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.LengthFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.SoftTFIDFFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.feature.SynonymPairFeatureGenerator;
import edu.ktlab.phenominer.ontologymatching.ontology.OBOOntologyFactory;
import edu.ktlab.phenominer.ontologymatching.ontology.OntologyLoader;
import edu.ktlab.phenominer.ontologymatching.paring.Pair;
import edu.ktlab.phenominer.ontologymatching.paring.TermParing;

public class FeatureCollectorExample {
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

	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";
	static String fileSynPair = "data/mapping/syn_pair.txt";
	static String fileNonSynPair = "data/mapping/nonsyn_pair.txt";

	public static void main(String[] args) {
		featureCollector = new FeatureCollector(featureGenerators);
		featureSet = new FeatureSet();

		OBOOntologyFactory hpoFactory = OntologyLoader.loadOBOOntology(fileHPO);
		OBOOntologyFactory mpoFactory = OntologyLoader.loadOBOOntology(fileMPO);

		List<Pair<Frame, Frame>> synPairs = TermParing.loadPairs(hpoFactory, mpoFactory,
				fileSynPair);

		for (Pair<Frame, Frame> pair : synPairs) {
			System.out.print(pair.getLeft().getId() + "\t" + pair.getRight().getId() + "\t");
			ArrayList<String> features = featureCollector.getFeatures(pair);
			System.out.println(features);
			String vector = featureSet.addprintVector(features, "1", false);
			System.out.println(vector);
		}
	}

}
