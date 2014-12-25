package edu.ktlab.phenominer.ontologymatching.ontology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;

public class OntologyLoaderExample {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";
	static String fileMPO = "data/ontology/mp.18-02-14.obo";

	public static void main(String[] args) {
		OBOOntologyFactory oboFactory = OntologyLoader.loadOBOOntology(fileHPO);
		List<Frame> listFrame;
		Collection<Frame> frames = oboFactory.getFrames();
		if (frames instanceof List)
			listFrame = (List<Frame>) frames;
		else
			listFrame = new ArrayList<Frame>(frames);
		for (Frame frame : listFrame) {
			System.out.println(frame.getId());
		}
	}

}
