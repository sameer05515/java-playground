# Shrimad Bhagwat Geeta - Next.js

Equivalent Next.js App Router implementation of the original AngularJS application.

## Requirements
- Node.js 20+ (22 recommended)

## Run
```bash
npm install
npm run dev
```
Open http://localhost:3000

## Production
```bash
npm run build
npm start
```

## Routes
- `/`
- `/chapter/1`
- `/chapter/1/verse/1.1`
- `/word-meaning/a-kāraḥ`

## Audio
The original application contains a large MP3 collection. MP3 files are intentionally not included in this ZIP to keep it small. Copy the original `src/main/webapp/audio` directory into this project's `public/audio` directory. Existing `shlokPath` values then work without changing the JSON.

## Data
Original JSON datasets are under `public/data/json` and images under `public/data/images`.
