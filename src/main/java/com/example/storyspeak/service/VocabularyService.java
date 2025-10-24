package com.example.storyspeak.service;

import com.example.storyspeak.domain.Vocabulary;
import com.example.storyspeak.repository.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final OpenAIService openAIService;

    public List<Vocabulary> getAllWords() {
        return vocabularyRepository.findAll();
    }

    public Vocabulary getMeaningAndSaveWord(String word) {
        // Check if the word already exists
        Optional<Vocabulary> existingWord = vocabularyRepository.findByWord(word);
        if (existingWord.isPresent()) {
            return existingWord.get();
        }

        // If not, get the meaning from OpenAI
        String systemPrompt = "You are an English-to-Korean dictionary. Provide a concise Korean meaning for the given English word.";
        String userPrompt = "Please provide the Korean meaning for the word: '" + word + "'";
        String meaning = openAIService.chat(systemPrompt, userPrompt);

        // Save the new word and its meaning
        Vocabulary newVocabulary = new Vocabulary(word, meaning);
        return vocabularyRepository.save(newVocabulary);
    }
}
