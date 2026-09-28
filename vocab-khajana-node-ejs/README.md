# Vocab Khajana - Node.js + Express + EJS

Equivalent Node.js/Express/EJS migration of the cleaned Spring Boot MVC version.

## Stack
- Node.js
- Express 5
- EJS
- fast-xml-parser
- `data/khajana.xml` retained as the vocabulary source

## Run

```bash
npm install
npm start
```

Open:

```text
http://localhost:3000
```

Development:

```bash
npm install
npm run dev
```

## Routes

- `/` - home
- `/getVocabs` - JSON API (`?offset=0&count=0`)
- `/viewVocabs` - detailed vocabulary
- `/viewVocabsSimple` - simple vocabulary
- `/viewVocabsWithDivJquery` - exercise view
- `/add-vocab` - add vocabulary (runtime memory)
- `/targetWifeRepairingReturning` - migrated legacy page
- `/login` - simple login (`jbk` / `jbk`)
- `/welcome`
- `/list-todos`

## Architecture

```text
Browser
  -> Express Router
  -> Controller
  -> VocabularyService
  -> khajana.xml
  -> EJS
```
