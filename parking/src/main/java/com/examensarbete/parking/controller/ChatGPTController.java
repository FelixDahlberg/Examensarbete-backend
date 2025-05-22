package com.examensarbete.parking.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ChatGPTController {

    @Value("${openai.api.key}")
    private String apiKey;

    @PostMapping("/interpret")
    public ResponseEntity<?> interpretDescription(@RequestBody Map<String, String> body) throws IOException, InterruptedException {
        String userText = body.get("text");
        if (userText == null || userText.isBlank()) {
            return ResponseEntity.badRequest().body("Text saknas i förfrågan.");
        }

        String jsonPayload = String.format("""
            {
              "model": "gpt-3.5-turbo",
              "messages": [
                {
                  "role": "system",
                  "content": "Du är en expert på svenska trafikregler och hjälper till att tolka parkeringsskyltar tydligt och enkelt."
                },
                {
                  "role": "user",
                  "content": "Förklara dessa parkeringsskyltar för en vanlig bilförare: %s"
                }
              ],
              "temperature": 0.5,
              "max_tokens": 500
            }
        """, userText.replace("\"", "\\\"")); // Escape quotes

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        JsonNode choices = root.path("choices");
        if (choices.isArray() && !choices.isEmpty()) {
            JsonNode message = choices.get(0).path("message");
            String content = message.path("content").asText();

            Map<String, String> result = new HashMap<>();
            result.put("content", content);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body("OpenAI svarade utan giltigt innehåll. Hela svaret: " + response.body());
        }
    }
}