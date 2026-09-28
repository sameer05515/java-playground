const path = require('node:path');
const express = require('express');
const VocabularyService = require('./services/vocabularyService');
const vocabularyController = require('./controllers/vocabularyController');
const authController = require('./controllers/authController');

const app = express();
const port = Number(process.env.PORT || 3000);
const service = new VocabularyService(path.join(__dirname, '..', 'data', 'khajana.xml'));

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));
app.use(express.static(path.join(__dirname, 'public')));
app.use(vocabularyController(service));
app.use(authController());

app.use((err, req, res, next) => {
  console.error(err);
  res.status(500).send('Internal Server Error');
});

app.listen(port, () => console.log(`Vocab Khajana running at http://localhost:${port}`));
