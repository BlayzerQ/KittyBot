package blayzer.kittybot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Utils {

	public static String readUrl(String url) throws IOException {
    	URL uri = new URL(url);
        URLConnection conURl = (URLConnection) uri.openConnection();
        conURl.setConnectTimeout(2000);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conURl.getInputStream(), "UTF-8"));
        String inputLine = in.readLine();
        in.close();
		//System.out.println(inputLine);
		return inputLine;
	}
	
	public static String getAttachMedia(JSONObject response) {
		try {
			JSONArray items = (JSONArray) response.get("items");
			JSONObject json = (JSONObject) items.get(0);
			JSONArray att = (JSONArray) json.get("attachments");
			JSONObject photo = (JSONObject) att.get(0);
			JSONObject media = (JSONObject) photo.get("photo");
			//System.out.println(media);
			String url = media.get("photo_604").toString();
			return url;
		} catch (IndexOutOfBoundsException e) {
			return "https://pp.userapi.com/c639516/v639516721/33037/JjSc0CDyutw.jpg";
		} catch (NullPointerException e) {
			return "https://pp.userapi.com/c639516/v639516721/33037/JjSc0CDyutw.jpg";
		}
	}
	
	public static String getRandomMessage(String... words) {
		Random random = new Random();
		return words[random.nextInt(words.length)];
	}
	
}
