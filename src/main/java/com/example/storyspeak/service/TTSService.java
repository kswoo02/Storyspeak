package com.example.storyspeak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service that wraps OpenAI's text‑to‑speech API.  It converts given
 * text into an MP3 byte array using the selected voice.  This service
 * simply delegates to {@link OpenAIService} and does not perform any
 * additional processing.
 */
@Service
@RequiredArgsConstructor
public class TTSService {

    private final OpenAIService openAI;

    /**
     * Synthesise text into speech and return the raw MP3 bytes.
     *
     * @param text  the input text
     * @param voice the voice preset; defaults to "nova"
     * @return the MP3 data
     */
    public byte[] synthesizeSpeech(String text, String voice) {
        return openAI.textToSpeech(text, voice);
    }
}