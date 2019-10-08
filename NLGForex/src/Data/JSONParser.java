package Data;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import SentenceCategories.LargeDecreaseBuilder;
import SentenceCategories.LargeIncreaseBuilder;
import SentenceCategories.SmallDecreaseBuilder;
import SentenceCategories.SmallIncreaseBuilder;
import SentenceCategories.StagnantBuilder;
import User.FinalGUI;
import nGram.BigramRunnable;
import nGram.TrigramRunnable;
import nGram.UnigramRunnable;

public class JSONParser {
	
	private static String date;
	private static float rate;
	private static String code;
	private static String dateHist;
	private static float rateHist;
	private static String name;
	
	private static String countryCode;
	private static String url;
	private static String historical;
	
	
	
	public JSONParser (String countryCode, String url, String historical) {
		this.countryCode = countryCode;
		this.url = url;
		this.historical = historical;
	}
	
	public static void theParser () throws JSONException, IOException, ParseException {
		 
		 JSONReader jr1 = new JSONReader(countryCode, url, historical);
		 
			JSONObject jsonObject = new JSONObject (jr1.createLiveObject());
			
			date = (String) jsonObject.getString("date");
			//System.out.println(date);
			rate = (float) jsonObject.getFloat("rate");
			//System.out.println(rate);
			code = (String) jsonObject.getString("code");
			//System.out.println(code);
			name =  (String) jsonObject.getString("name");
			
			JSONObject jsonObjectHist = new JSONObject (jr1.createHistorical());
			
			dateHist = (String) jsonObjectHist.getString("date");
			//System.out.println(dateHist);
			rateHist = (float) jsonObjectHist.getFloat("rate");
			//System.out.println(rateHist);

			float percentCh = ((rate - rateHist) / (rateHist)) * 100;
			
			System.out.println(percentCh + " percentage change");
			
			// positive
			if (percentCh > 0) {
			// stagnant builder
				if (percentCh <= 0.4) {
					StagnantBuilder sb = new StagnantBuilder ();
					sb.sentences();
					
					if (FinalGUI.state == FinalGUI.STATE.UNI) {
						UnigramRunnable ur = new UnigramRunnable ();
						ur.uniRunnable(sb.sentences());
						System.out.println(ur.finalSentence);
					}else if (FinalGUI.state == FinalGUI.STATE.BI) {
						BigramRunnable br = new BigramRunnable ();
						br.biRunnable(sb.sentences());
						System.out.println(br.finalSentence);
					} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
						TrigramRunnable tr = new TrigramRunnable ();
						tr.triRunnable(sb.sentences());
						System.out.println(tr.finalSentence);
					}
					System.out.println(percentCh + " Stagnant");
					
				// small positive	
				} else if (percentCh >= 0.4 && percentCh <= 0.99) {
					SmallIncreaseBuilder sib = new SmallIncreaseBuilder ();
					sib.sentences();
					
					if (FinalGUI.state == FinalGUI.STATE.UNI) {
						UnigramRunnable ur = new UnigramRunnable ();
						ur.uniRunnable(sib.sentences());
						System.out.println(ur.finalSentence);
					}else if (FinalGUI.state == FinalGUI.STATE.BI) {
						BigramRunnable br = new BigramRunnable ();
						br.biRunnable(sib.sentences());
						System.out.println(br.finalSentence);
					} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
						TrigramRunnable tr = new TrigramRunnable ();
						tr.triRunnable(sib.sentences());
						System.out.println(tr.finalSentence);
					}
					System.out.println(percentCh + " small increase");
					
				// large positive
				} else if (percentCh >= 1.0) {
					LargeIncreaseBuilder lib = new LargeIncreaseBuilder ();
					lib.sentences();
					
					if (FinalGUI.state == FinalGUI.STATE.UNI) {
						UnigramRunnable ur = new UnigramRunnable ();
						ur.uniRunnable(lib.sentences());
						System.out.println(ur.finalSentence);
					}else if (FinalGUI.state == FinalGUI.STATE.BI) {
						BigramRunnable br = new BigramRunnable ();
						br.biRunnable(lib.sentences());
						System.out.println(br.finalSentence);
					} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
						TrigramRunnable tr = new TrigramRunnable ();
						tr.triRunnable(lib.sentences());
						System.out.println(tr.finalSentence);
					}
					System.out.println(percentCh + " large increase");
				}
			//negative	
			} else {
				// stagnant negative
				if (percentCh >= -0.4) {
					StagnantBuilder sb = new StagnantBuilder ();
					sb.sentences();
					
					if (FinalGUI.state == FinalGUI.STATE.UNI) {
						UnigramRunnable ur = new UnigramRunnable ();
						ur.uniRunnable(sb.sentences());
						System.out.println(ur.finalSentence);
					}else if (FinalGUI.state == FinalGUI.STATE.BI) {
						BigramRunnable br = new BigramRunnable ();
						br.biRunnable(sb.sentences());
						System.out.println(br.finalSentence);
					} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
						TrigramRunnable tr = new TrigramRunnable ();
						tr.triRunnable(sb.sentences());
						System.out.println(tr.finalSentence);
					}
					System.out.println(percentCh + " Stagnant");
				// small negative	
			} else if (percentCh < -0.4 && percentCh >= -0.99) {
				SmallDecreaseBuilder sdb = new SmallDecreaseBuilder ();
				sdb.sentences();
				
				if (FinalGUI.state == FinalGUI.STATE.UNI) {
					UnigramRunnable ur = new UnigramRunnable ();
					ur.uniRunnable(sdb.sentences());
					System.out.println(ur.finalSentence);
				}else if (FinalGUI.state == FinalGUI.STATE.BI) {
					BigramRunnable br = new BigramRunnable ();
					br.biRunnable(sdb.sentences());
					System.out.println(br.finalSentence);
				} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
					TrigramRunnable tr = new TrigramRunnable ();
					tr.triRunnable(sdb.sentences());
					System.out.println(tr.finalSentence);
				}
				System.out.println(percentCh + " small decrease");
			// large negative	
			} else if (percentCh < -1.0) {
				LargeDecreaseBuilder ldb = new LargeDecreaseBuilder ();
				ldb.sentences();
				
				if (FinalGUI.state == FinalGUI.STATE.UNI) {
					UnigramRunnable ur = new UnigramRunnable ();
					ur.uniRunnable(ldb.sentences());
					System.out.println(ur.finalSentence);
				}else if (FinalGUI.state == FinalGUI.STATE.BI) {
					BigramRunnable br = new BigramRunnable ();
					br.biRunnable(ldb.sentences());
					System.out.println(br.finalSentence);
				} else if (FinalGUI.state == FinalGUI.STATE.TRI) {
					TrigramRunnable tr = new TrigramRunnable ();
					tr.triRunnable(ldb.sentences());
					System.out.println(tr.finalSentence);
				}
			}
		}
	}		

	public static String getCountryCode() {
		return countryCode;
	}

	public static void setCountryCode(String countryCode) {
		JSONParser.countryCode = countryCode;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		JSONParser.url = url;
	}

	public static String getHistorical() {
		return historical;
	}

	public static void setHistorical(String historical) {
		JSONParser.historical = historical;
	}

	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		JSONParser.date = date;
	}

	public static float getRate() {
		return rate;
	}

	public static void setRate(float rate) {
		JSONParser.rate = rate;
	}

	public static String getCode() {
		return code;
	}

	public static void setCode(String code) {
		JSONParser.code = code;
	}

	public static String getDateHist() {
		return dateHist;
	}

	public static void setDateHist(String dateHist) {
		JSONParser.dateHist = dateHist;
	}

	public static float getRateHist() {
		return rateHist;
	}

	public static void setRateHist(float rateHist) {
		JSONParser.rateHist = rateHist;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		JSONParser.name = name;
	}


}
