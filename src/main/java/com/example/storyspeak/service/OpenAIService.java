package com.example.storyspeak.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * Centralised service for interacting with OpenAI's REST endpoints.  This
 * component encapsulates the logic required to call the chat, text‑to‑speech,
 * transcription and image APIs.  It uses Spring's {@link WebClient} to
 * perform asynchronous HTTP requests and block for results.  API keys and
 * base URLs are injected via configuration properties.
 */
@Service
@RequiredArgsConstructor
public class OpenAIService {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.base-url:https://api.openai.com/v1}")
    private String baseUrl;

    private WebClient getWebClient() {
        // Build WebClient with base URL and default headers
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Submit a chat completion request to the OpenAI API and return the
     * assistant's reply.  The method accepts both a system prompt and a
     * user prompt, constructs the appropriate JSON payload and extracts
     * the first choice from the response.
     *
     * @param systemPrompt the system prompt describing assistant behaviour
     * @param userPrompt   the user prompt containing instructions or input
     * @return the assistant's response content
     */
    public String chat(String systemPrompt, String userPrompt) {
        WebClient client = getWebClient();
        Map<String, Object> body = Map.of(
                "model", "gpt-4o",
                "messages", List.of(
                        Map.of("role", "system", "content", systemPrompt),
                        Map.of("role", "user", "content", userPrompt)
                ),
                "max_tokens", 800
        );
        Mono<JsonNode> response = client.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class);
        JsonNode root = response.block();
        if (root != null) {
            return root.path("choices").get(0).path("message").path("content").asText();
        }
        return "";
    }

    /**
     * Convert text into speech using OpenAI's text‑to‑speech API.  The
     * response is returned as a byte array representing the MP3 audio.
     *
     * @param text  the text to synthesise
     * @param voice the voice preset (e.g. "nova" or "echo")
     * @return the MP3 audio bytes
     */
    public byte[] textToSpeech(String text, String voice) {
        WebClient client = getWebClient();
        Map<String, Object> body = Map.of(
                "model", "tts-1",
                "input", text,
                "voice", voice
        );
        Mono<byte[]> response = client.post()
                .uri("/audio/speech")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(byte[].class);
        return response.block();
    }

    /**
     * Transcribe an audio file to text using OpenAI's Whisper model.  The
     * method builds a multipart request using {@link MultipartBodyBuilder}
     * because the audio must be sent as a file upload.
     *
     * @param data the audio data as byte array
     * @return the transcribed text
     */
    public String transcribeAudio(byte[] data) {
        WebClient client = getWebClient();
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", data)
                .header("Content-Type", "audio/mpeg")
                .filename("recording.mp3");
        builder.part("model", "whisper-1");
        Mono<JsonNode> response = client.post()
                .uri("/audio/transcriptions")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(JsonNode.class);
        JsonNode root = response.block();
        if (root != null) {
            return root.path("text").asText();
        }
        return "";
    }

    /**
     * Generate an image based on a prompt using OpenAI's image generation
     * endpoint.  Returns the URL of the first image in the response.
     *
     * @param prompt the textual description of the desired image
     * @return a URL pointing to the generated image
     */
    public String generateImage(String prompt) {
        WebClient client = getWebClient();
        Map<String, Object> body = Map.of(
                "model", "dall-e-3",
                "prompt", prompt,
                "n", 1,
                "size", "1024x1024"
        );
        Mono<JsonNode> response = client.post()
                .uri("/images/generations")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(JsonNode.class);
        JsonNode root = response.block();
        if (root != null) {
            return root.path("data").get(0).path("url").asText();
        }
        return "";
    }
}