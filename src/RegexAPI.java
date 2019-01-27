import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//RegexApi is an APi to read content of an URL


public class RegexAPI {

	
	URLConnection bc;
	String url;
	String regex;
	String lookupKey;
	String text;
	public RegexAPI(String url) {
		this.url = url;
		try {
			bc = new URL(url).openConnection();
		} catch (MalformedURLException e) {
			
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		bc.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
    
	}
	// in case of looking a special keywork.
public void setKey(String key) {
	this.lookupKey = key;
}
public String parseTheLink(String url) throws IOException {
	this.url = url;
	
	try {
		bc = new URL(url).openConnection();
	} catch (MalformedURLException e) {
		
		System.out.println(e);
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	bc.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	return this.parseTheLink();
	
}
	
	
		public String parseTheLink() throws IOException 
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(bc.getInputStream()));
			String inputLine = "";
			text = "";
			while ((inputLine = in.readLine()) != null) 
			{

				text += inputLine + "\n";
			}
            
            
            in.close();
            return text;
		}
		
    public void regexM() { 
     Pattern pattern = Pattern.compile(regex);
 	Matcher matcher = pattern.matcher(text);
 	
 	while(matcher.find()) {
 		System.out.println(matcher.group(1));
 		System.out.println(matcher.group(2));
 		System.out.println(matcher.group(3));
 		System.out.println("------------------------------");
 	}
    }
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public void setUp() {}
}
