package com.example.storyspeak.controller;

import com.example.storyspeak.domain.Vocabulary;
import com.example.storyspeak.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vocabulary")
public class VocabularyController {

    private final VocabularyService vocabularyService;

    @GetMapping
    public String vocabularyList(Model model) {
        List<Vocabulary> vocabularyList = vocabularyService.getAllWords();
        model.addAttribute("vocabularyList", vocabularyList);
        return "vocabulary";
    }

    @PostMapping("/add")
    public String addVocabulary(@RequestParam("word") String word) {
        vocabularyService.getMeaningAndSaveWord(word);
        return "redirect:/vocabulary";
    }

    @PostMapping("/delete")
    public String deleteVocabulary(@RequestParam("id") Long id) {
        vocabularyService.deleteWordById(id);
        return "redirect:/vocabulary";
    }
}
