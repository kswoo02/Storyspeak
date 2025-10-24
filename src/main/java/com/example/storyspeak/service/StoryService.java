package com.example.storyspeak.service;

import com.example.storyspeak.domain.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service responsible for orchestrating the creation of stories via
 * OpenAI's chat completion API.  Given a topic and CEFR level this
 * service constructs an appropriate prompt, invokes the chat API and
 * returns a populated {@link Story} object.  The returned content
 * contains interleaved Korean and English paragraphs suitable for
 * presentation in the UI.
 */
@Service
@RequiredArgsConstructor
public class StoryService {

    private final OpenAIService openAI;

    /**
     * Generate a bilingual children’s story.  The resulting story is
     * composed of six paragraphs, each containing one or two sentences
     * in Korean followed by the English translation.  The level is
     * incorporated into the system prompt to adjust the vocabulary
     * difficulty.
     *
     * @param topic the subject or theme of the story
     * @param level the CEFR difficulty level
     * @return the generated story
     */
    public Story createStory(String topic, String level) {
        String system = "You are a bilingual children's storyteller. " +
                "Write a six paragraph fairy tale in Korean followed by its English translation. " +
                "Each paragraph should be 1–2 sentences. Adjust the vocabulary to CEFR level " + level + ".";
        String user = String.format("주제: %s\n동화에서 따뜻하고 교훈적인 분위기를 전달하세요.", topic);
        String content = openAI.chat(system, user);
        Story story = new Story();
        story.setTitle(topic + " 이야기");
        story.setContent(content.trim());
        return story;
    }
}