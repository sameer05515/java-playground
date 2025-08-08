package com.prem.vocab.controller;

import com.prem.vocab.model.*;
import com.prem.vocab.service.VocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class VocabularyController {
    private final VocabularyService service;

    @GetMapping("/")
    public String index(Model m) {
        m.addAttribute("totalWords", service.findAll().size());
        return "index";
    }

    @GetMapping("/viewVocabs")
    public String view(Model m) {
        m.addAttribute("words", reverse(service.findAll()));
        return "view-vocabs";
    }

    @GetMapping("/viewVocabsSimple")
    public String simple(Model m) {
        m.addAttribute("words", reverse(service.findAll()));
        return "view-vocabs-simple";
    }

    @GetMapping("/viewVocabsWithDivJquery")
    public String exercise(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "0") int count,
            Model m) {
        m.addAttribute("offset", offset);
        m.addAttribute("count", count);
        m.addAttribute("words", reverse(service.findAll()));
        return "view-vocabs-exercise";
    }

    @GetMapping(value = "/getVocabs", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public VocabResponse json(@RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "0") int count) {
        return service.getResponse(offset, count);
    }

    @GetMapping("/add-vocab")
    public String add() {
        return "add-vocab";
    }

    @GetMapping("/targetWifeRepairingReturning")
    public String target() {
        return "target-wife";
    }

    private List<WordEntry> reverse(List<WordEntry> x) {
        List<WordEntry> r = new ArrayList<>(x);
        Collections.reverse(r);
        return r;
    }
}