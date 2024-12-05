package com.app.hospital.mqlchatrg.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ChatAiService {

    private final ChatClient chatClient;


    private static final String FILE_NAME = "cvs.txt";

    public ChatAiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }


    public String ragChatWithFixedFile(String question) {
        try {

            String fileContent = readFileFromResources(FILE_NAME);

            String prompt = buildPrompt(question, fileContent);

            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (IOException e) {
            return "Erreur lors de la lecture du fichier : " + e.getMessage();
        }
    }


    private String buildPrompt(String question, String context) {
        return "Voici des informations contextuelles à considérer :\n" + context + "\n\nQuestion : " + question + "\n\n être concise dans la réponse ";
    }


    private String readFileFromResources(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Fichier non trouvé : " + fileName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}