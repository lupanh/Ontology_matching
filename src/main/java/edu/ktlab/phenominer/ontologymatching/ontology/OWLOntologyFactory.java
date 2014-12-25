package edu.ktlab.phenominer.ontologymatching.ontology;

import org.semanticweb.owlapi.model.OWLOntology;

public class OWLOntologyFactory {
	private OWLOntology ontology;

	public OWLOntologyFactory() {
	}

	public OWLOntologyFactory(OWLOntology ontology) {
		super();
		this.ontology = ontology;
	}

	public OWLOntology getOntology() {
		return ontology;
	}

	public void setOntology(OWLOntology ontology) {
		this.ontology = ontology;
	}
}
