package edu.ktlab.phenominer.ontologymatching.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

public class StringJoinerExample {

	public static void main(String[] args) {
		List<String> tokens = new ArrayList<String>();
		tokens.add("hanoi");
		tokens.add("hanoi");
		tokens.add("hanoi");
		
		String text = Joiner.on(" ").join(tokens);
		System.out.println(text);		
	}

}
