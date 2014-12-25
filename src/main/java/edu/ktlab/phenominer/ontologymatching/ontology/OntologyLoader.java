package edu.ktlab.phenominer.ontologymatching.ontology;

import java.io.File;

import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;

<<<<<<< HEAD:src/main/java/edu/ktlab/phenominer/ontologymatching/ontology/OntologyLoader.java
=======
import edu.ktlab.ontology.OboOntology;
import edu.ktlab.ontology.OwlOntology;
import edu.ktlab.ontology.utils.ConstantConfigs;


>>>>>>> origin/master:src/main/java/edu/ktlab/ontology/io/OntologyLoader.java
public class OntologyLoader {
	private static OboOntology hpOntology = null;
	private static OboOntology mpOntology = null;

	public static OBOOntologyFactory loadOBOOntology(String filename) {
		try {
			OBOFormatParser parser = new OBOFormatParser();
			OBODoc doc = parser.parse(new File(filename));
			return new OBOOntologyFactory(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static OWLOntologyFactory loadOWLOntology(String filename) {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			org.semanticweb.owlapi.model.OWLOntology ontology = manager
					.loadOntologyFromOntologyDocument(new File(filename));
			return new OWLOntologyFactory(ontology);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
<<<<<<< HEAD:src/main/java/edu/ktlab/phenominer/ontologymatching/ontology/OntologyLoader.java
=======

	public static OboOntology getHPOntology(){
		if(hpOntology == null) 
			hpOntology = loadOboOntology(ConstantConfigs.DEFAULT_HP_ONTOLOGY_RESOURSE);
		return hpOntology;
	}

	public static OboOntology getMPOntology(){
		if(mpOntology == null) 
			mpOntology = loadOboOntology(ConstantConfigs.DEFAULT_MP_ONTOLOGY_RESOURSE);
		return mpOntology;
	}

	public static String getHPTermName(String hpId){
		return getHPOntology().getTermName(hpId);
	}

	public static String getMPTermName(String mpId){
		return getMPOntology().getTermName(mpId);
	}

	public static String[] getHPSynonyms(String hpId){
		return getHPOntology().getSynonyms(hpId);
	}
	
	public static String[] getMPSynonyms(String mpId){
		return getMPOntology().getSynonyms(mpId);
	}
	
	
	public static void main(String[] args){
		OboOntology o = OntologyLoader.getHPOntology();
		System.out.println(o.getTermName("HP:0004408"));
		o.getSynonyms("HP:0004408");

		System.out.println("*****************************");

		o = OntologyLoader.getMPOntology();
		System.out.println(o.getTermName("MP:0008119"));
		o.getSynonyms("MP:0008119");
	}
>>>>>>> origin/master:src/main/java/edu/ktlab/ontology/io/OntologyLoader.java
}
