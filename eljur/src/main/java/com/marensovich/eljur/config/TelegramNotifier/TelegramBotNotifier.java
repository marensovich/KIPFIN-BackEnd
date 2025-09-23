package com.marensovich.eljur.config.TelegramNotifier;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Telegram bot notifier.
 */
public class TelegramBotNotifier {

    private final String botToken;
    private final String chatId;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Instantiates a new Telegram bot notifier.
     *
     * @param botToken the bot token
     * @param chatId   the chat id
     */
    public TelegramBotNotifier(String botToken, String chatId) {
        this.botToken = botToken;
        this.chatId = chatId;
    }

    /**
     * Send message.
     *
     * @param message the message
     */
    public void sendMessage(String message) {
        try {
            Map<String, Object> payload = Map.of(
                    "chat_id", chatId,
                    "text", message,
                    "parse_mode", "MarkdownV2"
            );

            String json = objectMapper.writeValueAsString(payload);

            String urlString = "https://api.telegram.org/bot" + botToken + "/sendMessage";
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
            }

            if (connection.getErrorStream() != null) {
                String errorResponse = new BufferedReader(new InputStreamReader(connection.getErrorStream()))
                        .lines().collect(Collectors.joining("\n"));
                System.err.println("Telegram error: " + errorResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Escape markdown v 2 string.
     *
     * @param text the text
     * @return the string
     */
    public static String escapeMarkdownV2(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("~", "\\~")
                .replace("`", "\\`")
                .replace(">", "\\>")
                .replace("#", "\\#")
                .replace("+", "\\+")
                .replace("-", "\\-")
                .replace("=", "\\=")
                .replace("|", "\\|")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace(".", "\\.")
                .replace("!", "\\!");
    }
}
