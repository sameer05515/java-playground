package com.p.smbg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class GeetaService {

    private final List<Map<String, Object>> chapters;
    private final List<Map<String, Object>> chapterSummaries;
    private final List<Map<String, Object>> wordMeanings;

    public GeetaService(ObjectMapper mapper) throws IOException {
        this.chapters = read(mapper, "data/json/chapter-verse-detail-temp.json");
        this.chapterSummaries = read(mapper, "data/json/chapter-summary.json");
        this.wordMeanings = read(mapper, "data/json/chapter-verse-word-meaning-temp.json");
    }

    private List<Map<String, Object>> read(ObjectMapper mapper, String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        try (InputStream in = resource.getInputStream()) {
            return mapper.readValue(in, new TypeReference<List<Map<String, Object>>>() {});
        }
    }

    public List<Map<String, Object>> getChapters() {
        return chapters;
    }

    public List<Map<String, Object>> getChapterSummaries() {
        return chapterSummaries;
    }

    public Map<String, Object> getChapter(String id) {
        return chapters.stream()
                .filter(c -> id.equals(String.valueOf(c.get("id"))))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Chapter not found: " + id));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getVerse(String chapterNo, String verseNo) {
        Map<String, Object> chapter = getChapter(chapterNo);
        List<Map<String, Object>> verses = (List<Map<String, Object>>) chapter.get("verses");

        return verses.stream()
                .filter(v -> verseNo.equals(String.valueOf(v.get("id"))))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Verse not found: " + chapterNo + "/" + verseNo));
    }

    public Map<String, Object> getWordMeaning(String id) {
        return wordMeanings.stream()
                .filter(w -> id.equals(String.valueOf(w.get("id"))))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Word meaning not found: " + id));
    }
}
