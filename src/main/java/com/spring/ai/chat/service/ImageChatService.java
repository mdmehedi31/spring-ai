package com.spring.ai.chat.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageChatService {

    private final ChatModel chatModel;

    public ImageChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }


    private MimeType getImageMimeType(String type) {

        if(type.contains("jpeg")){
            return MimeTypeUtils.IMAGE_JPEG;
        }else if(type.contains("png")){
            return MimeTypeUtils.IMAGE_PNG;
        }else if(type.contains("gif")){
            return MimeTypeUtils.IMAGE_GIF;
        }

        return MimeTypeUtils.IMAGE_JPEG;

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

    public String convertImageToText(MultipartFile file, String message){
        String content = ChatClient.create(chatModel).prompt()
                .user(us -> us.text(message)
                        .media(getImageMimeType(file.getContentType()),
                                file.getResource()))
                .call().content();
        return content;
    }
}
