package edu.ktlab.ontology.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtil {
	
	
	public static void writeObject(String file, Object obj) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(obj);
		out.close();
		fileOut.close();
	}
	
	public static <T> T readObject(String file) throws Exception{
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		T t = (T) in.readObject();
		in.close();
		fileIn.close();
		return t;
	}

}
