package Data;

import java.io.IOException;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

public class Main {
	
	private static String urlUSD = "http://www.floatrates.com/daily/usd.json";
	private static String urlGBP = "http://www.floatrates.com/daily/gbp.json";
	private static String urlEUR = "http://www.floatrates.com/daily/eur.json";
	
	 @SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, JSONException, ParseException {
		 
		 /* runnable cast creates new historical comparison JSON objects and saves them to file */
		 
		 // build usd historical file 
		 JSONReader jr1 = new JSONReader (null, urlUSD, null);
		 jr1.saveHistorical();
		 
		 // builds gbp historical file
		 JSONReader jr2 = new JSONReader (null, urlGBP, null );
		 jr2.saveHistorical();
		 
		 // builds eur historical file
		 JSONReader jr3 = new JSONReader (null, urlEUR, null);
		 jr3.saveHistorical();
						 				   		  		    
		  }

}
