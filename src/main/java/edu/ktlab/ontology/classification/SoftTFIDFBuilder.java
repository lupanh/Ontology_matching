package edu.ktlab.ontology.classification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wcohen.ss.BasicStringWrapperIterator;
import com.wcohen.ss.JaroWinkler;
import com.wcohen.ss.SoftTFIDF;
import com.wcohen.ss.api.StringWrapper;
import com.wcohen.ss.api.Tokenizer;
import com.wcohen.ss.tokens.SimpleTokenizer;

import edu.ktlab.ontology.io.OntologyLoader;
import edu.ktlab.ontology.paring.Pair;

public class SoftTFIDFBuilder implements Serializable {
	private static final long serialVersionUID = 2528407936625831504L;

	private static SoftTFIDFBuilder instance = null;

	private SoftTFIDF distance;
	private List<StringWrapper> wrappers;

	public static SoftTFIDFBuilder getIntance(){
		if(instance == null) instance = new SoftTFIDFBuilder();
		return instance;
	}

	public SoftTFIDFBuilder(){
		init();
	}

	public void init(){
		Tokenizer tokenizer = new SimpleTokenizer(false,true);
		distance = new SoftTFIDF(tokenizer,new JaroWinkler(),0.8);
		wrappers = new ArrayList<StringWrapper>();
	}

	public void build(List<Pair> pairs){
		build(pairs.toArray( new Pair[pairs.size()]));
	}
	
	public void build(Pair[] pairs){
		for(Pair p: pairs){
			String hpName = OntologyLoader.getHPTermName(p.getHPId());
			String mpName = OntologyLoader.getMPTermName(p.getMPId());
			add(hpName).add(mpName);
		}
		distance.train(new BasicStringWrapperIterator(wrappers.iterator()));
	}
	
	public void build(SoftTFIDFBuilder builder){
		this.wrappers.addAll(builder.getWrappers());
		distance.train(new BasicStringWrapperIterator(wrappers.iterator()));
	}

	public SoftTFIDFBuilder add(String s){
		wrappers.add(distance.prepare(s));
		return this;
	}
	
	public double score(String s1, String s2){
		return distance.score(s1, s2);
	}

	public SoftTFIDF getSoftTFIDF(){ return distance; }
	public List<StringWrapper> getWrappers(){ return this.wrappers; }
}
