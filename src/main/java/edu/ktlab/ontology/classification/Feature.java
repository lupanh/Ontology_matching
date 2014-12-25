package edu.ktlab.ontology.classification;

public class Feature {
	private int id;
	private String feature;
	private double weight = 1;
	
	public Feature(){}
	
	public Feature(String feature){
		this.feature = feature;
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getFeature() { return feature; }
	public void setFeature(String feature) { this.feature = feature; }

	public double getWeight() { return weight; }
	public void setWeight(double weight) { this.weight = weight; }
}
