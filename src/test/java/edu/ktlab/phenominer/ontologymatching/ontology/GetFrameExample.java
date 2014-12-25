package edu.ktlab.phenominer.ontologymatching.ontology;

import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

public class GetFrameExample {
	static String fileHPO = "data/ontology/hp.18-02-14.obo";

	public static void main(String[] args) {
		OBOOntologyFactory oboFactory = OntologyLoader.loadOBOOntology(fileHPO);
		Frame frame = oboFactory.getFrame("HP:0100565");
		System.out.println(frame.getTagValue(OboFormatTag.TAG_NAME));
	}

}
