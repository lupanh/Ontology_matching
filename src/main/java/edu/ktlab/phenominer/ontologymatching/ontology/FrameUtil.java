package edu.ktlab.phenominer.ontologymatching.ontology;

import java.util.ArrayList;
import java.util.List;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

public class FrameUtil {
	public static String getID(Frame frame) {
		return frame.getId();
	}

	public static String getTagName(Frame frame) {
		return frame.getTagValue(OboFormatTag.TAG_NAME).toString();
	}
	
	public static String getDefine(Frame frame) {
		Object define = frame.getTagValue(OboFormatTag.TAG_DEF);
		if (define == null)
			return "";
		else
			return define.toString();
	}

	public static List<String> getSynonyms(Frame frame) {
		List<String> synonyms = new ArrayList<String>();

		for (Clause syn : frame.getClauses(OboFormatTag.TAG_SYNONYM)) {
			synonyms.add(syn.getValue().toString().toLowerCase());
		}
		return synonyms;
	}
}
