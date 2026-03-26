package com.spring.ai.chat.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ImageChatService {

    private final ChatModel chatModel;

    public ImageChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String convertImageToText(){
        String response= ChatClient.create(chatModel).prompt().
                user(us -> us.text("Explain this images")
                        .media(MimeTypeUtils.IMAGE_JPEG,
                                new ClassPathResource("images/lab3_0ut.png")))
                .call().
                content();

    return response;
    }
}
