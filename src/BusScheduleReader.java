import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class uses Regex API to read bus Schedules and Routes from user Input
public class BusScheduleReader {
	String Scheduleregex = "<hr.*/>\\s*<h3>\\s*(.*)</h3>(\\s*<div.*>\\s*<div.*>\\s.*\\s*.*\\s*.*\\s*</div>)*";
	String busNumberRegex = "<strong><a href=\"(.*)\">(.*)</a></strong>";
	String url = "https://www.communitytransit.org/busservice/schedules/";
	String routeUrl = "https://www.communitytransit.org/busservice/schedules/route/";
	String routeRegex ="<td.*>\\s*<h2>Weekday.*<small>(.*?)</small></h2>\\s*(.*?)\\s*.*\\s*(.*)\\s*(.*)\\s*(\\s*<th.*>\\s*<span.*>\\s*<i.*\\s*<strong.*\\s*</span>\\s*<p>(.*)</p>\\s*</th>\\s*)*\\s*";
	char lookupKey;
	String userIn;
	
	
	BusScheduleReader() throws IOException {
		
		RegexAPI ra = new RegexAPI(url);
		ra.setRegex(Scheduleregex);
		run(ra);
	}
		
		public void run(RegexAPI ra) throws IOException {
			Scanner reader = new Scanner(System.in);  // Reading from System.in
			
			System.out.println("Please enter a letter that your destinations start with:");
			lookupKey = reader.next().charAt(0); 
			getUserPreferedSchedule(ra, lookupKey);
			
			System.out.println("Please enter a route ID as a string:");
			String routeNumber= reader.next();
			printRoute(ra, routeNumber);
			}
		
		
		private void printRoute(RegexAPI ra, String routeNumber) throws IOException {
			routeUrl = routeUrl+routeNumber; // Concatenating the URL
			String busLine="";
	    	String destination;
	    	Pattern pattern = Pattern.compile(routeRegex);
	        Matcher matcher = pattern.matcher(ra.parseTheLink(routeUrl));
	    	
	    	while(matcher.find()) {
	    		System.out.printf("Destination : %s\n",matcher.group(1));
	    		destination= matcher.group(0);
	    		Pattern destinationPattern = pattern.compile("\\s*<p>(.*)</p>");  
	    		Matcher busMatcher = destinationPattern.matcher(destination);
	    		
	    		int counter= 1;
	    		while(busMatcher.find()) {
	    			System.out.printf("Stop Number: %d is  %s \n",counter++ , busMatcher.group(1));
	    			
	    		}
	    		System.out.printf("----------------------------------------------------------------------------\n");
	    	}
			
		}

		public void getUserPreferedSchedule(RegexAPI ra, char firstChar ) throws IOException {
		
		Pattern pattern = Pattern.compile(Scheduleregex);
		Matcher matcher = pattern.matcher(ra.parseTheLink());
		while(matcher.find()) {
				if (matcher.group(1).charAt(0) == Character.toLowerCase(firstChar) || matcher.group(1).charAt(0) == Character.toUpperCase(firstChar)) {
    		System.out.printf("Destination : %s\n",matcher.group(1));
    		String busLine = matcher.group(0);
    		Pattern busPattern = pattern.compile(busNumberRegex);  
    		Matcher busMatcher = busPattern.matcher(busLine);
    		while(busMatcher.find()) {
    			
    			System.out.printf("Bus Number %s \n",busMatcher.group(2));
    		}
    		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
    	}
		 
		}
		try {
			ra.parseTheLink();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		}
	
	
	public static void main(String[] args) throws IOException {
		BusScheduleReader bs = new BusScheduleReader();

	}

}
