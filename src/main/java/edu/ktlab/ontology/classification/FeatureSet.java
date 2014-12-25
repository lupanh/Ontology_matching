package edu.ktlab.ontology.classification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FeatureSet implements Serializable {
	private static final long serialVersionUID = 1316496895598356726L;
	
	private Map<String, Integer> wordlist;
	private ArrayList<String> labels;

	int labelKey = 0;

	public FeatureSet() {
		wordlist = new HashMap<String, Integer>();
		wordlist.put("NO_USE", 0);
		labels = new ArrayList<String>();
	}
	
	public static FeatureSet createFeatureSet(){
		return new FeatureSet();
	}

	public Map<String, Integer> getWordlist() { return wordlist; }
	public void setWordlist(Map<String, Integer> wordlist) { this.wordlist = wordlist; }

	public ArrayList<String> getLabels() { return labels; }
	public void setLabels(ArrayList<String> labels) { this.labels = labels; }

	public int getLabelKey() { return labelKey; }
	public void setLabelKey(int labelKey) { this.labelKey = labelKey; }

	public void addFeatureLabel(String[] strFeatures, String label, boolean flagTest) {
		HashSet<String> setFeatures = new HashSet<String>();
		for (String feature : strFeatures)
			setFeatures.add(feature);
		if (!label.equals(""))
			if (!labels.contains(label))
				if (!flagTest) {
					labels.add(label);
				}		
	}

	public TreeMap<Integer, Integer> addStringFeatureVector(Feature[] features, String label,
			boolean flagTest) {
		HashSet<String> setFeatures = new HashSet<String>();
		TreeMap<Integer, Integer> vector = new TreeMap<Integer, Integer>();

		for (Feature f : features){
			String strFeature = f.getFeature();
			setFeatures.add(strFeature);
		}
		if (setFeatures.size() == 0)
			return null;

		if (!label.equals(""))
			if (labels.contains(label))
				vector.put(labelKey, labels.indexOf(label));
			else {
				if (!flagTest) {
					labels.add(label);
					vector.put(labelKey, labels.indexOf(label));
				} else {
					// throw new
					// IllegalArgumentException("Label of Testing Data is error!!!");
					return null;
				}
			}
		for (String feature : setFeatures) {
			if (wordlist.containsKey(feature)) {
				vector.put(wordlist.get(feature), 1);
			} else {
				if (!flagTest) {
					wordlist.put(feature, wordlist.size());
					vector.put(wordlist.get(feature), 1);
				}
			}
		}

		return vector;
	}

	public TreeMap<Integer, Integer> addStringFeatureVector(List<Feature> strFeatures,
			String label, boolean flagTest) {
		if (strFeatures == null)
			return null;
		return addStringFeatureVector(strFeatures.toArray(new Feature[strFeatures.size()]), label,
				flagTest);
	}

	public String addprintSVMVector(List<Feature> strFeatures, String label, boolean flagTest) {
		TreeMap<Integer, Integer> vector = addStringFeatureVector(strFeatures, label, flagTest);
		if (vector == null)
			return null;

		StringBuilder builder = new StringBuilder();
		builder.append("" + vector.get(labelKey));
		for (int key : vector.keySet()) {
			if (key == labelKey)
				continue;
			builder.append(" " + key + ":" + vector.get(key));
		}
		return builder.toString();
	}
}
