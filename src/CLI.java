import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
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
		
		opt.addOption(Option.builder().argName("key")
				.hasArg()
				.required()
				.desc("Twitter OAuthConsumerKey")
				.build());	
		opt.addOption(Option.builder().argName("secret")
				.hasArg()
				.required()
				.desc("Twitter OAuthConsumerSecret")
				.build());	
		opt.addOption(Option.builder().argName("tkn")
				.hasArg()
				.required()
				.desc("Twitter OAuthAccessToken")
				.build());	
		opt.addOption(Option.builder().argName("tknSecret")
				.hasArg()
				.required()
				.desc("Twitter OAuthAccessTokenSecret")
				.build());	
		opt.addOption(Option.builder().argName("lang")
				.hasArg()
				.required()
				.desc("Twitter language code")
				.build());	
		
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
			System.err.println("Please refer to program usage.");
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
