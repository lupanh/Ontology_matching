package edu.ktlab.ontology.utils;

import java.util.HashMap;
import java.util.Map;

public class RandomIntGroupSelection {
	private int numOfElements;
	private int numOfGroups;

	private RandomIntSelection randomInt;
	private Map<Integer, int[]> parts;

	public RandomIntGroupSelection(int numOfElements, int numOfGroups) {
		super();
		this.numOfElements = numOfElements;
		this.numOfGroups = numOfGroups;

		randomInt = new RandomIntSelection(0, numOfElements - 1);
		parts = new HashMap<Integer, int[]>();
	}

	public void populate(){
		int numOfGroupElements = (int) numOfElements/numOfGroups;
		int remainder = numOfElements % numOfGroupElements;
		
		for(int i = 0; i < numOfGroups; i++){
			int[] res = null;
			if(i < remainder)
				res = randomInt.nextInt(numOfGroupElements + 1);
			else 
				res = randomInt.nextInt(numOfGroupElements);
			parts.put(i+1, res);
		}
	}
	
	public int[] getPartAt(int index){
		return parts.get(index);
	}

	public int[] combinePartsExceptPartAt(int index){
		int sizeOfPart = parts.get(index).length;
		int[] res = new int[numOfElements - sizeOfPart];
		int count = 0;
		
		for(Integer key: parts.keySet()){
			if(key == index) continue;
			int[] els = parts.get(key);
			for(int i = 0; i < els.length; i++)
				res[count++] = els[i];
		}
		return res;
	}

	public int getNumOfElements() { return numOfElements; }
	public void setNumOfElements(int numOfElements) { this.numOfElements = numOfElements; }

	public int getNumOfGroups() { return numOfGroups; }
	public void setNumOfGroups(int numOfGroups) { this.numOfGroups = numOfGroups; }

	
	public static void main(String[] args){
		RandomIntGroupSelection groupSelection = new RandomIntGroupSelection(23, 10);
		groupSelection.populate();
		
		int[] res = groupSelection.getPartAt(2);
		System.out.println(res.length);
		
		res = groupSelection.combinePartsExceptPartAt(2);
		System.out.println(res.length);
	}
}
