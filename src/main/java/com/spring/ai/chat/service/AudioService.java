package com.spring.ai.chat.service;


import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AudioService {

    private OpenAiAudioTranscriptionModel  openAiAudioTranscriptionModel;

    public AudioService(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
    }

    public String convertVoiceToText(MultipartFile file, String language) {

        if(language==null)
            language="en";

        OpenAiAudioTranscriptionOptions openAiAudioTranscriptionOptions =
                OpenAiAudioTranscriptionOptions.builder()
                        .language(language)
                        .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                        .temperature(0.5f)
                        .build();

        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(file.getResource(), openAiAudioTranscriptionOptions);

        return openAiAudioTranscriptionModel.call(prompt).getResult().getOutput();
    }
}
