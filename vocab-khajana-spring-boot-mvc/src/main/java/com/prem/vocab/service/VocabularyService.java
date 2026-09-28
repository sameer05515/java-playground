package com.prem.vocab.service;

import com.prem.vocab.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.InputStream;
import java.util.*;

@Service
public class VocabularyService {
    private final Resource resource;

    public VocabularyService(@Value("${vocab.xml.path}") Resource resource) {
        this.resource = resource;
    }

    public List<WordEntry> findAll() {
        try (InputStream in = resource.getInputStream()) {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            f.setFeature("http://xml.org/sax/features/external-general-entities", false);
            f.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            Document d = f.newDocumentBuilder().parse(in);
            NodeList nodes = d.getElementsByTagName("myword");
            List<WordEntry> out = new ArrayList<>();
            for (int i = 0; i < nodes.getLength(); i++) {
                Element mw = (Element) nodes.item(i);
                Element w = child(mw, "word");
                WordEntry e = WordEntry.builder().id(i + 1).word(text(w)).type(w.getAttribute("type")).build();
                Element ms = child(mw, "meanings");
                if (ms != null) {
                    NodeList x = ms.getElementsByTagName("meaning");
                    for (int j = 0; j < x.getLength(); j++)
                        e.getMeanings().add(Meaning.builder().id(j + 1).text(text(x.item(j))).build());
                }
                Element es = child(mw, "examples");
                if (es != null) {
                    NodeList x = es.getElementsByTagName("example");
                    for (int j = 0; j < x.getLength(); j++)
                        e.getExamples().add(Example.builder().id(j + 1).text(text(x.item(j))).build());
                }
                out.add(e);
            }
            return out;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to read khajana.xml", e);
        }
    }

    public VocabResponse getResponse(int offset, int count) {
        List<WordEntry> all = findAll();
        int from = Math.max(0, offset);
        int to = count > 0 ? Math.min(all.size(), from + count) : all.size();
        List<WordEntry> words = from < all.size() ? new ArrayList<>(all.subList(from, to)) : new ArrayList<>();
        return VocabResponse.builder().txtOffset(offset).txtCount(count).words(words).build();
    }

    private Element child(Element p, String n) {
        NodeList x = p.getElementsByTagName(n);
        return x.getLength() == 0 ? null : (Element) x.item(0);
    }

    private String text(Node n) {
        return n == null ? "" : n.getTextContent().trim();
    }
}