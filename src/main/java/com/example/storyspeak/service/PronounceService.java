package com.example.storyspeak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Service that evaluates a user's pronunciation of a given target text.
 * The audio sample is transcribed using Whisper and then scored via
 * ChatGPT.  The returned feedback is descriptive rather than
 * numerical, focusing on encouragement and specific areas for
 * improvement.
 */
@Service
@RequiredArgsConstructor
public class PronounceService {

    private final OpenAIService openAI;

    /**
     * Assess the user's pronunciation by transcribing their audio and
     * comparing it against the expected target using ChatGPT.  A short
     * feedback message is generated describing pronunciation accuracy,
     * intonation and fluency.
     *
     * @param file   the uploaded audio
     * @param target the phrase or word the user attempted to pronounce
     * @return a feedback message
     */
    public String assessPronunciation(MultipartFile file, String target) {
        try {
            byte[] data = file.getBytes();
            String transcript = openAI.transcribeAudio(data);
            String system = "You are a pronunciation coach. Compare a student's spoken phrase with the expected text. " +
                    "Provide gentle feedback on pronunciation, intonation and stress. Mention specific phonemes that were pronounced well or need improvement.";
            String user = String.format("Expected: '%s'\nStudent said: '%s'", target, transcript);
            return openAI.chat(system, user);
        } catch (IOException e) {
            return "Failed to analyse pronunciation: " + e.getMessage();
        }
    }
}