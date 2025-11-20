package br.com.fiap.gs_rise.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ChatConfig {

    private final OpenAiChatModel model;

    @Bean
    public ChatClient chatClient() {
        return ChatClient.create(model);
    }
}
