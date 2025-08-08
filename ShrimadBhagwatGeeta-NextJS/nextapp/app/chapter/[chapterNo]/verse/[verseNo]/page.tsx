import Link from 'next/link';
import { getVerse, chapterUrl, wordUrl, verseUrl } from '@/lib/geeta';
export default async function VersePage({params}:{params:Promise<{chapterNo:string,verseNo:string}>}){
 const p=await params; const chapterNo=decodeURIComponent(p.chapterNo); const verseNo=decodeURIComponent(p.verseNo); const v=await getVerse(chapterNo,verseNo);
 if(!v)return <main className="container"><h2>Verse not found</h2><Link href="/">Home</Link></main>;
 const chapter=await (await import('@/lib/geeta')).getChapter(chapterNo);
 return <main className="container"><div className="headingBg"><div>{v.verseHeader} <span>({chapter?.chapterSubTitleForChapterPage})</span></div><div style={{textAlign:'center',marginTop:8}}><Link href="/">HOME</Link></div><div className="navrow"><Link href={chapter?.previousChapter?chapterUrl(String(chapter.previousChapter)):'#'}>Previous Chapter</Link><Link href={chapter?.nextChapter?chapterUrl(String(chapter.nextChapter)):'#'}>Next Chapter</Link></div><div className="navrow"><Link href={v.previousVerseUrl ? verseUrl(String(v.previousVerse)) : '#'}>Previous</Link><Link href={v.nextVerseUrl ? verseUrl(String(v.nextVerse)) : '#'}>Next</Link></div></div>
 <div className="verse-grid"><div>{v.shlok?.map((s:any)=><div className="shlok" key={s.id}>{s.value}</div>)}{v.shlokEng?.map((s:any)=><div className="shlok" key={s.id}>{s.value}</div>)}</div><div className="audio"><audio controls loop controlsList="nodownload" src={v.shlokPath?`/${v.shlokPath}`:undefined}/><div className="muted">{v.shlokPath}</div></div></div>
 <section className="meaning"><h3>Meaning :</h3>{v.meaning?.map((m:any)=><span key={m.id}><Link href={wordUrl(m.sanskrit.trim())}>{m.sanskrit}</Link> -- {m.meaning}, </span>)}</section>
 <section className="meaning"><h3>Translation :</h3>{v.translation?.map((t:any)=><p key={t.id}><b>{t.header}</b> -- {t.value}</p>)}</section>
 <section><h3>Commentary :</h3>{v.commentary?.map((c:any)=><div className="commentary" style={c.isShlokCommentary?{textAlign:'center',fontWeight:700}:undefined} key={c.id}>{c.value}</div>)}</section>
 </main>
}
