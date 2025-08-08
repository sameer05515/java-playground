const express = require('express');

module.exports = function vocabularyController(service) {
  const router = express.Router();

  router.get('/', (req, res) => {
    res.render('index', { totalWords: service.findAllIncludingRuntime().length });
  });

  router.get('/viewVocabs', (req, res) => {
    res.render('view-vocabs', { words: [...service.findAllIncludingRuntime()].reverse() });
  });

  router.get('/viewVocabsSimple', (req, res) => {
    res.render('view-vocabs-simple', { words: [...service.findAllIncludingRuntime()].reverse() });
  });

  router.get('/viewVocabsWithDivJquery', (req, res) => {
    const offset = Number(req.query.offset) || 0;
    const count = Number(req.query.count) || 0;
    res.render('view-vocabs-exercise', {
      offset,
      count,
      words: [...service.findAllIncludingRuntime()].reverse()
    });
  });

  router.get('/getVocabs', (req, res) => {
    const offset = Number(req.query.offset) || 0;
    const count = Number(req.query.count) || 0;
    res.json(service.getResponse(offset, count));
  });

  router.get('/add-vocab', (req, res) => res.render('add-vocab', { saved: false }));

  router.post('/add-vocab', express.urlencoded({ extended: true }), (req, res) => {
    const meanings = String(req.body.meanings || '').split(/\r?\n/).map(s => s.trim()).filter(Boolean);
    const examples = String(req.body.examples || '').split(/\r?\n/).map(s => s.trim()).filter(Boolean);
    const saved = service.addVocabulary({
      word: req.body.word,
      type: req.body.type,
      meanings,
      examples
    });
    res.render('add-vocab', { saved });
  });

  router.get('/targetWifeRepairingReturning', (req, res) => res.render('target-wife'));

  return router;
};
