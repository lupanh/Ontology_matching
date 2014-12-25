package edu.ktlab.phenominer.ontologymatching.ontology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;

public class OBOOntologyFactory {
	private OBODoc ontology;

	public OBOOntologyFactory() {
	}

	public OBOOntologyFactory(OBODoc ontology) {
		super();
		this.ontology = ontology;
	}

	public OBODoc getOntology() {
		return ontology;
	}

	public void setOntology(OBODoc ontology) {
		this.ontology = ontology;
	}

	public Collection<Frame> getFrames() {
		return ontology.getTermFrames();
	}

	public List<Frame> getShuffleListFrames() {
		List<Frame> listFrame;
		Collection<Frame> frames = getFrames();
		if (frames instanceof List)
			listFrame = (List<Frame>) frames;
		else
			listFrame = new ArrayList<Frame>(frames);
		Collections.shuffle(listFrame);
		return listFrame;
	}

	public Frame getFrame(String id) {
		return ontology.getTermFrame(id);
	}
}