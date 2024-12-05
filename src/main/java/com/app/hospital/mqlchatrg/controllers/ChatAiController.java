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


    @GetMapping(value = "/ask", produces = MediaType.TEXT_PLAIN_VALUE)
    public String ask(@RequestParam String question) {
        return chatAiService.ragChatWithFixedFile(question);
    }
}