package edu.ktlab.ontology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;


public class OboOntology {
	private OBODoc ontology;
	private Map<String, List<String>> terms;

	public OboOntology(){}

	public OboOntology(OBODoc ontology) {
		super();
		this.ontology = ontology;

		populate();
	}

	public OBODoc getOntology() { return ontology; }
	public void setOntology(OBODoc ontology) { this.ontology = ontology; }

	public void populate(){
		terms = new HashMap<String, List<String>>();

		Iterator<Frame> itr = ontology.getTermFrames().iterator();

		while (itr.hasNext()) {
			Frame frame = itr.next();
			if (frame.getTagValue(OboFormatTag.TAG_IS_OBSELETE) != null) continue;

			Iterator<Clause> parent = frame.getClauses(OboFormatTag.TAG_IS_A).iterator();
			while (parent.hasNext()) {
				Clause pr = parent.next();
				if(terms.containsKey(pr.getValue().toString()) == false){
					List<String> child = new ArrayList<String>();
					child.add(frame.getTagValue(OboFormatTag.TAG_NAME).toString());
					terms.put(pr.getValue().toString(), child);
				} else 
					terms.get(pr.getValue().toString()).add(frame.getTagValue(OboFormatTag.TAG_NAME).toString());
			}
		}
	}

	public String getTermName(String id){
		Frame term = ontology.getTermFrame(id);
		return term.getTagValue("name", String.class);
	}
	
	public String[] getSynonyms(String id){
		List<String> holder = new ArrayList<String>();
		Frame frame = ontology.getTermFrame(id);
		Iterator<Clause> synonyms = frame.getClauses(OboFormatTag.TAG_SYNONYM).iterator();
		while(synonyms.hasNext()){
			Clause synonym = synonyms.next();
			Object value = synonym.getValue();
			holder.add(value.toString().trim());
		}
		return holder.toArray(new String[holder.size()]);
	}

	public String[] getSiblings(String id){
		Frame frame = ontology.getTermFrame(id);
		Iterator<Clause> parents = frame.getClauses(OboFormatTag.TAG_IS_A).iterator();
		while (parents.hasNext()) {
			Clause pr = parents.next();
			System.out.println("**********************************");
			List<String> siblings = terms.get(pr.getValue());
			for(String siblingId: siblings)
				System.out.println(siblingId);
			
		}
		return null;
	}
}