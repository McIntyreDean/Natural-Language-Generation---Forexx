package SentenceCategories;

import Data.JSONParser;


public class Phrases {
	
	String name = JSONParser.getName();

	public String [] begin () {
		// holds the phrases that are cycled through in the sentence builder
		
		// beginning of a sentence

			
		String begin = "At the end of trading on ";
		String begin2 = "Following ";
		String begin3 = "At the conclusion of trading on ";
		String begin4 = "According to latest trading ";
		String begin5 = "More bad news today for " + name + " traders ";
		String begin6 = "After a strong showing on ";
		String begin7 = "Concluding a weak day of trading on ";
		
		String [] beginnings = {begin, begin2, begin3, begin4, begin5, begin6, begin7};
		
		
		return beginnings;
		
	}
	
	public String [] ends () {
		
		// end of a sentence
		String end = " traders are increasingly tentative";
		String end2 = " some feel the market may pick up soon ";
		String end3 = " there is no sign of an end to the falling price of " + name;
		String end4 = " traders are concerned with the volatility of this currency ";
		String end5 = " this is a result of monetary policy changes";
		String end6 = " strong forecasts indicate an upturn in the market";
		String end7 = " latest analysis concludes that there is a strong market";
		String end8 = " feeling are held that the free fall will continue";
		String end9 = " the continued uncertainty over brexit is causing concern";
		String end10 = " despite brexit " + name + " remains strong";
		
		String [] ends = {end, end2, end3, end4, end5, end6, end7, end8, end9, end10};
		
		return ends;
	}

}
