import Link from 'next/link';
import { getChapter, chapterUrl, verseUrl } from '@/lib/geeta';
export default async function ChapterPage({params}:{params:Promise<{chapterNo:string}>}){
 const {chapterNo}=await params; const c=await getChapter(decodeURIComponent(chapterNo));
 if(!c)return <main className="container"><h2>Chapter not found</h2><Link href="/">Home</Link></main>;
 return <main className="container"><div className="headingBg"><div>Bhagavad Gita : <b>{c.chapterTitleForChapterPage}</b> <span>({c.chapterSubTitleForChapterPage})</span></div><div style={{textAlign:'center',marginTop:8}}><Link href="/">HOME</Link></div><div className="navrow"><Link href={c.previousChapterUrl ? chapterUrl(String(c.previousChapter)) : '#'}>Previous Chapter</Link><Link href={c.nextChapterUrl ? chapterUrl(String(c.nextChapter)) : '#'}>Next Chapter</Link></div></div>
 <section>{(c.description??[]).map((d:string,i:number)=><p key={i}>{d}</p>)}</section>
 {c.verses?.map((v:any)=><article className="card" style={{marginBottom:14}} key={v.id}><h3><Link href={verseUrl(String(v.id))}>{v.verseHeader}</Link></h3><div className="card-body">{v.shlok?.map((s:any)=><div className="shlok" key={s.id}>{s.value}</div>)}<p>{v.oneLiner}</p></div></article>)}
 </main>
}
