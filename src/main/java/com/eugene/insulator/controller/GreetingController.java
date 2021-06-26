package com.eugene.insulator.controller;

import com.eugene.insulator.domain.Message;
import com.eugene.insulator.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/greeting")
public class GreetingController {

    private static final String REDIRECT_GREETING_MAIN = "redirect:/greeting/main";

    private MessageRepository messageRepository;

    @Autowired
    public GreetingController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String mainPage() {
        return "main/greeting";
    }

    @GetMapping("/main")
    public String greeting(Map<String, Object> map) {
        Iterable<Message> messages = messageRepository.findAll();
        map.put("messages", messages);
        return "main/form";
    }

    @PostMapping("/main")
    public String addMessage(@RequestParam(value = "text") String text, @RequestParam(value = "tag") String tag) {
        var message = new Message(text, tag);
        messageRepository.save(message);
        return REDIRECT_GREETING_MAIN;
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> map) {
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByText(filter);
        } else {
            messages = messageRepository.findAll();
        }
        map.put("messages", messages);
        return "main/form";
    }

}
