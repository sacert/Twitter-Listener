import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Main {
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	// filter english results
	public static final String langCode = "en";
	

	public static void main(String[] args) throws TwitterException, InterruptedException {
		
		// get user search result
		Scanner hashtagScan = new Scanner(System.in);
		System.out.print("Twitter Feed For: ");
	    String hashtag = hashtagScan.nextLine();
	    hashtagScan.close();
		
		ConfigurationBuilder cf = new ConfigurationBuilder();
		
		cf.setDebugEnabled(true)
		 		.setOAuthConsumerKey("")
		 		.setOAuthConsumerSecret("")
				.setOAuthAccessToken("")
				.setOAuthAccessTokenSecret("");
		
		TwitterFactory tf = new TwitterFactory(cf.build());
		twitter4j.Twitter twitter = tf.getInstance();
		
		// put all non-repeated tweets in here 
		List<String> tweetsList = new ArrayList<String>();
		
		// to change color of hashtags
		String[] hashing = null;
	
		// get the full tweet with modified colours
		String fullTweet = "";
		
		boolean firstTime = true;
		
        try {
            Query query = new Query("lang:" + langCode + " " + hashtag);
            QueryResult result;
            while(true) {
            	
            	// for the first time, don't wait to update
            	if(!firstTime)
            		Thread.sleep(5000);
            	firstTime = false;
            	
            	// check internet status
            	if(inetStatus() == false) {
            		break;
            	}
            	
            	// get tweet results into a list
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                
                for (Status tweet : tweets) {
                	
                	// initialize the tweet for each tweet
                	fullTweet = "";
                	
                	// if tweet is a link or retweet, dont display it
                	if(filterTweet(tweet)) {
                		String tweetString = "@" + tweet.getUser().getScreenName() + " - " + tweet.getText();
                		
                		// only print if not in list and is not a tweet specifically directed to someone
                		if(tweetCheck(tweetsList, tweet, tweetString)) {
                			tweetsList.add(tweetString);
                			
                			// parse tweet and determine color hashtag
                			hashing = tweet.getText().split(" |\n");
                			
                			prettierText(hashing);

                			// jumble the string back together
                			for(int i = 0; i < hashing.length; i++) {
                				fullTweet += hashing[i] + " ";
                			}
                			
                			System.out.println(ANSI_CYAN + "@" + tweet.getUser().getScreenName() + ANSI_RESET + " - " + fullTweet);
                			
                			// if there are 50 tweets within list, clear half of them to make room for another set
                			if(tweetsList.size() >= 50) {
                				for(int i = 0; i < 25; i++)
                					tweetsList.remove(i);
                			}
                		}
                	}
                }
            } 
            System.exit(1);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	// filter through tweets to get non-duplicates and are not directed at someone '@'
	static boolean tweetCheck(List<String> tweetsList, Status tweet, String tweetString) {
		return(!tweetsList.contains("@" + tweet.getUser().getScreenName() + " - " + tweet.getText()) &&  !tweet.getText().startsWith("@"));	
	}
	
	// modify tweet to add colour for terminal
	static void prettierText(String[] hashing) {
		
		for(int i = 0; i < hashing.length; i++) {

			if(hashing[i].startsWith("#")) {
				hashing[i] = ANSI_YELLOW + hashing[i] + ANSI_RESET;
			}
			if(hashing[i].startsWith("@")) {
				hashing[i] = ANSI_RED + hashing[i] + ANSI_RESET;
			}
		}
	}
	
	// filter through tweet to remove links and retweets
	static boolean filterTweet(Status tweet) {
		return ((!tweet.getText().contains("https://") && !tweet.getText().contains("http://")) && !tweet.isRetweet());
	}
	
	// check if connected to the internet
	public static boolean inetTest(String site) {
		Socket sk = new Socket();
		InetSocketAddress addr = new InetSocketAddress(site, 80);
		try {
			sk.connect(addr, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {sk.close();}
			catch (IOException e) {}
		}
	}
	
	public static boolean inetStatus() throws InterruptedException {
		boolean noInet = false;
		// check if there is a valid connect, if not, try to reconnect
		if(!inetTest("www.google.ca")) {
			System.out.println("ERROR: No Internet Connection");
			int retryCounter = 0;
			
			// attempt 3 reconnects to the internet
			while(retryCounter != 3) {
				System.out.println("Attempting to connect to internet...Retry " + (retryCounter+1) + "\r");
				Thread.sleep(15000);
				
				if(inetTest("www.google.ca")) {
					System.out.println("Reconnected");
					Thread.sleep(15000);
					noInet = false;
					break;
				}
				else {
					noInet = true;
				}
				retryCounter++;
			}
		}
		return !noInet;
	}
}
