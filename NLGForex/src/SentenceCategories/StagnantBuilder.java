package SentenceCategories;

import java.util.ArrayList;
import java.util.Random;

import Data.JSONParser;
import User.FinalGUI;
import nGram.UnigramModel;

public class StagnantBuilder implements BuilderInterface {
	
	String date = JSONParser.getDate();
	float rate = JSONParser.getRate();
	String code = JSONParser.getCode();
	String dateHist = JSONParser.getDateHist();
	float rateHist = JSONParser.getRateHist();
	String srcCountry = JSONParser.getHistorical();
	String name =  JSONParser.getName();
	
	FinalGUI fg = new FinalGUI ();
	

	public ArrayList <String> sentences () {
		String tempSrc = null;
		ArrayList <String> sentences = new ArrayList <String> ();
		
		if (srcCountry.equals("15072019EUR.json")) {
			tempSrc = "EUR";
		} if (srcCountry.equals("030719USD.json"))  {
			tempSrc = "USD";
		} if (srcCountry.equals("15072019GBP.json"))  {
			tempSrc  = "GBP";
		}
		
		for (int loop = 0; loop < fg.getNumRandomS(); loop ++) {
			String sentence, sentence2, sentence3;
			
			UnigramModel um = new UnigramModel ();
			um.createModel();
			
			// creates random sentences for evaluation
			sentence = "At the end of trading on the " + date + " there was little change in the price of " + tempSrc + " with regards to " + code +
			" " + um.randomSentence(4) + ".";
			sentence2 = "Following the " + date + " there was little change in the price of " + code + " " + um.randomSentence(5)
			+ ".";
			sentence3 = um.randomSentence(10);
			String [] strs = {sentence, sentence2, sentence3};
			

			
			for (int loop2 = 0; loop2 < strs.length; loop2 ++) {
				sentences.add(strs[loop2]);
			}
			
			for (int loop2 = 0; loop2 < sentences.size(); loop2 ++) {
				//System.out.println(sentences.get(loop));
			}
			
		}

			String temp, temp2, temp3, temp4, temp5, temp6;
			Phrases ph = new Phrases ();
			String [] begin = ph.begin();
			String [] end  = ph.ends();

			Random rBeg = new Random ();
			int rand = rBeg.nextInt(begin.length);
			Random rEnd = new Random ();
			int randEnd = rEnd.nextInt(end.length);
			
			for (int loop = 0; loop < fg.getNumRandomS(); loop ++) {
				
				temp  = begin[rand] + date + " there was little change in the price of " + 
				tempSrc + " with regards to the " + name;
				temp2 = begin[rand] + date + " the " + tempSrc + " remains stangnent against the " + name;
				temp3 = begin [rand] + date + " the " + code + " has moved little from " + rateHist + 
				" to " + rate + " the " + srcCountry + " remains stagnent.";
				temp4  = begin[rand] + date + " there was little change in the price of " + 
				tempSrc + " with regards to " + code + end[randEnd];
				temp5 = begin[rand] + date + " the " + tempSrc + " remains stangnent against the " + name + end[randEnd];
				temp6 = begin [rand] + date + " the " + code + " has moved little from " + rateHist + 
						" to " + rate + " the " + tempSrc + " remains stagnent." + end[randEnd];
				
				
				String [] strs = {temp, temp2, temp3, temp4, temp5, temp6};
				
				for (int loop2 = 0; loop2 < strs.length; loop2 ++) {
					sentences.add(strs[loop2]);
				}
			}
		
		return sentences;
		
	

		
	}
	

}
