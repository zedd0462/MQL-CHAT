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

            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            // Validation : vérifier que la réponse contient des éléments du fichier
            if (!response.toLowerCase().contains("les informations fournies") && !fileContent.toLowerCase().contains(response.toLowerCase())) {
                return "Réponse jugée hors contexte. Impossible de répondre à la question avec les informations disponibles.";
            }

            return response;
        } catch (IOException e) {
            return "Erreur lors de la lecture du fichier : " + e.getMessage();
        }
    }



    private String buildPrompt(String question, String context) {
        return "Vous êtes un assistant intelligent. Répondez uniquement en utilisant les informations suivantes :\n"
                + context
                + "\n\nNe faites aucune supposition en dehors de ces informations. Si la réponse ne peut pas être déterminée avec ces informations, répondez : 'Les informations fournies ne permettent pas de répondre à cette question.'\n\n"
                + "Question : " + question
                + "\n\nRéponse :";
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