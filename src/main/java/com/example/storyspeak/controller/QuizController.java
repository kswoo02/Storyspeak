package com.example.storyspeak.controller;

import com.example.storyspeak.domain.QuizQuestion;
import com.example.storyspeak.service.QuizService;
import com.example.storyspeak.service.VocabularyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller responsible for generating and processing vocabulary quizzes
 * based on the story content.  The quiz consists of multiple choice
 * questions where the user selects the correct meaning of a given word.
 */
@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final VocabularyService vocabularyService;

    /**
     * Create a new quiz from the provided story content.  The quiz is
     * stored in the user's session to persist across requests.  For
     * demonstration purposes we generate a single question per story.
     *
     * @param storyContent the full bilingual story text
     * @param session      the HTTP session used to store quiz questions
     * @param model        the view model
     * @return the name of the quiz JSP
     */
    @GetMapping("/quiz")
    public String startQuiz(@RequestParam("story") String storyContent,
                            HttpSession session,
                            Model model) {
        List<QuizQuestion> questions = quizService.createQuiz(storyContent);
        session.setAttribute("quizQuestions", questions);
        session.setAttribute("correctWords", new java.util.ArrayList<>());
        session.setAttribute("incorrectWords", new java.util.ArrayList<>());
        // present the first question
        if (!questions.isEmpty()) {
            model.addAttribute("question", questions.get(0));
            model.addAttribute("index", 0);
        }
        return "quiz";
    }

    /**
     * Handle the quiz submission for a given question.  The user's answer
     * is checked against the correct option.  If correct, the word is
     * appended to the list of correctly answered words in the session.
     * After processing the current question we either show the next
     * question or redirect to the results page.
     *
     * @param index    the current question index
     * @param answer   the answer selected by the user
     * @param session  the HTTP session storing quiz state
     * @param model    the view model
     * @return the next view
     */
    @PostMapping("/quiz/submit")
    public String submitAnswer(@RequestParam("index") int index,
                               @RequestParam("answer") String answer,
                               HttpSession session,
                               Model model) {
        List<QuizQuestion> questions = (List<QuizQuestion>) session.getAttribute("quizQuestions");
        List<String> correctWords = (List<String>) session.getAttribute("correctWords");
        if (questions == null || index >= questions.size()) {
            return "redirect:/result";
        }
        QuizQuestion current = questions.get(index);
        boolean correct = current.getCorrectMeaning().equals(answer);
        if (correct) {
            correctWords.add(current.getWord());
        } else {
            List<QuizQuestion> incorrectWords = (List<QuizQuestion>) session.getAttribute("incorrectWords");
            incorrectWords.add(current);
        }
        int nextIndex = index + 1;
        if (nextIndex < questions.size()) {
            model.addAttribute("question", questions.get(nextIndex));
            model.addAttribute("index", nextIndex);
            model.addAttribute("previousCorrect", correct);
            return "quiz";
        } else {
            session.setAttribute("quizQuestions", questions);
            session.setAttribute("correctWords", correctWords);
            model.addAttribute("previousCorrect", correct);
            return "redirect:/result";
        }
    }

    /**
     * Display the final results after the quiz is completed.  The list
     * of correctly answered words is passed to the view for display.
     *
     * @param session the HTTP session containing quiz state
     * @param model   the view model
     * @return the name of the results JSP
     */
    @GetMapping("/result")
    public String showResults(HttpSession session, Model model) {
        List<String> correctWords = (List<String>) session.getAttribute("correctWords");
        model.addAttribute("correctWords", correctWords);
        List<QuizQuestion> incorrectWords = (List<QuizQuestion>) session.getAttribute("incorrectWords");
        model.addAttribute("incorrectWords", incorrectWords);
        return "result";
    }

    @PostMapping("/quiz/addVocabulary")
    public String addVocabularyFromQuiz(@RequestParam("selectedWords") List<String> selectedWords) {
        for (String word : selectedWords) {
            vocabularyService.getMeaningAndSaveWord(word);
        }
        return "redirect:/vocabulary";
    }
}