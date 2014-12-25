package edu.ktlab.ontology.paring;

import java.util.ArrayList;
import java.util.List;

import edu.ktlab.ontology.classification.Feature;

public class Pair {
	private String hPId;
	private String mPId;
	private String label;
	
	private List<Feature> features = new ArrayList<Feature>();
	
	public Pair(String hPId, String mPId, String label) {
		super();
		this.hPId = hPId;
		this.mPId = mPId;
		this.label = label;
	}
	
	public String getHPId() { return hPId; }
	public void setTermId1(String hPId) { this.hPId = hPId; }
	
	public String getMPId() { return mPId; }
	public void setMPId(String mpId) { this.mPId = mpId; }
	
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	
	public List<Feature> getFeatures() { return features; }
	public void setFeatures(List<Feature> features) { this.features = features; }
	public void addFeature(Feature f){ this.features.add(f); }
	public void addFeatures(Feature[] features){ 
		for(Feature f: features)
			addFeature(f);
	}
	
	public String getStringFeatureVector(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < features.size(); i++){
			Feature f = features.get(i);
			if(i != features.size() - 1) 
				builder.append(f.getFeature()).append(" ");
			else 
				builder.append(f.getFeature());
		}
		return builder.toString();
	}
	
}

