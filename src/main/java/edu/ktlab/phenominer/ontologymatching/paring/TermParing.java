package edu.ktlab.phenominer.ontologymatching.paring;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

import com.wcohen.secondstring.JaroWinkler;
import com.wcohen.secondstring.SoftTFIDF;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import edu.ktlab.phenominer.ontologymatching.ontology.OBOOntologyFactory;
import edu.ktlab.phenominer.ontologymatching.util.FileHelper;

public class TermParing {
	public static List<Pair<Frame, Frame>> pairing(OBOOntologyFactory hpo, OBOOntologyFactory mpo) {
		List<Pair<Frame, Frame>> framePairs = new ArrayList<Pair<Frame, Frame>>();
		for (Frame frame1 : hpo.getFrames())
			for (Frame frame2 : mpo.getFrames())
				framePairs.add(new Pair<Frame, Frame>(frame1, frame2));

		return framePairs;
	}
	
	public static List<Pair<Frame, Frame>> getPairBySoftTFIDF(OBOOntologyFactory hpo, OBOOntologyFactory mpo, double threshold) {
		SoftTFIDF softTFIDF = new SoftTFIDF(new JaroWinkler(), 0.9);
		List<Pair<Frame, Frame>> framePairs = new ArrayList<Pair<Frame, Frame>>();		
		for (Frame frame1 : hpo.getFrames())
			for (Frame frame2 : mpo.getFrames()) {
				if (softTFIDF.score(frame1.getTagValue(OboFormatTag.TAG_NAME).toString(), frame2.getTagValue(OboFormatTag.TAG_NAME).toString()) >= threshold)
					framePairs.add(new Pair<Frame, Frame>(frame1, frame2));
			}
				

		return framePairs;
	}
	
	public static List<Pair<Frame, Frame>> getPairByThreshold(OBOOntologyFactory hpo, OBOOntologyFactory mpo, AbstractStringMetric metric, double threshold) {
		List<Pair<Frame, Frame>> framePairs = new ArrayList<Pair<Frame, Frame>>();		
		for (Frame frame1 : hpo.getFrames())
			for (Frame frame2 : mpo.getFrames()) {
				if (metric.getSimilarity(frame1.getTagValue(OboFormatTag.TAG_NAME).toString(), frame2.getTagValue(OboFormatTag.TAG_NAME).toString()) >= threshold)
					framePairs.add(new Pair<Frame, Frame>(frame1, frame2));
			}
				

		return framePairs;
	}

	public static List<Pair<Frame, Frame>> getRandomPairs(OBOOntologyFactory hpo,
			OBOOntologyFactory mpo, int numPairs) {
		List<Pair<Frame, Frame>> framePairs = new ArrayList<Pair<Frame, Frame>>();
		List<Frame> hpoFrames = hpo.getShuffleListFrames();
		
		int count = 0;
		int numFramePerRound = numPairs / hpoFrames.size() + 1;
		int countFramePerRound = 0;
		for (Frame frame1 : hpoFrames) {
			List<Frame> mpoFrames = mpo.getShuffleListFrames();
			for (Frame frame2 : mpoFrames) {
				framePairs.add(new Pair<Frame, Frame>(frame1, frame2));
				count++;
				countFramePerRound++;
				if (count == numPairs)
					return framePairs;
				if (countFramePerRound == numFramePerRound)
					break;
			}
			countFramePerRound = 0;
		}			

		return framePairs;
	}

	public static List<Pair<Frame, Frame>> loadPairs(OBOOntologyFactory hpo, OBOOntologyFactory mpo, String file) {
		List<Pair<Frame, Frame>> framePairs = new ArrayList<Pair<Frame, Frame>>();
		String[] lines = FileHelper.readFileAsLines(new File(file), Charset.forName("UTF-8"));
		for (String line : lines) {
			String[] segs = line.split("\t");
			Frame frameHPO = hpo.getFrame(segs[0]);
			Frame frameMPO = mpo.getFrame(segs[1]);
			Pair<Frame, Frame> pair = new Pair<Frame, Frame>(frameHPO, frameMPO);
			framePairs.add(pair);
		}
		
		return framePairs;
	}

}
