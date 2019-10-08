package nGram;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

public class UnigramModel {
	TreeMap <String, Integer> wordFrequency = new TreeMap <String, Integer> ();
	TreeMap <String, Double> wordProbability = new TreeMap <String, Double> ();
	private double numberOfWords = 0;
	private FileInputStream fis;
	private BufferedReader br;
	private File file = new File("CorpusForex.txt");
	private BufferedInputStream bis;
	
	public UnigramModel() {
		
	}
	
	public double scoreFor(ArrayList <String> sent) {
		double score = 0;
		Double wordProb;
		
		for(String word : sent) {
			wordProb = this.wordProbability.get(word);
			if(wordProb != null) {
				score = score + wordProb; //addition rather than multiplication because we are working in logarthims
			}		
		}
		//System.out.println("Score in log form " + score);
		//System.out.println("Score in decimal " +Math.pow(10, score));
		
		return Math.pow(10, score);
		

	}
	
	public void createModel() {
		String line;
		String cleanedUpWord;
		String [] words;
		ArrayList <String> theWords;
		openFile();
		
		try {
			while ((line = br.readLine()) != null) {
	        	words = line.split(" ");
	        	words = addToFront(words, "<s>");
	        	theWords = new ArrayList <String>(Arrays.asList(words));
	        	theWords.add("</s>");
	     		for(String tempWord : theWords) {
	     			//display each word
	    			//System.out.println(tempWord);
	    			
	     			//tidy up - still leaves in - for adjectives
	     			cleanedUpWord = tempWord.replaceAll("[^a-zA-Z</>-]", "").toLowerCase();
	     			//System.out.println(tempWord + " becomes " +cleanedUpWord);
	    			if(this.wordFrequency.containsKey(cleanedUpWord)) {
	    				this.wordFrequency.put(cleanedUpWord, (this.wordFrequency.get(cleanedUpWord) +1));
	    			} else {
	    				this.wordFrequency.put(cleanedUpWord, 1);
	    			}
	    			this.numberOfWords++;
	    		}
	         }	
		} catch (IOException e) {
	       	e.printStackTrace();
	    }
		
	}
	
	public  String[] addToFront(String[] array, String toAdd) {
	    String[] result = new String[array.length + 1];
	    result[0] = toAdd;
	    for(int loop = 1; loop < result.length; loop++) {
	    	result[loop] = array[loop - 1];
	    }
	    return result;
	}
	
	public void calculateProbability() {
		String output = "";
		int num;
		double probability;
        //create output
        for(String tempKey : this.wordFrequency.keySet()) {
        	num =  this.wordFrequency.get(tempKey);
       	 	probability = (float) (num / this.numberOfWords);
       	 	//this.wordProbability.put(tempKey, probability);
       	 	//use logarithms to store probability
       	 	this.wordProbability.put(tempKey, Math.log10(probability));
       	 	
       	 	output = output +tempKey + "  " +Math.log10(probability) +"\n"; 
       	 	//System.out.println(output);
        }
        
		 try {
			FileOutputStream outputFile = new FileOutputStream("probability.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end calculateProbability
	
	public void printFrequency() {
		String output = "";
		int num;
	    //create output
        for(String tempKey : this.wordFrequency.keySet()) {
        	num =  this.wordFrequency.get(tempKey);
       	 	output = output +tempKey + "  " +num +"\n"; 
       	 	//System.out.println(output);
        }
        
		 try {
			FileOutputStream outputFile = new FileOutputStream("frequency.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end printFrequency

	public void testDistributionIsZero() {
		double sum = 0;
		for(double tempNum : this.wordProbability.values()) {
			sum = sum + tempNum;
		}
		System.out.println("Dsitribution is " +Math.pow(10, sum));
	}
		
	
	
	public void displayFrequency() {
		String output = "";
		int num;
		
        //create output
        for(String tempKey : wordFrequency.keySet()) {
       	 num = wordFrequency.get(tempKey);
       	 output = output +tempKey + "  " +num +"\n"; 
       	 //System.out.println(output);
        }
        
		 try {
			FileOutputStream outputFile = new FileOutputStream("frequency.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end displayFrequency
	
	public void openFile() {
	   try {
	    	this.fis = new FileInputStream(this.file);
	        this.bis = new BufferedInputStream(this.fis);
	        this.br = new BufferedReader(new InputStreamReader(this.bis));

	    } catch (FileNotFoundException e) {
	      	e.printStackTrace();
	    } 
	}//openFile
	
	
	public void closeFile() {
	    try {   
	         // dispose all the resources after using them.
	         this.fis.close();
	         this.bis.close();
	         this.br.close();

	    } catch (FileNotFoundException e) {
	      	e.printStackTrace();
	    } catch (IOException e) {
	       	e.printStackTrace();
	    }
	}

	public String randomSentence(int numberOfWords) {
		//Set <String> allWords = this.wordFrequency.keySet();
		Object [] allWords = this.wordFrequency.keySet().toArray();
		int size = allWords.length;
		Random numGenerator = new Random();
		String sentence = "";
		
		for(int loop = 0; loop < numberOfWords; loop++) {
			sentence = sentence + allWords[numGenerator.nextInt(size)] +" ";
		}
		//System.out.println(sentence);
		
		return sentence;
		
		
	}

}
