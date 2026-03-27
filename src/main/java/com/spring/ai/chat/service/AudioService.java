package com.spring.ai.chat.service;


import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Service
public class AudioService {

    private OpenAiAudioTranscriptionModel  openAiAudioTranscriptionModel;
    private OpenAiAudioSpeechModel audioSpeechModel;

    public AudioService(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel,OpenAiAudioSpeechModel audioSpeechModel) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
        this.audioSpeechModel = audioSpeechModel;
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


    public Resource convertTextToSpace(String text) {
        OpenAiAudioSpeechOptions options= OpenAiAudioSpeechOptions
                .builder()
                .model(OpenAiAudioApi.TtsModel.TTS_1.getValue())
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .speed(0.75)
                .build();

        TextToSpeechPrompt prompt = new TextToSpeechPrompt(text, options);

        TextToSpeechResponse response = audioSpeechModel.call(prompt);
        byte[] output = response.getResult().getOutput();

        ByteArrayResource resource = new ByteArrayResource(output);

       Resource file = resource;
       return file;

    }
}
