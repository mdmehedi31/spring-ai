package com.spring.ai.chat.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageChatService {

    private final ChatModel chatModel;
    private final ImageModel imageModel;

    public ImageChatService(ChatModel chatModel, ImageModel imageModel) {
        this.chatModel = chatModel;
        this.imageModel = imageModel;
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

    public String generateImageByText(String prompt){

        ImageResponse response = imageModel.call(
                new ImagePrompt(prompt,
                        OpenAiImageOptions.
                                builder()
                                .width(1792)
                                .height(1024)
                                .quality("hd")
                                .N(1)
                                .build())
        );

        return response.getResult().getOutput().getUrl();
    }
}
