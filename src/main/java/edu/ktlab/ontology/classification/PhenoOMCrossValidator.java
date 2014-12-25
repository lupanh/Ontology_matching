package edu.ktlab.ontology.classification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import de.bwaldvogel.liblinear.Train;
import edu.ktlab.ontology.classification.analyze.AcronymAnalyzer;
import edu.ktlab.ontology.classification.analyze.LengthAnalyzer;
import edu.ktlab.ontology.classification.analyze.CommonTokenAnalyzer;
import edu.ktlab.ontology.classification.analyze.DifferentTokenAnalyzer;
import edu.ktlab.ontology.classification.analyze.SoftTFIDAnalyzer;
import edu.ktlab.ontology.io.PairLoader;
import edu.ktlab.ontology.paring.Pair;

public class PhenoOMCrossValidator {
	private FeatureVectorGenerator featureVectorGenerator;
	private FeatureSet featureSet;
	private Pair[] trainingPairs;

	private String directoryPair = "data";
	private String fileTraining = "model/OntologyMatching.training";
	private String fileModel = "model/OntologyMatching.model";
	private String fileWordlist = "model/OntologyMatching.worldlist";

	// Using liblinear
	public void validate(int s, double c, int fold) throws Exception{
		long current = System.currentTimeMillis();
		featureSet = FeatureSet.createFeatureSet();
		trainingPairs = PairLoader.loadDirectory(directoryPair);
		SoftTFIDFBuilder.getIntance().build(trainingPairs);
		featureVectorGenerator = createFeatureVectorGenerator();

		createVectorTrainingFile();

		System.out.println("\n**********************" 
				+ " Evaluation method " + s + " with c=" + c
				+ " *************************************\n");
		Train.main(new String[] { "-v", Integer.toString(fold), "-c", Double.toString(c), "-s", Integer.toString(s), 
				fileTraining, fileModel});
		System.out.println(System.currentTimeMillis() - current);
	}

	public FeatureVectorGenerator createFeatureVectorGenerator(){
		return new FeatureVectorGenerator(
				new AcronymAnalyzer(), new LengthAnalyzer(),
				new CommonTokenAnalyzer(), new DifferentTokenAnalyzer(),
				new SoftTFIDAnalyzer(SoftTFIDFBuilder.getIntance())
				);
	}

	public void createVectorTrainingFile() throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileTraining)));

		for(Pair p: trainingPairs){
			featureVectorGenerator.analyze(p);
			String vector = featureSet.
					addprintSVMVector(p.getFeatures(), p.getLabel(), false);
			if(vector == null) continue;
			writer.append(vector).append("\n");
		}

		writer.close();

		FileOutputStream fileOut = new FileOutputStream(fileWordlist);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(featureSet);
		out.close();
		fileOut.close();
	}

	public static void main(String[] args) throws Exception{
		new PhenoOMCrossValidator().validate(6, 0.5, 10);
	}

}
