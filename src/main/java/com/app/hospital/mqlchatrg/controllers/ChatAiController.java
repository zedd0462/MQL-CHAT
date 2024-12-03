package com.app.hospital.mqlchatrg.controllers;

import com.app.hospital.mqlchatrg.services.ChatAiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatAiController {

    private final ChatAiService chatAiService;

    public ChatAiController(ChatAiService chatAiService) {
        this.chatAiService = chatAiService;
    }

    /**
     * Endpoint pour poser une question en ajoutant un contexte provenant d'un fichier fixé.
     *
     * @param question La question posée.
     * @return La réponse de l'IA.
     */
    @GetMapping(value = "/ask", produces = MediaType.TEXT_PLAIN_VALUE)
    public String ask(@RequestParam String question) {
        return chatAiService.ragChatWithFixedFile(question);
    }
}