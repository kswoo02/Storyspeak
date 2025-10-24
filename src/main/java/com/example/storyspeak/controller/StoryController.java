package com.example.storyspeak.controller;

import com.example.storyspeak.domain.Story;
import com.example.storyspeak.service.ImageService;
import com.example.storyspeak.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller responsible for creating stories based on user input.  The
 * incoming form contains a topic and CEFR level.  These values are passed to
 * the {@link StoryService} which interacts with the OpenAI Chat API to
 * generate a bilingual story.  After the story is generated, an image
 * representing the cover is retrieved from the {@link ImageService}.
 */
@Controller
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;
    private final ImageService imageService;

    /**
     * Handle the form submission that creates a new story.  The generated
     * story and cover image URL are placed into the model for rendering
     * by the JSP template.
     *
     * @param topic the user selected topic or phrase
     * @param level the selected CEFR level (e.g. A1, A2, B1)
     * @param model the model used to pass values to the view layer
     * @return the name of the JSP view to render
     */
    @PostMapping("/story/create")
    public String createStory(@RequestParam(required = false) String topic,
                              @RequestParam(name = "custom_topic", required = false) String customTopic,
                              @RequestParam String level,
                              Model model) {

        String selectedTopic = (customTopic != null && !customTopic.isEmpty()) ? customTopic : topic;

        if (selectedTopic == null || selectedTopic.trim().isEmpty()) {
            return "redirect:/";
        }

        Story story = storyService.createStory(selectedTopic, level);
        story.setContent(story.getContent().replace("\n", "|||"));
        String imageUrl = imageService.createCover(story.getTitle(), selectedTopic);
        model.addAttribute("story", story);
        model.addAttribute("imageUrl", imageUrl);
        return "story";
    }
}