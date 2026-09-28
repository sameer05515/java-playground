package com.p.smbg;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class GeetaController {
 private final GeetaService service;
 public GeetaController(GeetaService service){this.service=service;}
 @GetMapping({"/","/home"}) public String home(Model m){m.addAttribute("chapters",service.getChapterSummaries());return "home";}
 @GetMapping("/chapter/{chapterNo}") public String chapter(@PathVariable String chapterNo,Model m){m.addAttribute("chapter",service.getChapter(chapterNo));return "chapter";}
 @GetMapping("/chapter/{chapterNo}/verse/{verseNo}") public String verse(@PathVariable String chapterNo,@PathVariable String verseNo,Model m){m.addAttribute("chapter",service.getChapter(chapterNo));m.addAttribute("verse",service.getVerse(chapterNo,verseNo));return "verse";}
 @GetMapping("/word-meaning/{wordId}") public String word(@PathVariable String wordId,Model m){m.addAttribute("wordMeaning",service.getWordMeaning(wordId));return "wordMeaning";}
}