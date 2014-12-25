package edu.ktlab.ontology.classification;

import edu.ktlab.ontology.classification.analyze.FeatureAnalyzer;
import edu.ktlab.ontology.classification.analyze.SynonymsAnalyzer;
import edu.ktlab.ontology.io.OntologyLoader;
import edu.ktlab.ontology.paring.Pair;

public class FeatureVectorGenerator {

	private FeatureAnalyzer[] analyzers;

	public FeatureVectorGenerator(FeatureAnalyzer... analyzers) {
		super();
		this.analyzers = analyzers;
	}

	public void analyze(Pair p){
		for(FeatureAnalyzer analyzer: analyzers){
			Feature[] features = null;
			if(analyzer instanceof SynonymsAnalyzer){
				features = analyzer.analyze(p.getHPId(), p.getMPId());
			} else {
				String hpName = OntologyLoader.getHPTermName(p.getHPId());
				String mpName = OntologyLoader.getMPTermName(p.getMPId());
				features = analyzer.analyze(hpName.toLowerCase(), mpName.toLowerCase());
			}
			if(features == null || features.length == 0) continue;
			p.addFeatures(features);
		}
	}

	public FeatureAnalyzer[] getAnalyzers() { return analyzers; }
	public void setAnalyzers(FeatureAnalyzer[] analyzers) { this.analyzers = analyzers; }

}
