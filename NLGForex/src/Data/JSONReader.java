package Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*Class reads the json object and can also make historical object for comparison*/

public class JSONReader {
	
	private static String countryCode;
	private static String url;
	private static String historical;
	
	public JSONReader (String countryCode, String url, String historical) {
		
		this.countryCode = countryCode;
		this.url = url;
		this.historical = historical;
				
	} // end constructor
	
	
	 private static String readAll(Reader rd) throws IOException, JSONException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	 
	 public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		    }
		  }
	 
	 public static String createLiveObject () throws JSONException, IOException  {		
		 
		 JSONObject obj = JSONReader.readJsonFromUrl(url);
			
		 	System.out.println(obj.get(countryCode));
			
			String output = obj.getJSONObject(countryCode).toString();
			
			return output;
		 
	 }
	 
	 public static String createHistorical () throws IOException, ParseException {
		 JSONParser parser = new JSONParser();
		 String output;		 
			 
			 Object obj = parser.parse(new FileReader(historical));
			 org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
				
			 output = jsonObject.get(countryCode).toString();
			 
			 
			 
			 return output;			
		
				 
	 }
	 
	public static void saveHistorical () throws JSONException, IOException {
		
		String fileName = JOptionPane.showInputDialog("Enter todays date and the currency");
		
		JSONObject obj = JSONReader.readJsonFromUrl(url);
		
		try (FileWriter file = new FileWriter(fileName + ".json"))
		{
			file.write(obj.toString());
			file.flush();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//System.out.println(obj.toString());
		
	}


	public static String getCountryCode() {
		return countryCode;
	}


	public static void setCountryCode(String countryCode) {
		JSONReader.countryCode = countryCode;
	}
	 



	
		

}




