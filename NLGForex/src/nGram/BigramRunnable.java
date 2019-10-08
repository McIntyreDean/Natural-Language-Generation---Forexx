package nGram;

import java.util.ArrayList;
import java.util.TreeMap;

public class BigramRunnable {
	
	public static String finalSentence;
	
	public void biRunnable (ArrayList <String> entries) {
		
		TreeMap <Double, String> finalEntries = new TreeMap <Double, String> ();
		
		BigramModel bm = new BigramModel ();
		
		for (int loop = 0; loop < entries.size(); loop ++) {
			double tempDub;
			
			String sentence = entries.get(loop);
			
			tempDub = bm.perplexityOf(sentence);
			
			finalEntries.put(tempDub, sentence);
		}
		
		//System.out.println(finalEntries.firstEntry().getValue());
		
		finalSentence = finalEntries.firstEntry().getValue();
		
	}

}
