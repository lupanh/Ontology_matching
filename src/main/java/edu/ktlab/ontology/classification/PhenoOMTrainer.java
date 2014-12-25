package edu.ktlab.ontology.classification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.bwaldvogel.liblinear.Train;
import edu.ktlab.ontology.classification.analyze.AcronymAnalyzer;
import edu.ktlab.ontology.classification.analyze.LengthAnalyzer;
import edu.ktlab.ontology.classification.analyze.CommonTokenAnalyzer;
import edu.ktlab.ontology.classification.analyze.DifferentTokenAnalyzer;
import edu.ktlab.ontology.classification.analyze.SoftTFIDAnalyzer;
import edu.ktlab.ontology.io.PairLoader;
import edu.ktlab.ontology.paring.Pair;
import edu.ktlab.ontology.utils.IOUtil;

public class PhenoOMTrainer {
	private FeatureVectorGenerator featureVectorGenerator;
	private FeatureSet featureSet;
	private Pair[] trainingPairs;
	
	private String directoryPair = "data";
	private String fileTraining = "model/OntologyMatching.training";
	private String fileModel = "model/OntologyMatching.model";
	private String fileWordlist = "model/OntologyMatching.worldlist";

	public void train(double c, int s) throws Exception {
		long current = System.currentTimeMillis();
		featureSet = FeatureSet.createFeatureSet();
		trainingPairs = PairLoader.loadDirectory(directoryPair);
		
		SoftTFIDFBuilder.getIntance().build(trainingPairs);
		
		featureVectorGenerator = createFeatureVectorGenerator();
		
		createVectorTrainingFile();
		Train.main(new String[] { "-c", Double.toString(c), "-s", Integer.toString(s), 
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
		
		IOUtil.writeObject(fileWordlist, featureSet);
	}
	
	public static void main(String[] args) throws Exception{
		new PhenoOMTrainer().train(1.0, 6);
	}
}
