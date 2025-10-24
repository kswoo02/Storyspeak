package com.example.storyspeak.service;

import com.example.storyspeak.domain.QuizQuestion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Service that generates vocabulary quizzes from a story.  The
 * {@code createQuiz} method extracts a set of important English words
 * from the bilingual story and requests definitions and distractors
 * from ChatGPT.  The response is parsed into a list of
 * {@link QuizQuestion} objects ready for presentation in the UI.
 */
@Service
@RequiredArgsConstructor
public class QuizService {

    private final OpenAIService openAI;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Create a list of quiz questions from the provided story.  Each
     * question consists of an English word, its correct definition and
     * two plausible but incorrect definitions (distractors).  The
     * returned list is limited to a maximum of three questions for
     * brevity.
     *
     * @param story the full story text
     * @return a list of quiz questions
     */
    public List<QuizQuestion> createQuiz(String story) {
        String system = "You are an English tutor creating vocabulary quizzes for a Korean student. " +
                "Given a bilingual children's story, extract three important English words. " +
                "For each word, provide the Korean translation of the word as the 'definition' and two plausible but incorrect Korean translations as 'distractors'. " +
                "Respond strictly as a JSON array where each element is an object with keys 'word', 'definition' and 'distractors'.";
        String user = story;
        String response = openAI.chat(system, user);
        List<QuizQuestion> questions = new ArrayList<>();
        if (response == null || response.isEmpty()) {
            return questions;
        }

        if (response.startsWith("```json")) {
            response = response.substring(7, response.length() - 3).trim();
        }

        try {
            JsonNode root = mapper.readTree(response);
            // Sometimes the response is a JSON object with a "questions" field
            if (root.isObject() && root.has("questions")) {
                root = root.get("questions");
            }
            if (root.isArray()) {
                for (JsonNode node : root) {
                    String word = node.path("word").asText();
                    String definition = node.path("definition").asText();
                    List<String> distractors = mapper.convertValue(node.path("distractors"), new TypeReference<List<String>>() {});
                    List<String> options = new ArrayList<>();
                    options.add(definition);
                    options.addAll(distractors);
                    Collections.shuffle(options);
                    QuizQuestion q = new QuizQuestion(word, definition, options);
                    questions.add(q);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse quiz from OpenAI response: " + response);
            e.printStackTrace();
            // fallback: return empty list
        }
        return questions;
    }
}