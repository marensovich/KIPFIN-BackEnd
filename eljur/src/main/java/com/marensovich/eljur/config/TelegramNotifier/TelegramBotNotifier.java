package com.marensovich.eljur.config.TelegramNotifier;


public class TelegramBotNotifier {

    private final String botToken;
    private final String chatId;

    public TelegramBotNotifier(String botToken, String chatId) {
        this.botToken = botToken;
        this.chatId = chatId;
    }

    public void sendMessage(String message) {
        //try {
        //    String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        //    urlString = String.format(urlString, botToken, chatId, URLEncoder.encode(message, "UTF-8"));

        //    URL url = new URL(urlString);
        //    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //    connection.setRequestMethod("GET");

        //    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //    String inputLine;
        //    StringBuilder response = new StringBuilder();

        //    while ((inputLine = in.readLine()) != null) {
        //        response.append(inputLine);
        //    }
        //    in.close();

        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }
}
