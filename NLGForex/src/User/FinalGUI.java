package User;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import Data.JSONParser;
import nGram.BigramRunnable;
import nGram.TrigramRunnable;
import nGram.UnigramRunnable;

public class FinalGUI {

	private JFrame frame;
	private JLabel headingLbl;
	private JTabbedPane tabbedPane;
	private JPanel homePnl;
	private JPanel unigramPnl;
	private JLabel lblPleaseSelectA;
	private JLabel lblComparedTo;
	private JComboBox <String> sourceBx;
	private JComboBox <String> compareBx;
	private JTextArea textArea;
	
	// source URLS
	private static String urlUSD = "http://www.floatrates.com/daily/usd.json";
	private static String urlGBP = "http://www.floatrates.com/daily/gbp.json";
	private static String urlEUR = "http://www.floatrates.com/daily/eur.json";
	
	// historical source files
	private static String USDhist = "030719USD.json";
	private static String GBPhist = "15072019GBP.json";
	private static String EURhist = "15072019EUR.json";
	private JPanel bigramPnl;
	private JLabel lblPleaseSelectA_1;
	private JLabel lblComparedTo_1;
	private JComboBox<String> sourceBiBx;
	private JComboBox<String> compareBiBx;
	private JTextArea textArea_1;
	private JPanel trigramPnl;
	private JLabel lblPleaseSelectA_2;
	private JLabel lblComparedTo_2;
	private JComboBox<String> sourceTriBx;
	private JComboBox<String> compareTriBx;
	private JTextArea textArea_2;
	
	// 
	
	// number of sentences loop
	int numRandomS = 20;
	
	// Application State
	public enum STATE {
		UNI, 
		BI,
		TRI,
	}
	public static STATE state = STATE.UNI;
	private JButton btnValUni;
	private JButton btnDisUni;
	private JButton btnNewButton;
	private JButton btnValidate;
	private JButton btnDiscard;
	private JLabel lblImage;
	private JLabel lblWelcome;
	private JComboBox<String> sourceDataBx;
	private JComboBox<String> compareDataBx;
	private JLabel lblDataViewer;
	private JTextArea textDataArea;
	private JLabel lblName;
	private JLabel lblPrice;
	private JLabel lblPercentChange;
	private JTextArea textAreaName;
	private JTextArea textAreaPrice;
	private JTextArea textAreaPercent;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FinalGUI window = new FinalGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FinalGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 468, 526);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(getHeadingLbl());
		frame.getContentPane().add(getTabbedPane());
		frame.getContentPane().add(getBtnValidate());
		frame.getContentPane().add(getBtnDiscard());
	}
	
	private JLabel getHeadingLbl() {
		if (headingLbl == null) {
			headingLbl = new JLabel("Forex Text Generator");
			headingLbl.setBounds(0, 11, 257, 14);
		}
		return headingLbl;
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(0, 36, 452, 421);
			tabbedPane.addTab("Home", null, getHomePnl(), null);
			tabbedPane.addTab("Unigram", null, getUnigramPnl(), null);
			tabbedPane.addTab("Bigram", null, getBigramPnl(), null);
			tabbedPane.addTab("Trigram", null, getTrigramPnl(), null);
		}
		return tabbedPane;
	}
	private JPanel getHomePnl() {
		if (homePnl == null) {
			homePnl = new JPanel();
			homePnl.setLayout(null);
			homePnl.add(getLblWelcome());
			homePnl.add(getSourceDataBx());
			homePnl.add(getCompareDataBx());
			homePnl.add(getLblDataViewer());
			homePnl.add(getLblName());
			homePnl.add(getLblPrice());
			homePnl.add(getLblPercentChange());
			homePnl.add(getTextAreaName());
			homePnl.add(getTextAreaPrice());
			homePnl.add(getTextAreaPercent());
		}
		return homePnl;
	}
	private JPanel getUnigramPnl() {
		if (unigramPnl == null) {
			unigramPnl = new JPanel();
			unigramPnl.setLayout(null);
			unigramPnl.add(getLblPleaseSelectA());
			unigramPnl.add(getLblComparedTo());
			unigramPnl.add(getSourceBx());
			unigramPnl.add(getCompareBx());
			unigramPnl.add(getTextArea());
		}
		return unigramPnl;
	}
	private JLabel getLblPleaseSelectA() {
		if (lblPleaseSelectA == null) {
			lblPleaseSelectA = new JLabel("Please Select A Currency");
			lblPleaseSelectA.setBounds(10, 29, 150, 14);
		}
		return lblPleaseSelectA;
	}
	private JLabel getLblComparedTo() {
		if (lblComparedTo == null) {
			lblComparedTo = new JLabel("Compared To");
			lblComparedTo.setBounds(10, 130, 100, 14);
		}
		return lblComparedTo;
	}
	private JComboBox <String> getSourceBx() {
		if (sourceBx == null) {
			sourceBx = new JComboBox <String>();
			sourceBx.setBounds(10, 64, 119, 20);
		}
		
		sourceBx.addItem("Please Select....");
		sourceBx.addItem("USD");
		sourceBx.addItem("EUR");
		sourceBx.addItem("GBP");
		
		return sourceBx;
	}
	private JComboBox <String> getCompareBx() {
		if (compareBx == null) {
			compareBx = new JComboBox <String>();
			compareBx.setBounds(10, 167, 119, 20);
		}
		
		compareBx.addItem("Please Select....");
		compareBx.addItem("JPY");
		compareBx.addItem("AUD");
		compareBx.addItem("CAD");
		compareBx.addItem("PLN");
		compareBx.addItem("NOK");
		compareBx.addItem("SEK");
		compareBx.addItem("RUB");
		
		compareBx.addItemListener(new ItemListener() {
			// handles the comparables
			public void itemStateChanged (ItemEvent event) {
					state = STATE.UNI;
					
					try {
						progRunner(sourceBx, compareBx);
					} catch (JSONException | IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					textArea.setText(UnigramRunnable.finalSentence);
			}
		});
		
		return compareBx;
	}
	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setBounds(10, 230, 427, 163);
		}
		
		 	textArea.setWrapStyleWord(true);
		 	textArea.setLineWrap(true);
		    textArea.setOpaque(false);
		    textArea.setEditable(false);
		    textArea.setFocusable(false);
		
		return textArea;
	}
	private JPanel getBigramPnl() {
		if (bigramPnl == null) {
			bigramPnl = new JPanel();
			bigramPnl.setLayout(null);
			bigramPnl.add(getLblPleaseSelectA_1());
			bigramPnl.add(getLblComparedTo_1());
			bigramPnl.add(getSourceBiBx());
			bigramPnl.add(getCompareBiBx());
			bigramPnl.add(getTextArea_1());
		}
		return bigramPnl;
	}
	private JLabel getLblPleaseSelectA_1() {
		if (lblPleaseSelectA_1 == null) {
			lblPleaseSelectA_1 = new JLabel("Please Select A Currency");
			lblPleaseSelectA_1.setBounds(10, 29, 162, 14);
		}
		return lblPleaseSelectA_1;
	}
	private JLabel getLblComparedTo_1() {
		if (lblComparedTo_1 == null) {
			lblComparedTo_1 = new JLabel("Compared To");
			lblComparedTo_1.setBounds(10, 130, 122, 14);
		}
		return lblComparedTo_1;
	}
	private JComboBox<String> getSourceBiBx() {
		if (sourceBiBx == null) {
			sourceBiBx = new JComboBox <String>();
			sourceBiBx.setBounds(10, 59, 106, 20);
		}
		
		sourceBiBx.addItem("Please Select....");
		sourceBiBx.addItem("USD");
		sourceBiBx.addItem("EUR");
		sourceBiBx.addItem("GBP");
		
		return sourceBiBx;
	}
	private JComboBox<String> getCompareBiBx() {
		if (compareBiBx == null) {
			compareBiBx = new JComboBox <String>();
			compareBiBx.setBounds(10, 167, 106, 20);
		}
		
		compareBiBx.addItem("Please Select....");
		compareBiBx.addItem("JPY");
		compareBiBx.addItem("AUD");
		compareBiBx.addItem("CAD");
		compareBiBx.addItem("PLN");
		compareBiBx.addItem("NOK");
		compareBiBx.addItem("SEK");
		compareBiBx.addItem("RUB");
		
		compareBiBx.addItemListener(new ItemListener() {
			public void itemStateChanged (ItemEvent event) {
					state = STATE.BI;
					
					try {
						progRunner(sourceBiBx, compareBiBx);
					} catch (JSONException | IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					textArea_1.setText(BigramRunnable.finalSentence);
			}
		});
		
		return compareBiBx;
	}
	private JTextArea getTextArea_1() {
		if (textArea_1 == null) {
			textArea_1 = new JTextArea();
			textArea_1.setBounds(10, 220, 427, 173);
		}
		
		textArea_1.setWrapStyleWord(true);
	    textArea_1.setLineWrap(true);
	    textArea_1.setOpaque(false);
	    textArea_1.setEditable(false);
	    textArea_1.setFocusable(false);
		
		return textArea_1;	
	}
	private JPanel getTrigramPnl() {
		if (trigramPnl == null) {
			trigramPnl = new JPanel();
			trigramPnl.setLayout(null);
			trigramPnl.add(getLblPleaseSelectA_2());
			trigramPnl.add(getLblComparedTo_2());
			trigramPnl.add(getSourceTriBx());
			trigramPnl.add(getCompareTriBx());
			trigramPnl.add(getTextArea_2());
		}
		return trigramPnl;
	}
	private JLabel getLblPleaseSelectA_2() {
		if (lblPleaseSelectA_2 == null) {
			lblPleaseSelectA_2 = new JLabel("Please Select A Currency");
			lblPleaseSelectA_2.setBounds(10, 29, 154, 14);
		}
		return lblPleaseSelectA_2;
	}
	private JLabel getLblComparedTo_2() {
		if (lblComparedTo_2 == null) {
			lblComparedTo_2 = new JLabel("Compared To");
			lblComparedTo_2.setBounds(10, 130, 116, 14);
		}
		return lblComparedTo_2;
	}
	private JComboBox<String> getSourceTriBx() {
		if (sourceTriBx == null) {
			sourceTriBx = new JComboBox <String>();
			sourceTriBx.setBounds(10, 59, 116, 20);
		}
		
		sourceTriBx.addItem("Please Select....");
		sourceTriBx.addItem("USD");
		sourceTriBx.addItem("EUR");
		sourceTriBx.addItem("GBP");
		
		return sourceTriBx;
	}
	private JComboBox<String> getCompareTriBx() {
		if (compareTriBx == null) {
			compareTriBx = new JComboBox <String>();
			compareTriBx.setBounds(10, 166, 116, 20);
		}
		
		compareTriBx.addItem("Please Select....");
		compareTriBx.addItem("JPY");
		compareTriBx.addItem("AUD");
		compareTriBx.addItem("CAD");
		compareTriBx.addItem("PLN");
		compareTriBx.addItem("NOK");
		compareTriBx.addItem("SEK");
		compareTriBx.addItem("RUB");
		
		
		compareTriBx.addItemListener(new ItemListener() {
			public void itemStateChanged (ItemEvent event) {
					state = STATE.TRI;
			
					try {
						progRunner(sourceTriBx, compareTriBx);
					} catch (JSONException | IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					

					textArea_2.setText(TrigramRunnable.finalSentence);
			}
		});
		
		return compareTriBx;
	}
	private JTextArea getTextArea_2() {
		if (textArea_2 == null) {
			textArea_2 = new JTextArea();
			textArea_2.setBounds(10, 219, 427, 163);
		}
		
		textArea_2.setWrapStyleWord(true);
	    textArea_2.setLineWrap(true);
	    textArea_2.setOpaque(false);
	    textArea_2.setEditable(false);
	    textArea_2.setFocusable(false);
		
		return textArea_2;
	}

	public int getNumRandomS() {
		return numRandomS;
	}

	public void setNumRandomS(int numRandomS) {
		this.numRandomS = numRandomS;
	}

	
	public void addToCorpus (JTextArea textArea) {
	// validator - if sentence is acceptable, add it to the corpus
		Writer output;
		String fileName = "CorpusForex.txt";
		try {
			output = new BufferedWriter (new FileWriter(fileName , true));
			
			if (FinalGUI.state == FinalGUI.STATE.UNI) {
				textArea.setText(UnigramRunnable.finalSentence);
			}else if (FinalGUI.state == FinalGUI.STATE.BI) {
				textArea.setText(BigramRunnable.finalSentence);
			} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
				textArea.setText(TrigramRunnable.finalSentence);
			}
			
			output.append(" " + textArea.getText() + ".");
			output.close();
			System.out.println("it works");
		} catch (IOException e) {
			e.printStackTrace();
		}
		textArea.setText("Thanks for helping me learn!!!");
	}
	
	private JButton getBtnValidate() {
		if (btnValidate == null) {
			btnValidate = new JButton("Validate");
			btnValidate.setBounds(0, 454, 89, 23);
		}
		
		btnValidate.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (FinalGUI.state == FinalGUI.STATE.UNI) {
					addToCorpus(textArea);
				}else if (FinalGUI.state == FinalGUI.STATE.BI) {
					addToCorpus(textArea_1);
				} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
					addToCorpus(textArea_2);
				}
			
			}
		});
		
		return btnValidate;
	}
	
	private JButton getBtnDiscard() {
		if (btnDiscard == null) {
			btnDiscard = new JButton("Discard");
			btnDiscard.setBounds(252, 454, 89, 23);
		}
		
		btnDiscard.addActionListener (new ActionListener () {
		@Override
			public void actionPerformed(ActionEvent e) {
				// return the selections of the combobx's back to empty and empty the textArea
				if (FinalGUI.state == FinalGUI.STATE.UNI) {
					discardSent(textArea);
				}else if (FinalGUI.state == FinalGUI.STATE.BI) {
					discardSent(textArea_1);
				} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
					discardSent(textArea_2);
				}
			}
			
		});
		
		return btnDiscard;
	}
	
	public void discardSent (JTextArea textArea) {
				
		System.out.println("it works yasss");
		textArea.setText(null);
	}
	
// PLN, NOK, SEK, RUB
	
	public void progRunner (JComboBox <String> sourceBx, JComboBox <String> compareBx) throws JSONException, IOException, ParseException {
		// void deals with the selection of the country codes and runs the appropriate JSONParser
		// EUR src
		if (sourceBx.getSelectedItem().equals("EUR")) {
			if (compareBx.getSelectedItem().equals("JPY")) {
				JSONParser jp1 = new JSONParser ("jpy", urlEUR, EURhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("AUD")) {
				JSONParser jp1 = new JSONParser ("aud", urlEUR, EURhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("CAD")) {
				JSONParser jp1 = new JSONParser ("cad", urlEUR, EURhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("PLN")) {
				JSONParser jp1 = new JSONParser ("pln", urlEUR, EURhist);
				jp1.theParser();
			}else if (compareBx.getSelectedItem().equals("NOK")) {
				JSONParser jp1 = new JSONParser ("nok", urlEUR, EURhist);
				jp1.theParser();
			}else if (compareBx.getSelectedItem().equals("SEK")) {
				JSONParser jp1 = new JSONParser ("sek", urlEUR, EURhist);
				jp1.theParser();
			}else if (compareBx.getSelectedItem().equals("RUB")) {
				JSONParser jp1 = new JSONParser ("rub", urlEUR, EURhist);
				jp1.theParser();
			}
		// USD src
		} else if (sourceBx.getSelectedItem().equals("USD")) {
			if (compareBx.getSelectedItem().equals("JPY")) {
				JSONParser jp1 = new JSONParser ("jpy", urlUSD, USDhist);
				jp1.theParser();			
			} else if (compareBx.getSelectedItem().equals("AUD")) {
				JSONParser jp1 = new JSONParser ("aud", urlUSD, USDhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("CAD")) {
				JSONParser jp1 = new JSONParser ("cad", urlUSD, USDhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("PLN")) {
				JSONParser jp1 = new JSONParser ("pln", urlUSD, USDhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("NOK")) {
				JSONParser jp1 = new JSONParser ("nok", urlUSD, USDhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("SEK")) {
				JSONParser jp1 = new JSONParser ("sek", urlUSD, USDhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("RUB")) {
				JSONParser jp1 = new JSONParser ("rub", urlUSD, USDhist);
				jp1.theParser();
			}
		// GBP src	
		} else if (sourceBx.getSelectedItem().equals("GBP")) {
			if (compareBx.getSelectedItem().equals("JPY")) {
				JSONParser jp1 = new JSONParser ("jpy", urlGBP, GBPhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("AUD")) {
				JSONParser jp1 = new JSONParser ("aud", urlGBP, GBPhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("CAD")) {
				JSONParser jp1 = new JSONParser ("cad", urlGBP, GBPhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("PLN")) {
				JSONParser jp1 = new JSONParser ("pln", urlGBP, GBPhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("NOK")) {
				JSONParser jp1 = new JSONParser ("nok", urlGBP, GBPhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("SEK")) {
				JSONParser jp1 = new JSONParser ("sek", urlGBP, GBPhist);
				jp1.theParser();
			} else if (compareBx.getSelectedItem().equals("RUB")) {
				JSONParser jp1 = new JSONParser ("rub", urlGBP, GBPhist);
				jp1.theParser();
			}
		}
		

	}
	private JLabel getLblWelcome() {
		if (lblWelcome == null) {
			lblWelcome = new JLabel("Welcome To The Forex Text Generator");
			lblWelcome.setBounds(10, 11, 390, 14);
		}
		return lblWelcome;
	}
	private JComboBox<String> getSourceDataBx() {
		if (sourceDataBx == null) {
			sourceDataBx = new JComboBox <String>();
			sourceDataBx.setBounds(10, 59, 119, 20);
		}
		
		sourceDataBx.addItem("Please Select....");
		sourceDataBx.addItem("USD");
		sourceDataBx.addItem("EUR");
		sourceDataBx.addItem("GBP");
		
		return sourceDataBx;
	}
	private JComboBox<String> getCompareDataBx() {
		if (compareDataBx == null) {
			compareDataBx = new JComboBox <String>();
			compareDataBx.setBounds(10, 127, 119, 20);
		}
		
		compareDataBx.addItem("Please Select....");
		compareDataBx.addItem("JPY");
		compareDataBx.addItem("AUD");
		compareDataBx.addItem("CAD");
		compareDataBx.addItem("PLN");
		compareDataBx.addItem("NOK");
		compareDataBx.addItem("SEK");
		compareDataBx.addItem("RUB");
		
		compareDataBx.addItemListener(new ItemListener() {
			public void itemStateChanged (ItemEvent event) {
					state = STATE.TRI;
			
					try {
						progRunner(sourceDataBx, compareDataBx);
					} catch (JSONException | IOException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					
					// change this so that it spits out the relevant info
				textAreaName.setText(JSONParser.getName());
				float rate = JSONParser.getRate();
				String rateS = Float.toString(rate);
				textAreaPrice.setText(rateS);
				float hist = JSONParser.getRateHist();
				String histS = Float.toString(hist);
				textAreaPercent.setText(histS);
			}
		});
		
		return compareDataBx;
	}
	private JLabel getLblDataViewer() {
		if (lblDataViewer == null) {
			lblDataViewer = new JLabel("Data Viewer");
			lblDataViewer.setBounds(10, 224, 214, 14);
		}
		return lblDataViewer;
	}
	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel("Name:");
			lblName.setBounds(10, 259, 46, 14);
		}
		return lblName;
	}
	private JLabel getLblPrice() {
		if (lblPrice == null) {
			lblPrice = new JLabel("Price:");
			lblPrice.setBounds(249, 259, 37, 14);
		}
		return lblPrice;
	}
	private JLabel getLblPercentChange() {
		if (lblPercentChange == null) {
			lblPercentChange = new JLabel("Price Hist:");
			lblPercentChange.setBounds(10, 319, 71, 14);
		}
		return lblPercentChange;
	}
	private JTextArea getTextAreaName() {
		if (textAreaName == null) {
			textAreaName = new JTextArea();
			textAreaName.setBounds(55, 249, 100, 22);
		}
		
		textAreaName.setWrapStyleWord(true);
	    textAreaName.setLineWrap(true);
	    textAreaName.setOpaque(false);
	    textAreaName.setEditable(false);
	    textAreaName.setFocusable(false);
		
		return textAreaName;
	}
	private JTextArea getTextAreaPrice() {
		if (textAreaPrice == null) {
			textAreaPrice = new JTextArea();
			textAreaPrice.setBounds(291, 254, 68, 22);
		}
		
		textAreaPrice.setWrapStyleWord(true);
	    textAreaPrice.setLineWrap(true);
	    textAreaPrice.setOpaque(false);
	    textAreaPrice.setEditable(false);
	    textAreaPrice.setFocusable(false);
		
		return textAreaPrice;
	}
	private JTextArea getTextAreaPercent() {
		if (textAreaPercent == null) {
			textAreaPercent = new JTextArea();
			textAreaPercent.setBounds(106, 314, 253, 22);
		}
		
		textAreaPercent.setWrapStyleWord(true);
	    textAreaPercent.setLineWrap(true);
	    textAreaPercent.setOpaque(false);
	    textAreaPercent.setEditable(false);
	    textAreaPercent.setFocusable(false);
		
		return textAreaPercent;
	}
}

	


