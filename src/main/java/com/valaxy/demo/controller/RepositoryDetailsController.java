package com.stalin.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


@RestController
public class RepositoryDetailsController {




    @Autowired
    private Environment env;

	@RequestMapping("/")
	public String getRepos() throws IOException {
		GitHub github = new GitHubBuilder().withPassword("valaxytech@gmail.com", "XXXXXXXX").build();
		GHRepositorySearchBuilder builder = github.searchRepositories();
		return "<!DOCTYPE html>\n" +
            "<html lang=\"fr\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Bienvenue à Paris</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            background-color: #f4f4f4;\n" +
            "            color: #333;\n" +
            "        }\n" +
            "        header {\n" +
            "            background-color: #007bff;\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 20px 0;\n" +
            "        }\n" +
            "        main {\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        footer {\n" +
            "            background-color: #333;\n" +
            "            color: white;\n" +
            "            text-align: center;\n" +
            "            padding: 10px 0;\n" +
            "            position: fixed;\n" +
            "            bottom: 0;\n" +
            "            width: 100%;\n" +
            "        }\n" +
            "        .info {\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <header>\n" +
            "        <h1>Bienvenue à Paris</h1>\n" +
            "    </header>\n" +
            "    <main>\n" +
            "        <div class=\"info\">\n" +
            "            <h2>À propos de Paris</h2>\n" +
            "            <p>Paris, la capitale de la France, est une ville emblématique réputée pour son histoire, sa culture, sa cuisine et son architecture magnifique. C'est l'une des destinations les plus visitées au monde.</p>\n" +
            "        </div>\n" +
            "        <div class=\"info\">\n" +
            "            <h2>Les attractions principales</h2>\n" +
            "            <ul>\n" +
            "                <li>La Tour Eiffel</li>\n" +
            "                <li>Le Louvre</li>\n" +
            "                <li>Notre-Dame de Paris</li>\n" +
            "                <li>Montmartre</li>\n" +
            "                <li>Champs-Élysées</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "        <div class=\"info\">\n" +
            "            <h2>Comment nous contacter</h2>\n" +
            "            <p>Si vous avez des questions ou avez besoin d'informations supplémentaires sur Paris, n'hésitez pas à nous contacter :</p>\n" +
            "            <p>Email: info@paris.fr</p>\n" +
            "            <p>Téléphone: +33 1 23 45 67 89</p>\n" +
            "        </div>\n" +
            "    </main>\n" +
            "    <footer>\n" +
            "        <p>&copy; 2024 Ville de Paris. Tous droits réservés.</p>\n" +
            "    </footer>\n" +
            "</body>\n" +
            "</html>";
	}

	@GetMapping("/trends")
	public Map<String, String> getTwitterTrends(@RequestParam("placeid") String trendPlace, @RequestParam("count") String trendCount) {
		String consumerKey = env.getProperty("CONSUMER_KEY");
		String consumerSecret = env.getProperty("CONSUMER_SECRET");
		String accessToken = env.getProperty("ACCESS_TOKEN");
		String accessTokenSecret = env.getProperty("ACCESS_TOKEN_SECRET");
		System.out.println("consumerKey "+consumerKey+" consumerSecret "+consumerSecret+" accessToken "+accessToken+" accessTokenSecret "+accessTokenSecret);		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		        .setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		System.out.println("Twitter Factory "+tf);
		System.out.println("Code testing purpose ");
		Twitter twitter = tf.getInstance();
		System.out.println("Twitter object "+twitter);
		Map<String, String> trendDetails = new HashMap<String, String>();
		try {
			Trends trends = twitter.getPlaceTrends(Integer.parseInt(trendPlace));
			System.out.println("After API call");
			int count = 0;
			for (Trend trend : trends.getTrends()) {
				if (count < Integer.parseInt(trendCount)) {
					trendDetails.put(trend.getName(), trend.getURL());
					count++;
				}
			}
		} catch (TwitterException e) {
			trendDetails.put("test", "MyTweet");
            //trendDetails.put("Twitter Exception", e.getMessage());
			System.out.println("Twitter exception "+e.getMessage());

		}catch (Exception e) {
			trendDetails.put("test", "MyTweet");
            System.out.println("Exception "+e.getMessage());
		}
		return trendDetails;
	}

}
