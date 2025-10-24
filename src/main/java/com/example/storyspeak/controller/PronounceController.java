package com.example.storyspeak.controller;

import com.example.storyspeak.service.PronounceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for accepting a user's spoken input and returning a simple
 * pronunciation assessment.  The uploaded audio is transcribed via
 * Whisper and then compared against the expected text of the story or
 * vocabulary.  The evaluation returned to the user is qualitative
 * rather than quantitative.
 */
@Controller
@RequiredArgsConstructor
public class PronounceController {

    private final PronounceService pronounceService;

    /**
     * Accept a recorded utterance and provide feedback on the user's
     * pronunciation.  The transcribed result and analysis are added to
     * the model for rendering.
     *
     * @param file   the uploaded audio file
     * @param target the expected phrase or word being practiced
     * @param model  the view model used for rendering results
     * @return the name of the JSP view to render
     */
    @PostMapping("/pronounce")
    public String assessPronunciation(@RequestParam("file") MultipartFile file,
                                     @RequestParam("target") String target,
                                     Model model) {
        String feedback = pronounceService.assessPronunciation(file, target);
        model.addAttribute("feedback", feedback);
        model.addAttribute("target", target);
        return "pronounce";
    }
}