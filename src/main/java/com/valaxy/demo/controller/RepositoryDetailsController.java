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
            "    <title>Application Java pour le FYC version 3</title>\n" +
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
            "        <h1>Application Java pour le FYC</h1>\n" +
            "    </header>\n" +
            "    <main>\n" +
            "        <div class=\"info\">\n" +
            "            <h2>Présenté par le groupe : Emmanuel, Ruben, Noé & Nourdine</h2>\n" +
            "            <p>Bienvenue sur notre application Java spécialement conçue pour le FYC (Festival de l'Innovation et de la Créativité). Nous sommes un groupe d'étudiants en master 5 passionnés par le développement logiciel.</p>\n" +
            "        </div>\n" +
            "        <div class=\"info\">\n" +
            "            <h2>Les fonctionnalités impressionnantes de notre application :</h2>\n" +
            "            <ul>\n" +
            "                <li>Génération automatique de mèmes hilarants à partir de texte</li>\n" +
            "                <li>Traduction instantanée de code Java en poésie française</li>\n" +
            "                <li>Simulation de conversations entre intelligences artificielles pour animer les réunions ennuyeuses</li>\n" +
            "                <li>Recommandation de musique personnalisée basée sur l'humeur de l'utilisateur et la météo actuelle</li>\n" +
            "                <li>Conversion de café en code Java optimisé</li>\n" +
            "            </ul>\n" +
            "        </div>\n" +
            "        <div class=\"info\">\n" +
            "            <h2>Contactez-nous pour une démo en direct !</h2>\n" +
            "            <p>Envie d'en savoir plus sur notre incroyable application ? Contactez-nous dès maintenant pour une démonstration en direct :</p>\n" +
            "            <p>Email: emmanuel.ruben.noe.nourdine@master5.fyc</p>\n" +
            "            <p>Téléphone: +33 1 23 45 67 89</p>\n" +
            "        </div>\n" +
            "    </main>\n" +
            "    <footer>\n" +
            "        <p>&copy; 2024 Groupe Java FYC. Tous droits réservés.</p>\n" +
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
