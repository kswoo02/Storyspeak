package com.example.storyspeak.controller;

import com.example.storyspeak.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for ad hoc image generation requests.  Exposes a simple
 * endpoint that accepts a title and theme and returns a URL to a
 * generated image using OpenAI's DALL·E (or equivalent) API.
 */
@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/image/cover")
    public ResponseEntity<String> generateCover(@RequestParam String title,
                                                @RequestParam String theme) {
        String url = imageService.createCover(title, theme);
        return ResponseEntity.ok(url);
    }
}