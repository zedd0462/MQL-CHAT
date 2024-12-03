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

    // Nom du fichier (à chercher dans les ressources)
    private static final String FILE_NAME = "cvs.txt";

    public ChatAiService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Envoie une question à l'IA avec le contenu d'un fichier ajouté comme contexte.
     *
     * @param question La question posée.
     * @return La réponse de l'IA.
     */
    public String ragChatWithFixedFile(String question) {
        try {
            // Lire le contenu du fichier depuis les ressources
            String fileContent = readFileFromResources(FILE_NAME);
            // Construire le prompt
            String prompt = buildPrompt(question, fileContent);
            // Envoyer la requête à l'IA
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (IOException e) {
            return "Erreur lors de la lecture du fichier : " + e.getMessage();
        }
    }

    /**
     * Construit un prompt combinant le contexte et la question de l'utilisateur.
     *
     * @param question La question posée.
     * @param context Le contenu du fichier ajouté comme contexte.
     * @return Le prompt complet.
     */
    private String buildPrompt(String question, String context) {
        return "Voici des informations contextuelles à considérer :\n" + context + "\n\nQuestion : " + question + "\n\n être concise dans la réponse ";
    }

    /**
     * Lit le contenu d'un fichier dans les ressources du classpath.
     *
     * @param fileName Le nom du fichier.
     * @return Le contenu du fichier sous forme de chaîne.
     * @throws IOException Si une erreur de lecture survient.
     */
    private String readFileFromResources(String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IOException("Fichier non trouvé : " + fileName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}