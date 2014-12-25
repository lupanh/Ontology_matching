package edu.ktlab.ontology.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RandomIntSelection {
	private List<Integer> ints;
	
	public RandomIntSelection(int min, int max){
		ints = new ArrayList<Integer>();
		
		for(int i = 0; i < max-min+1; i++)
			ints.add(min + i);
	}
	
	public int nextInt(){
		int maxIndex = ints.size();
		if(maxIndex == 0) return -1;
		Double value = Math.random()*maxIndex;
		int nextIdx = value.intValue();
		int nextValue = ints.get(nextIdx); 
		ints.remove(nextIdx);
		return nextValue;
	}
	
	public int[] nextInt(int n){
		if(n > ints.size()) n = ints.size();
		int[] res = new int[n];
		for(int i = 0; i < n; i++){
			int value = nextInt();
			if(value == -1) break;
			res[i] = value;
		}
		return res;
	}

	public static void main(String[] args) throws IOException{
		RandomIntSelection random = new RandomIntSelection(1, 10008);
		
		PrintStream out = new PrintStream("output.txt");
		for(int i: random.nextInt(10000))
			out.println(i);
		out.close();
	}
}
