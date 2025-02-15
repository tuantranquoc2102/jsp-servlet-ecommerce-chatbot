package com.ecommerce.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "ChatbotControl", value = "/chatbot")
public class ChatbotControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userMessage = request.getParameter("message");

        if (userMessage == null || userMessage.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Message cannot be empty\"}");
            return;
        }

        String chatbotResponse = callChatbotAPI(userMessage);
        response.setContentType("application/json");
        response.getWriter().write(chatbotResponse);
    }

    private String callChatbotAPI(String message) {
        try {
            URL url = new URL("http://chatbot-api:3000/chatbot");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = "{\"message\": \"" + message + "\"}";
            System.out.println("callChatbotAPI message: " + message);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
                System.out.println("OutputStream : " + message);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            System.out.println("BufferedReader : " + br);

            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("response : " + response.toString());
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"error\": \"Failed to connect to chatbot API\"}";
        }
    }
}