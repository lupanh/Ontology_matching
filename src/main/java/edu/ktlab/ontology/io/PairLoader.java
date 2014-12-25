package edu.ktlab.ontology.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.ktlab.ontology.paring.Pair;
import edu.ktlab.ontology.utils.FileUtil;

public class PairLoader {
	public static Pair[] loadDirectory(String directory) throws Exception {
		String[] files = FileUtil.findFiles("data", "([\\w]*).txt");
		if(files == null || files.length == 0) return null;

		List<Pair> holder = new ArrayList<Pair>();
		for(String file: files)
			for(Pair p: loadFile(file))
				holder.add(p);
		return holder.toArray(new Pair[holder.size()]);
	}
	
	public static Pair[] loadFile(String fname) throws Exception{
		List<Pair> holder = new ArrayList<Pair>();
		InputStreamReader inr = new InputStreamReader(new FileInputStream(fname), "UTF-8");
		BufferedReader input = new BufferedReader(inr);
		String line = "";
		while ((line = input.readLine()) != null) {
			Pair p = createPair(line.trim());
			if(p == null) continue;
			holder.add(p);
		}
		input.close();
		return holder.toArray(new Pair[holder.size()]);
	}
	
	private static Pair createPair(String line){
		String[] tks = line.split("\t");
		if(tks.length != 3) return null;
		return new Pair(tks[0], tks[1], tks[2]);
	}
	
	public static void main(String[] args) throws Exception{
		Pair[] pairs = PairLoader.loadFile("data/logmap_pair.txt");
		
		int count = 0;
		for(Pair p: pairs){
			double score = Double.parseDouble(p.getLabel());
			if(score < 0.8) continue;
			
			String hpName = OntologyLoader.getHPTermName(p.getHPId());
			String mpName = OntologyLoader.getMPTermName(p.getMPId());
			
			System.out.println(hpName + " | " + mpName + " | ");
			count ++;
		}
		
		System.out.println("Total : " + count);
		
		
	}
}
