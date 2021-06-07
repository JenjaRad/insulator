package com.eugene.insulator.controller;

import com.eugene.insulator.domain.Message;
import com.eugene.insulator.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/greeting")
public class MainController {
    private MessageRepository messageRepository;

    @Autowired
    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String greeting(Map<String, Object> map) {
        Iterable<Message> messages = messageRepository.findAll();
        map.put("messages", messages);
        return "test";
    }

    @PostMapping
    public String addMessage(@RequestParam(value = "text") String text, @RequestParam(value = "tag") String tag) {
        Message message = new Message(text, tag);
        messageRepository.save(message);
        return "redirect:/greeting";
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
        return "test";
    }
}
