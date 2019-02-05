package blayzer.kittybot;

import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
 
public class KittyBot extends TelegramLongPollingBot {
 
	String passmsg = "";
	
	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(new KittyBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
	@Override
	public String getBotUsername() {
		return "dismaykittymeowbot";
	}
 
	@Override
	public String getBotToken() {
		return "430312581:AABYV30j4pjw3cJ3IeouSBwRG2e8Oqt0nE2";
	}
 
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null && message.hasText()) {
			System.out.println("Входящяя команда: " + message.getText());
			if (message.getText().equals("/start"))
				sendMsg(message, "Привет, Я - котобот! Список комманд: /cats, /2ch, /memes, /pics, /joke", true);
			else if (message.getText().equals("/help"))
				sendMsg(message, "Список комманд: /cats, /2ch, /memes, /pics, /joke", true);
			else if (message.getText().equals("/cats")) {
				try {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-15538981, 10000));
					JSONObject response = (JSONObject) messages.get("response");
					String photo = Utils.getAttachMedia(response);
					sendPhoto(message, photo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if (message.getText().equals("/2ch")) {
				try {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-22751485, 1985));
					JSONObject response = (JSONObject) messages.get("response");
					String photo = Utils.getAttachMedia(response);
					sendPhoto(message, photo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if (message.getText().equals("/memes")) {
				try {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-87960594, 1985));
					JSONObject response = (JSONObject) messages.get("response");
					String photo = Utils.getAttachMedia(response);
					sendPhoto(message, photo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if (message.getText().equals("/pics")) {
				try {
					JSONObject messages = (JSONObject) new JSONParser().parse(VK.getPosts(-84428813, 10000));
					JSONObject response = (JSONObject) messages.get("response");
					String photo = Utils.getAttachMedia(response);
					sendPhoto(message, photo);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			else if(message.getText().equals("/joke")) {
				try {
					Random random = new Random();
					String url = "http://bash.im/byrating/" + random.nextInt(50);
					Document bashDoc = Jsoup.connect(url).get();
					Elements elements = bashDoc.select(".text");
					String randomQuote = elements
		                .get(random.nextInt(elements.size()))
		                .html()
		                .replace("<br>", "");
					sendMsg(message, randomQuote, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
				sendMsg(message, "Неизвестная команда. Используй /help", true);
			System.out.println("Ответ: " + passmsg);
		}
	}
 
	private void sendMsg(Message message, String text, boolean replymessage) {
		SendMessage msg = new SendMessage();
		msg.enableMarkdown(true);
		msg.setChatId(message.getChatId().toString());
		if(replymessage)
			msg.setReplyToMessageId(message.getMessageId());
		msg.setText(text);
		passmsg = text;
		try {
			sendMessage(msg);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	private void sendPhoto(Message message, String photo) {
		SendPhoto msg = new SendPhoto();
		msg.setChatId(message.getChatId().toString());
		msg.setPhoto(photo);
		passmsg = photo;
		//msg.setCaption("Photo");
		try {
			sendPhoto(msg);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
 
}