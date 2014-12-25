package edu.ktlab.phenominer.ontologymatching.ontology;

import org.obolibrary.oboformat.model.Frame;

public class FrameUtilExample {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";

	public static void main(String[] args) {
		OBOOntologyFactory oboFactory = OntologyLoader.loadOBOOntology(fileHPO);
		Frame frame = oboFactory.getFrame("HP:0003218");
		System.out.println(FrameUtil.getID(frame));
		System.out.println(FrameUtil.getTagName(frame));
		System.out.println(FrameUtil.getSynonyms(frame));
		System.out.println(FrameUtil.getDefine(frame));
	}

}
