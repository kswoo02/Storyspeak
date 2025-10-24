package com.example.storyspeak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the StorySpeak JSP application.  This class is responsible
 * for bootstrapping the Spring Boot context and launching the embedded
 * servlet container.  Once started, the application exposes a small set
 * of controllers for rendering JSP pages that integrate with OpenAI's
 * REST APIs for story generation, text‑to‑speech and image generation.
 */
@SpringBootApplication
public class StorySpeakApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorySpeakApplication.class, args);
    }
}