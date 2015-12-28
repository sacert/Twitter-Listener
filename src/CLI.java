import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
/**
 * Takes in command line args, parses out twitter credentials for ease of use.
 * Exits if arguments are not given.
 * @author Dan
 *
 */
public class CLI {

	private String OAuthConsumerKey;
	private String OAuthConsumerSecret;
	private String OAuthAccessToken;
	private String OAuthAccessTokenSecret;
	private String langCode;
	
	public CLI(String[] args)
	{
		Options opt = new Options();
		opt.addOption("key",true,"Twitter OAuthConsumerKey");
		opt.addOption("secret",true,"Twitter OAuthConsumerSecret");
		opt.addOption("tkn",true,"Twitter OAuthAccessToken");
		opt.addOption("tknSecret",true,"Twitter OAuthAccessTokenSecret");
		opt.addOption("lang",true,"Twitter language code");
		
		for (Option o : opt.getOptions())
			o.setRequired(true);
		
		CommandLineParser cparse = new DefaultParser();
		try {
			CommandLine arguments = cparse.parse(opt, args);
			
			OAuthConsumerKey = arguments.getOptionValue("key");
			OAuthConsumerSecret = arguments.getOptionValue("secret");
			OAuthAccessToken = arguments.getOptionValue("token");
			OAuthAccessTokenSecret = arguments.getOptionValue("tokenSecret");
			langCode = arguments.getOptionValue("lang");
		} catch (ParseException pe){
			pe.printStackTrace();
			System.err.println("Incorrect Command Line Arguments.");
			System.exit(-1);
		}

	}

	public String getOAuthConsumerKey() {
		return OAuthConsumerKey;
	}

	public String getOAuthConsumerSecret() {
		return OAuthConsumerSecret;
	}

	public String getOAuthAccessToken() {
		return OAuthAccessToken;
	}

	public String getOAuthAccessTokenSecret() {
		return OAuthAccessTokenSecret;
	}

	public String getLangCode() {
		return langCode;
	}
}
