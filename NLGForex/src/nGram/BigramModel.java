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
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.TreeMap;

public class BigramModel {
	TreeMap <String, TreeMap <String, Double>> bigramCounts = new TreeMap <String, TreeMap <String, Double>> ();
	TreeMap <String, Integer> unigramCounts = new TreeMap <String, Integer> (); //unigram counts
	TreeMap <Integer, Integer> frequencyOfFrequencyCounts = new TreeMap <Integer, Integer> (); 
	//TreeMap <Double, String> cluesRankOrdered = new TreeMap <Double, String> ();
	//private TreeSet <RankedClue> allRankedClues = new TreeSet <RankedClue> ();
	TreeMap <String, TreeMap <String, Double>> biGramProbability;
	
	private FileInputStream fis;
	private BufferedInputStream bis;
	private BufferedReader br;
	
	/**
	 * Holds the number of words seen in the training data
	 * Required for doing the maths
	 */
	private double numberOfWords = 0;
	/**
	 * This variable holds the probability of unseen words
	 * The value of this depends on three factors
	 * 		1. The number of unique words seen in the training set
	 * 		2. The number of words seen once in the training set
	 * 		3. The value assigned to the variable  numberOfCommonlyUsedWordsInEnglish (see below)
	 */
	private double probabilityOfUnseenWord;
	/**
	 * Whether to work in decimal or logarithms
	 * Generally, set to false to work in logarithms
	 * Set to true with very small training sets to view probabilities in decimal
	 */
	private boolean decimal = false;
	/**
	 * Set to true to print all count and probabilities to files
	 * Writing to files takes time and slows the model down
	 * Only write to files when you need to inspect the files
	 */
	private boolean printToFile = false;
	/**
	 * Handled separately than other files because of its importance
	 * Generally, leave set to true as this is set to false automatically when dealling with large amounts of data
	 */
	private boolean perplexityLogFile = true;
	/** 
	 * Holds the details to write to the perplexity log file
	 */
	private String perplexityDetails = "";
	/**
	 * Determines whether working on data from a file or not
	 * Leave set to false as it is automatically set to true when bulk loading data 
	 */
	private boolean bulkLoad = false;
	/**
	 * The variable corpus is the corpus (or training set) that the model uses to build it's probability tables
	 */
	private File corpus = new File("CorpusForex.txt");
	
	private int numberOfCommonlyUsedWordsInEnglish = 1000;
	
	
	public BigramModel() {
		System.out.println("Building the bigram model, please be patient ...");
		long startTime;
		long endTime;
		
		startTime = System.nanoTime();
		
		calculateCounts();
		calculatProbabilityOfUnseenWords();
		calculateProbability();
		//printBigramCounts();
		
		endTime = System.nanoTime();
		double duration = (endTime - startTime) / 1000000000.0;
		System.out.println("I have built the Bigram in " +duration +" seconds \n\n");
		
	}
	
	
	public double perplexityOf(String sent) {
		//System.out.println("About to calculate perplexity");
		sent = sent.toLowerCase();
		double theProbability = 0;
		double thePerplexity;
		int length;
		boolean firstWord = true;
		String [] words;
		TreeMap <String, Double> existingEntry;
		ArrayList <String> theWords;
		double wordProbability;
		
		//convert to an array of words
		words = sent.split(" ");
		
		//convert to an ArrayList
		theWords = new ArrayList <String>(Arrays.asList(words));
		theWords.add(0, "<s>");
		theWords.add(theWords.size(), "</s>");
		length = theWords.size();
		
		//convert back to an array
		words = new String[theWords.size()];
		words = theWords.toArray(words);
		
				
		//iterate across the array of words
		for(int loop = 1; loop < words.length; loop++) {
			//if the preceeding word is listed in the probability table
			if(this.biGramProbability.containsKey(words[loop - 1])) {
				//word i-1 is found
				//System.out.println("word i-1 found " +words[loop - 1]);
				//System.out.println("word i " +words[loop]);
				//get a treemap of word/probability for the word we are dealing with (words[loop - 1])
				existingEntry = this.biGramProbability.get(words[loop - 1]);
				
				//test
				//System.out.println("FOR " +words[loop - 1]);
				//for(String tempWord : existingEntry.keySet()) {
					//System.out.println(tempWord);
				//}
				if(existingEntry.containsKey(words[loop])) {
					//word i is found and so the bigram is found
					//System.out.println("Word i found " +words[loop]);
					if(this.decimal) {
						//calculate the probability in decimal
						if(firstWord) {
							theProbability = this.biGramProbability.get(words[loop - 1]).get(words[loop]);
							if(this.perplexityLogFile) {
								this.perplexityDetails += "Bigram found " +words[loop - 1] + "  "  +words[loop] +" " +theProbability +"\n";	
							}
							firstWord = false;
						} else {
							if(this.perplexityLogFile) {
								this.perplexityDetails +=  "Bigram found " +words[loop - 1] + "  "  +words[loop] +" " +this.biGramProbability.get(words[loop - 1]).get(words[loop]) +"\n";
								this.perplexityDetails +=  "Multiplying " + theProbability +" * " +this.biGramProbability.get(words[loop - 1]).get(words[loop]) + " to get ";								
							}
							theProbability = theProbability * this.biGramProbability.get(words[loop - 1]).get(words[loop]);
							if(this.perplexityLogFile) {
								this.perplexityDetails += theProbability  +"\n";
							}
														
						}
					} else {
						//calculate the probability in logarithms
						if(this.perplexityLogFile) {
							this.perplexityDetails += "Bigram found " +words[loop - 1] + "  "  +words[loop] +" " +this.biGramProbability.get(words[loop - 1]).get(words[loop]) +"\n";
							this.perplexityDetails += "Adding " + theProbability +" + " +this.biGramProbability.get(words[loop - 1]).get(words[loop]) + " to get ";
						}
						theProbability = theProbability + this.biGramProbability.get(words[loop - 1]).get(words[loop]);
						if(this.perplexityLogFile) {
							this.perplexityDetails += theProbability +"\n";
						}
					}
				} else {
					//Bi gram not found 
					if(this.perplexityLogFile) {
						this.perplexityDetails += " Bigram " +words[loop -1] +" " +words[loop] +" not found \n";
					}
					wordProbability = calculateProbabilityOf(words[loop]);
					if(this.decimal) {
						//calculate the probability in decimal
						if(firstWord) {
							theProbability = wordProbability;
							if(this.perplexityLogFile) {
								this.perplexityDetails += words[loop - 1] + "  "  +words[loop] +" " +theProbability +"\n";
							}
							firstWord = false;
						} else {
							if(this.perplexityLogFile) {
								this.perplexityDetails += "Multiplying " + theProbability +" * " +wordProbability + " to get ";
							}
							theProbability = theProbability * wordProbability;
							if(this.perplexityLogFile) {
								this.perplexityDetails += theProbability +"\n";
							}
														
						}
					} else {
						//calculate the probability in logarithms
						wordProbability = Math.log10(wordProbability);
						if(this.perplexityLogFile) {
							this.perplexityDetails += "Adding " + theProbability +" + " +wordProbability + " to get ";
						}
						theProbability = theProbability + wordProbability;
						if(this.perplexityLogFile) {
							this.perplexityDetails += theProbability +"\n";
						}
					}
					
				}
				
			} else {
				//Bi gram not found - lets use the unigram count
				if(this.perplexityLogFile) {
					this.perplexityDetails += "Bigram " +words[loop -1] +" - " +words[loop] +" not found\n ";
				}
				wordProbability = calculateProbabilityOf(words[loop]);
				if(this.decimal) {
					//calculate the probability in decimal
					if(firstWord) {
						theProbability = wordProbability;
						if(this.perplexityLogFile) {
							this.perplexityDetails += words[loop - 1] + "  "  +words[loop] +" " +theProbability +"\n";
						}
						firstWord = false;
					} else {
						if(this.perplexityLogFile) {
							this.perplexityDetails += "Multiplying " + theProbability +" * " +wordProbability + " to get ";
						}
						theProbability = theProbability * wordProbability;
						if(this.perplexityLogFile) {
							this.perplexityDetails += theProbability +"\n";
						}
													
					}
				} else {
					//calculate the probability in logarithms
					wordProbability = Math.log10(wordProbability);
					if(this.perplexityLogFile) {
						this.perplexityDetails += "Adding " + theProbability +" + " +wordProbability + " to get ";
					}
					theProbability = theProbability + wordProbability;
					if(this.perplexityLogFile) {
						this.perplexityDetails += theProbability +"\n";
					}
				}
			}
		}
		
		//calculate the perplexity
		if(this.decimal) {
			thePerplexity = Math.pow(theProbability, (-1.0/length));
		} else {
			//convert back to decimal before calculating perplexity
			thePerplexity = Math.pow(Math.pow(10, theProbability), (-1.0/length));
		}
		
		//If using individual calls print details in console
		if(!this.bulkLoad) {
			//System.out.println(clue);
			//System.out.println("Probability = " +theProbability);
			//System.out.println("Perplexity = " +thePerplexity +"\n\n");	
		} 
		
		

		
		//Not happy with this here - but  will move when model is finalised
		if(this.perplexityLogFile  && !this.bulkLoad) {
			this.perplexityDetails += sent +"\n";
			this.perplexityDetails += "Probability " +theProbability +"\n";
			this.perplexityDetails += "Perplexity " +thePerplexity +"\n\n\n";
			
			printPerplexityDetails();
		}

		
		//System.out.println(thePerplexity +"   " +clue);
		return thePerplexity;
	}
	
	public double calculateProbabilityOf(String word) {
		int numberOfTimesSeen;
		double newCounts;
		double probability;
		double absoluteDiscount = 0.75;
		if(this.perplexityLogFile) {
			this.perplexityDetails += "Calculating the probability of " +word +"\n";
		}
		if(!this.unigramCounts.containsKey(word)) {
			if(this.perplexityLogFile) {
				this.perplexityDetails += "I have never seen this word before - probability = " +this.probabilityOfUnseenWord +"\n";
			}
			return this.probabilityOfUnseenWord;
			
		} else {
			numberOfTimesSeen = this.unigramCounts.get(word);
			if(this.perplexityLogFile) {
				this.perplexityDetails += "I have seen this word " +numberOfTimesSeen +" times before \n";
			}
			if(this.frequencyOfFrequencyCounts.containsKey(numberOfTimesSeen + 1)) {
				if(this.perplexityLogFile) {
					this.perplexityDetails += "I have Nc and Nc+1 \n";
					this.perplexityDetails += "C + 1 = " +(numberOfTimesSeen + 1) +"\n";
					this.perplexityDetails += "N2 = " +this.frequencyOfFrequencyCounts.get(numberOfTimesSeen + 1) +"\n";
					this.perplexityDetails += "N1 = " +this.frequencyOfFrequencyCounts.get(numberOfTimesSeen) +"\n";
					this.perplexityDetails += "N2/N1 = " +((double) this.frequencyOfFrequencyCounts.get(numberOfTimesSeen + 1) / (double) this.frequencyOfFrequencyCounts.get(numberOfTimesSeen)) +"\n";
				}
				newCounts = ((numberOfTimesSeen + 1) * (((double) this.frequencyOfFrequencyCounts.get(numberOfTimesSeen + 1)) / (double) this.frequencyOfFrequencyCounts.get(numberOfTimesSeen)));
				if(this.perplexityLogFile) {
					this.perplexityDetails += "New counts is " +newCounts +"\n";
				}
				probability = newCounts / this.numberOfWords;
				if(this.perplexityLogFile) {
					this.perplexityDetails += "Probability is " +probability +"\n";
				}
				return probability;
			} else {
				newCounts = numberOfTimesSeen - absoluteDiscount;
				probability = newCounts / this.numberOfWords;
				if(this.perplexityLogFile) {
					this.perplexityDetails += "I don't have Nc+1 ... using absolute discounting \n";
					this.perplexityDetails += "New counts is " +newCounts +"\n";
					this.perplexityDetails += "Probability is " +probability +"\n";
				}
				
				return probability;
			}
			
		}
	
	}
	
	public void calculateCounts() {
		String line;
		String theSentence;
		String [] words;
		ArrayList <String> theWords;
		ArrayList <String> theWordsTidiedUp;
		openFile();
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.UK);
		
		try {
			//read the text file line by line
			while ((line = br.readLine()) != null) {
				
				iterator.setText(line);
				int start = iterator.first();
				
				//breaks the line into sentences 
				for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
					theSentence = line.substring(start,end);
					//removes multiple spaces - they cause problems
					theSentence = theSentence.replaceAll("\\s+", " ");
					//System.out.println(theSentence);
					//System.out.println();
					//System.out.println(source.substring(start,end));
				
					words = theSentence.split(" "); //separate into words
					theWords = new ArrayList <String>(Arrays.asList(words));
					theWordsTidiedUp = new ArrayList<String> ();
					for(String word : theWords) {
						//System.out.println("Before " +word);
						//remove all non-alpha numeric characters
						word = word.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
						//System.out.println("After " +word);
						theWordsTidiedUp.add(word);
	        		}
					theWordsTidiedUp.add(0, "<s>");
					theWordsTidiedUp.add(theWordsTidiedUp.size(), "</s>");
					
					computeUnigramCount(theWordsTidiedUp);	        	
	        		        		        	
					//convert everything back to an array
					//It is easier to compute bigram counts this way
					String[] allWords = new String[theWordsTidiedUp.size()];
					allWords = theWordsTidiedUp.toArray(allWords);
	        	
					computeBigramCounts(allWords);
				}//end for loop that splits line into sentences
	        	
	        	
	         }//end while loop
			
			 computeFrequencyOfFrequencyCounts();
			
	 		 System.out.println("Final word count is " +this.numberOfWords);
	 		 System.out.println("Unique words is " +this.unigramCounts.size());
	 		 
	 		 //calculate number of bigrams - for printing only
	 		 int bigramCounts = 0;
	 		 TreeMap <String, Double> innerMap;
	 		 for(String word : this.bigramCounts.keySet()) {
	 			 innerMap = this.bigramCounts.get(word);
	 			 bigramCounts = bigramCounts + innerMap.size();
	 		 }
	 		System.out.println("Bigram count is " +bigramCounts);
	 		
		} catch (IOException e) {
	       	e.printStackTrace();
	    }
		
		
	}
	
	private void calculatProbabilityOfUnseenWords() {
		this.probabilityOfUnseenWord = (this.frequencyOfFrequencyCounts.get(1)/this.numberOfWords) / this.numberOfCommonlyUsedWordsInEnglish;
		System.out.println("Prob of unseen words is " +probabilityOfUnseenWord);
	}
	
	private void computeFrequencyOfFrequencyCounts() {
		
		for(Integer aCount : this.unigramCounts.values()) {
			//System.out.println("Found value " +aCount);
			if(this.frequencyOfFrequencyCounts.keySet().contains(aCount)) {
				//key already exists - add one to value
				this.frequencyOfFrequencyCounts.put(aCount, (this.frequencyOfFrequencyCounts.get(aCount) + 1));
			} else {
				//key doesn't exist set up with a value of 1
				//System.out.println("Setting up " +aCount);
				this.frequencyOfFrequencyCounts.put(aCount, 1);
			}
		}
		printFrequencyOfFrequencies();
	}
	
	private void computeBigramCounts(String [] allWords) {
		TreeMap <String, Double> newEntry;
		TreeMap <String, Double> oldEntry;
		boolean displayUpdates = false;
				
 		/*for testing
    	//print array
 		for(Object word : allWords) {
 			System.out.print(word + " ");
 		}
 		System.out.println("\n");
		*/
		
    	//do the raw bigram counts
    	for(int loop = 0; loop < allWords.length - 1; loop++) {
    		//for testing - display bigram word pairs
    		//System.out.println(allWords[loop] +"  " +allWords[loop + 1]);
    		if(this.bigramCounts.containsKey(allWords[loop])) {
    			//word already exists
    			if(displayUpdates) {
    				System.out.println("Found existing word " +allWords[loop]);	
    			}
    			oldEntry = this.bigramCounts.get(allWords[loop]);
    			if(oldEntry.containsKey(allWords[loop + 1])) {
    				//association already exists
    				//update counter
    				oldEntry.put((String) allWords[loop + 1], oldEntry.get((String) allWords[loop + 1]) + 1);
    				if(displayUpdates) {
    					System.out.println("Level 2 - Updating " + allWords[loop] +"  " +allWords[loop + 1]);	
    				}
    				this.bigramCounts.put((String) allWords[loop], oldEntry); 
    			} else {
    				//word does not exist
    				oldEntry.put((String) allWords[loop + 1], (double) 1);
    				if(displayUpdates) {
    					System.out.println("Level 2 - Adding " + allWords[loop] +"  " +allWords[loop + 1]);	
    				}
    				this.bigramCounts.put((String) allWords[loop], oldEntry);
    			}
    		} else {
    			//word does not exist
    			//create new key with next word value 1
    			newEntry = new TreeMap <String, Double> ();
    			newEntry.put((String) allWords[loop + 1], (double) 1);
    			if(displayUpdates) {
    				System.out.println("Level 1 - Adding " + allWords[loop] +"  " +allWords[loop + 1]);	
    			}
    			this.bigramCounts.put((String) allWords[loop], newEntry);
    		}//end if else
    	}//end for loop
    	if(this.printToFile) {
    		printBigramCounts();
    	}
    	
		
	}// end computeBigramCounts

	private void computeUnigramCount(ArrayList<String> theWords) {
		
		//for testing
    	//display each line in the console window
    	//for(String word : theWords) {
    		//System.out.print(word + " ");
    	//}
    	//System.out.println("\n");
    	
    	
    	//update frequency - unigram count
 		for(String tempWord : theWords) {

			if(this.unigramCounts.containsKey(tempWord)) {
				//word exists - add 1 to the count
				this.unigramCounts.put(tempWord, (this.unigramCounts.get(tempWord) +1));
			} else {
				//new word - add and set count to 1
				this.unigramCounts.put(tempWord, 1);
			}
			//increase word count
			this.numberOfWords++;
		}
 		if(this.printToFile) {
 			printUnigramCounts();
 		} 		
	} // end computeUnigramCount

	public void calculateProbability() {
		//System.out.println("About to calculate the probabilities");
		//copy the bigram count
		this.biGramProbability = this.bigramCounts;
		double wordsTogetherCount;
		int wordCount;
		double probability;
		boolean display = false;
		
		for(String outerWord : this.biGramProbability.keySet()) {
			for(String innerWord : this.biGramProbability.get(outerWord).keySet()) {
				if(display) {
					System.out.println("Testing " +outerWord +" and " +innerWord);	
				}
				wordsTogetherCount = this.biGramProbability.get(outerWord).get(innerWord);
				if(display) {
					System.out.println("Word together count " +wordsTogetherCount);	
				}
				wordCount = this.unigramCounts.get(outerWord.toLowerCase());
				if(display) {
					System.out.println("Word count " +wordCount);	
				}
			
				
				
				if(wordsTogetherCount == 0) {
						System.out.println("Found a zero");
				}
				probability = (double) wordsTogetherCount / wordCount;	
				
				
				if(display) {
					System.out.println(outerWord + " " +innerWord +" = " + probability);	
				}
				if(decimal) {
					//the code below leaves the probabilities in decimal - use only for inspecting probability file
					this.biGramProbability.get(outerWord).put(innerWord, probability);					
				} else {
					//Use log(prob)
					this.biGramProbability.get(outerWord).put(innerWord, Math.log10(probability));	
				}
				
			}
		}
		if(this.printToFile) {
			printProbability();
		}
		
	}

	public void printUnigramCounts() {
		String output = "";
		int num;
	    //create output
        for(String tempKey : this.unigramCounts.keySet()) {
        	num =  this.unigramCounts.get(tempKey);
       	 	output = output +tempKey + "  " +num +"\n"; 
       	 	//System.out.println(output);
        }
        
		 try {
			FileOutputStream outputFile = new FileOutputStream("unigramCounts.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end printFrequency
	
	public void printProbability() {
		String output = "";
		double probability;
		for(String outerWord : this.biGramProbability.keySet()) {
			for(String innerWord : this.biGramProbability.get(outerWord).keySet()) {
				probability = this.biGramProbability.get(outerWord).get(innerWord);
				output = output + outerWord.toUpperCase() +" " +innerWord + " " +probability +"\n\n";				
			}
			output = output +"\n";
		}
        
		 try {
			FileOutputStream outputFile = new FileOutputStream("biGramProbability.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end printFrequency
	
	
	public void printBigramCounts() {
		String output = "";
		String wordUpper;
		double number;
				
	   	for(String outerWord : this.bigramCounts.keySet()) {
    		for(String innerWord : this.bigramCounts.get(outerWord).keySet()) {
    			number = this.bigramCounts.get(outerWord).get(innerWord);
    			output = output +"BIGRAM - " +outerWord +"  " +innerWord +"  " +number +"\n";
    			
    		}
    	}
    	//System.out.println(output);
    	try {
			FileOutputStream outputFile = new FileOutputStream("bigramCounts.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printPerplexityDetails() {
    	//System.out.println(output);
    	try {
			FileOutputStream outputFile = new FileOutputStream("perplexityDetails.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(this.perplexityDetails);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printFrequencyOfFrequencies() {
		String output = "";
		Integer freqOfFreq;
				
    	for(Integer frequency : this.frequencyOfFrequencyCounts.keySet()) {
    		freqOfFreq = this.frequencyOfFrequencyCounts.get(frequency);
    		output = output +"N" +frequency +" = " +freqOfFreq +"\n";    		
    	}
    	//System.out.println(output);
    	try {
			FileOutputStream outputFile = new FileOutputStream("freqOfFreqCounts.txt");
			PrintStream printStream = new PrintStream(outputFile);
			printStream.print(output);
			printStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
	
	public void openFile() {
		   try {
		    	this.fis = new FileInputStream(this.corpus);
		        this.bis = new BufferedInputStream(this.fis);
		        this.br = new BufferedReader(new InputStreamReader(this.bis));

		    } catch (FileNotFoundException e) {
		      	e.printStackTrace();
		    } 
	}//openFile

}
