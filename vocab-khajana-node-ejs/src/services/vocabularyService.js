const fs = require('node:fs');
const path = require('node:path');
const { XMLParser } = require('fast-xml-parser');
const VocabResponse = require('../models/VocabResponse');

class VocabularyService {
  constructor(xmlPath) {
    this.xmlPath = xmlPath;
    this.parser = new XMLParser({
      ignoreAttributes: false,
      attributeNamePrefix: '@_',
      trimValues: true
    });
  }

  findAll() {
    const xml = fs.readFileSync(this.xmlPath, 'utf8');
    const document = this.parser.parse(xml);
    const root = document?.['vocab-config']?.['word-list'];
    const nodes = this.toArray(root?.myword ?? document?.myword);

    return nodes.map((myword, index) => {
      const wordNode = myword.word ?? {};
      return {
        id: index + 1,
        word: this.text(wordNode),
        type: wordNode['@_type'] ?? '',
        meanings: this.toArray(myword.meanings?.meaning).map((m, i) => ({
          id: i + 1,
          text: this.text(m)
        })),
        examples: this.toArray(myword.examples?.example).map((e, i) => ({
          id: i + 1,
          text: this.text(e)
        }))
      };
    });
  }

  getResponse(offset = 0, count = 0) {
    const all = this.findAll();
    const from = Math.max(0, Number(offset) || 0);
    const requestedCount = Number(count) || 0;
    const to = requestedCount > 0 ? Math.min(all.length, from + requestedCount) : all.length;
    const words = from < all.length ? all.slice(from, to) : [];
    return new VocabResponse(Number(offset) || 0, requestedCount, words);
  }

  addVocabulary({ word, type, meanings = [], examples = [] }) {
    // Keeps the web form functional without modifying the source XML on disk.
    // Restarting the app reloads the original khajana.xml data.
    const words = this.findAll();
    words.push({
      id: words.length + 1,
      word,
      type,
      meanings: meanings.filter(Boolean).map((text, i) => ({ id: i + 1, text })),
      examples: examples.filter(Boolean).map((text, i) => ({ id: i + 1, text }))
    });
    this.runtimeWords = words;
    return words.at(-1);
  }

  findAllIncludingRuntime() {
    return this.runtimeWords ?? this.findAll();
  }

  text(value) {
    if (value == null) return '';
    if (typeof value === 'string' || typeof value === 'number') return String(value).trim();
    if (typeof value === 'object' && '#text' in value) return String(value['#text'] ?? '').trim();
    return '';
  }

  toArray(value) {
    if (value == null) return [];
    return Array.isArray(value) ? value : [value];
  }
}

module.exports = VocabularyService;
