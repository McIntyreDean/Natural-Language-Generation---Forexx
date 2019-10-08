package nGram;

import java.util.ArrayList;
import java.util.TreeMap;

public class TrigramRunnable {
	
	public static String finalSentence;
	
	public void triRunnable (ArrayList <String> entries) {
		
		TreeMap <Double, String> finalEntries = new TreeMap <Double,String> ();
		
		TrigramModel tm = new TrigramModel ();
		
		for (int loop = 0; loop < entries.size(); loop ++) {
			
			double tempDub;
			
			String sentence = entries.get(loop);
			
			tempDub = tm.perplexityOf(sentence);
			
			finalEntries.put(tempDub, sentence);
		
		}
		
		//System.out.println(finalEntries.firstEntry().getValue());
		
		finalSentence  = finalEntries.firstEntry().getValue();
		
	}	
	
	
}
