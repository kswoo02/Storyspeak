package com.example.storyspeak.domain;

import java.util.List;

/**
 * Represents a single multiple choice vocabulary question.  Each question
 * includes the target word, its correct meaning and a list of
 * candidate meanings to display as options in the UI.  The first
 * element in the options list must be the correct answer; options
 * should be shuffled before presentation to the user.
 */
public class QuizQuestion {
    private String word;
    private String correctMeaning;
    private List<String> options;

    public QuizQuestion() {
    }

    public QuizQuestion(String word, String correctMeaning, List<String> options) {
        this.word = word;
        this.correctMeaning = correctMeaning;
        this.options = options;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCorrectMeaning() {
        return correctMeaning;
    }

    public void setCorrectMeaning(String correctMeaning) {
        this.correctMeaning = correctMeaning;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}