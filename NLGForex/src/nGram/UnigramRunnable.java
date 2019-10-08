package nGram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class UnigramRunnable {
	
	public static String finalSentence;
	
public void uniRunnable (ArrayList <String> entries) {
		
		TreeMap <Double, String> finalEntries = new TreeMap <Double, String> ();
		
		
		UnigramModel um = new UnigramModel ();
		
		um.createModel();
		um.calculateProbability();
		um.testDistributionIsZero();
		
		
		for (int loop = 0; loop < entries.size(); loop ++) {
			
			double tempDub;
			
			// testing sentence
			String sentence = (String) entries.get(loop);
			//splits sentence
			String [] words = sentence.split(" ");
			
			tempDub = um.scoreFor(new ArrayList <String> (Arrays.asList(words)));					
			
			finalEntries.put(tempDub, sentence);
			
			
		}
	
		
		//System.out.println(finalEntries); 		
		
		//System.out.println(finalEntries.firstEntry());

		finalSentence = finalEntries.firstEntry().getValue();
		
	}

	
	

}
