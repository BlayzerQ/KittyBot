package blayzer.kittybot;

import java.io.IOException;
import java.util.Random;

public class VK {
	
    private static String vkApi = "https://api.vk.com";
    private static String vkToken = "8f3ee2db997b009d9d8cb728c45bc8c28ea19694067ad0a6efb31ccfn1b5b56380972d3780831e23e834a";
    
    public static String getPosts(Integer groupID, Integer limit) {
        try { 
        	Random random = new Random();
			String response = Utils.readUrl(vkApi + "/method/wall.get?owner_id=" + groupID + 
        			"&offset=" + random.nextInt(limit) + "&count=1&access_token="+vkToken+"&v=5.62");
            return response;
        } 
        catch (IOException e)
        {
            return "{response: [{\"count\": 38889,\"items\": [{\"body\": \"Ошибка получения постов.\"}]}}";
        }  
    }
}
