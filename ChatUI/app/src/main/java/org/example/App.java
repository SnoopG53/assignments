package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class App {
    private JFrame frame;
    private JPanel chatPanel;
    private JTextField inputField;
    private JScrollPane scrollPane;
    private List<JsonObject> messageHistory;
    private final String apiKey = System.getenv("apiKey");
    private static final String URL = System.getenv("URL");
    private static final String USER = System.getenv("USER");
    private static final String PASSWORD = System.getenv("PASSWORD");


    public App() {
        messageHistory = new ArrayList<>();
        setupUI();
        loadChatHistory();
    }

    private void setupUI() {
        frame = new JFrame("Chat UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());

        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.addActionListener(e -> {
            String userInput = inputField.getText();
            inputField.setText("");
            processUserInput(userInput);
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void processUserInput(String userInput) {
        try {
            if (userInput == null || userInput.trim().isEmpty()) {
                throw new EmptyInputException("Input cannot be empty!");
            }

            addMessageBubble("User:", userInput, true);

            JsonObject userMessage = new JsonObject();
            userMessage.addProperty("role", "user");
            userMessage.addProperty("content", userInput);
            messageHistory.add(userMessage);
            saveMessageToDatabase("user", userInput);

            String response = getChatResponse();
            addMessageBubble("Response:", response, false);

            JsonObject assistantMessage = new JsonObject();
            assistantMessage.addProperty("role", "assistant");
            assistantMessage.addProperty("content", response);
            messageHistory.add(assistantMessage);
            saveMessageToDatabase("assistant", response);

            chatPanel.revalidate();
            SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
        } catch (EmptyInputException e) {
            addMessageBubble("Error:", e.getMessage(), false);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            addMessageBubble("Response:", "Error: Unable to get response from OpenAI.", false);
        }
    }

    private void loadChatHistory() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT role, content FROM chat_history ORDER BY id ASC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String role = resultSet.getString("role");
                String content = resultSet.getString("content");
                addMessageBubble(role.equals("user") ? "User:" : "Response:", content, role.equals("user"));

                JsonObject message = new JsonObject();
                message.addProperty("role", role);
                message.addProperty("content", content);
                messageHistory.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            addMessageBubble("Error:", "Unable to load chat history.", false);
        }
    }

    private void saveMessageToDatabase(String role, String content) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO chat_history (role, content) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role);
            preparedStatement.setString(2, content);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            addMessageBubble("Error:", "Unable to save message to database.", false);
        }
    }

    private String getChatResponse() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-3.5-turbo");
        JsonArray messages = new JsonArray();
        for (JsonObject message : messageHistory) {
            messages.add(message);
        }
        requestBody.add("messages", messages);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 429) {
                Thread.sleep(5000);
                return getChatResponse();
            }
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            JsonObject jsonResponse = gson.fromJson(response.body().string(), JsonObject.class);
            return jsonResponse.getAsJsonArray("choices").get(0)
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("content").getAsString();
        }
    }

    private void addMessageBubble(String label, String message, boolean isUser) {
        JPanel messageBubble = new JPanel();
        messageBubble.setLayout(new BorderLayout());
        messageBubble.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel userLabel = new JLabel(label);
        userLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        JTextArea messageText = new JTextArea(message);
        messageText.setWrapStyleWord(true);
        messageText.setLineWrap(true);
        messageText.setEditable(false);
        messageText.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageText.setBorder(BorderFactory.createLineBorder(isUser ? Color.BLUE : Color.GRAY, 1));
        messageText.setBackground(Color.WHITE);
        messageText.setForeground(Color.BLACK);
        messageBubble.add(userLabel, BorderLayout.NORTH);
        messageBubble.add(messageText, BorderLayout.CENTER);
        chatPanel.add(messageBubble);
        chatPanel.revalidate();
    }

    class EmptyInputException extends Exception {
        public EmptyInputException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
