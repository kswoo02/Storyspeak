package com.example.storyspeak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating images for the application's UI.
 * Specifically used to create a story cover illustration using the
 * OpenAI image generation API.  The prompt is kept general and
 * child‑friendly to produce colourful and appealing artwork.
 */
@Service
@RequiredArgsConstructor
public class ImageService {

    private final OpenAIService openAI;

    /**
     * Create a cover image for a story.  The prompt combines the title
     * and theme into a short description for the DALL·E API.  A
     * soft colour palette is encouraged by the phrasing.
     *
     * @param title the title of the story
     * @param theme the thematic element to guide the illustration
     * @return a URL to the generated image
     */
    public String createCover(String title, String theme) {
        String prompt = String.format("Book cover for a children's story titled '%s'. The image should include elements of %s and feature a friendly, pastel aesthetic.",
                title, theme);
        return openAI.generateImage(prompt);
    }
}