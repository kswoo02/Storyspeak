package com.example.storyspeak.controller;

import com.example.storyspeak.service.TTSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for converting text into speech via OpenAI's TTS endpoint.
 * This endpoint accepts a short text along with a desired voice and
 * returns an MP3 audio payload.  Although this feature is optional
 * within the application, it provides a convenient way for learners
 * to listen to the generated stories or vocabulary.
 */
@Controller
@RequiredArgsConstructor
public class TTSController {

    private final TTSService ttsService;

    /**
     * Convert the provided text into speech audio.  The resulting MP3
     * is streamed directly in the HTTP response with an appropriate
     * content type.  Supported voices include "nova", "onyx" and other
     * voices provided by OpenAI's API.
     *
     * @param text  the text to convert into speech
     * @param voice the voice name to use for synthesis
     * @return an HTTP response containing the MP3 audio bytes
     */
    @GetMapping("/tts")
    public ResponseEntity<byte[]> textToSpeech(@RequestParam String text,
                                               @RequestParam(defaultValue = "nova") String voice) {
        byte[] audio = ttsService.synthesizeSpeech(text, voice);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=tts.mp3")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(audio);
    }
}