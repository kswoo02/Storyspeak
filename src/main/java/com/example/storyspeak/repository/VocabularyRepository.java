package com.example.storyspeak.repository;

import com.example.storyspeak.domain.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    Optional<Vocabulary> findByWord(String word);
}
