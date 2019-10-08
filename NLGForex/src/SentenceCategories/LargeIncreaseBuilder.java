package SentenceCategories;

import java.util.ArrayList;
import java.util.Random;

import Data.JSONParser;
import User.FinalGUI;
import nGram.UnigramModel;

public class LargeIncreaseBuilder implements BuilderInterface {
	
	String date = JSONParser.getDate();
	float rate = JSONParser.getRate();
	String code = JSONParser.getCode();
	String dateHist = JSONParser.getDateHist();
	float rateHist = JSONParser.getRateHist();
	String srcCountry = JSONParser.getHistorical();
	String name =  JSONParser.getName();
	
	FinalGUI fg = new FinalGUI ();
	
	UnigramModel um = new UnigramModel ();
	
	public LargeIncreaseBuilder () {
		um.createModel();
	}

	@Override
	public ArrayList <String> sentences() {
		
		String tempSrc = null;
		ArrayList <String> sentences = new ArrayList <String> ();
		
		if (srcCountry.equals("15072019EUR.json")) {
			tempSrc = "EUR";
		} else if (srcCountry.equals("030719USD.json"))  {
			tempSrc = "USD";
		} else if (srcCountry.equals("15072019GBP.json"))  {
			tempSrc  = "GBP";
		}
		
		for (int loop = 0; loop < fg.getNumRandomS(); loop ++) {	
			
			String sentence, sentence2, sentence3;
			sentence = um.randomSentence(4) + " at the end of trading on " + date + " the " + tempSrc + " gained major ground against the " + code;
			sentence2 = "Following the " + date + um.randomSentence(1) + tempSrc + " continues its' rapid appreciation against " + code + um.randomSentence(4);
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
			
			temp  = begin[rand] + date + " the " + tempSrc + " made major gains on the " + 
			code;
			temp2 = begin[rand] + date + tempSrc +" continues its' rapid appreciation against " + name;
			temp3 = begin [rand] + date + " the " + name + " has moved shot up a significant amount from " + rateHist + 
			" to " + rate + " the " + tempSrc;
			temp4  = begin[rand] + date + "the" + tempSrc + " made major gains on the " + 
					code + " with regards to " + end[randEnd];
			temp5 = begin[rand] + date + tempSrc + " has made considerable gains against the " + name + end[randEnd];
			temp6 = begin [rand] + date + " the " + name + " has increased in value an exponensial amount from " + rateHist + 
					" to " + rate + " the " + tempSrc + end[randEnd];
			
			
			String [] strs = {temp, temp2, temp3, temp4, temp5, temp6};
			
			for (int loop2 = 0; loop2 < strs.length; loop2 ++) {
				sentences.add(strs[loop2]);
			}
		}
	
				
		
		return sentences;
		
	}
	

}
