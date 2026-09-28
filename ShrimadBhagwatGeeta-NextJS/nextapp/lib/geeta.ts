import fs from 'node:fs/promises';
import path from 'node:path';

type AnyObject = Record<string, any>;
const dataDir = path.join(process.cwd(), 'public', 'data', 'json');

async function readJson<T>(name: string): Promise<T> {
  return JSON.parse(await fs.readFile(path.join(dataDir, name), 'utf8')) as T;
}

export type Verse = AnyObject;
export type Chapter = AnyObject;
export type WordMeaning = AnyObject;

export const getChapterSummaries = () => readJson<Chapter[]>('chapter-summary.json');
export const getChapters = () => readJson<Chapter[]>('chapter-verse-detail-temp.json');
export const getWordMeanings = () => readJson<WordMeaning[]>('chapter-verse-word-meaning-temp.json');

export async function getChapter(id: string) {
  const chapters = await getChapters();
  return chapters.find(c => String(c.id) === id) ?? null;
}

export async function getVerse(chapterNo: string, verseNo: string) {
  const chapter = await getChapter(chapterNo);
  return chapter?.verses?.find((v: Verse) => String(v.id) === verseNo) ?? null;
}

export async function getWordMeaning(wordId: string) {
  const list = await getWordMeanings();
  return list.find(w => String(w.id) === wordId) ?? null;
}

export function chapterUrl(id: string) { return `/chapter/${encodeURIComponent(id)}`; }
export function verseUrl(id: string) {
  const [chapter, verse] = String(id).split('.');
  return `/chapter/${encodeURIComponent(chapter)}/verse/${encodeURIComponent(id)}`;
}
export function wordUrl(id: string) { return `/word-meaning/${encodeURIComponent(id)}`; }
