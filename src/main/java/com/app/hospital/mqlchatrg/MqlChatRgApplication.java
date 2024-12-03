package com.app.hospital.mqlchatrg;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@SpringBootApplication
public class MqlChatRgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqlChatRgApplication.class, args);
    }

    /*@Bean
    CommandLineRunner runner(ChatClient.Builder chatClientBuilder) {
        return args -> {
            var chatClient = chatClientBuilder.build();

            var response = chatClient.prompt()
                    .user("What is Ai?")

                    .call()
                    .content();

            System.out.println(response);
        };
    }*/





}
