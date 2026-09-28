import Link from 'next/link';
import { getWordMeaning, wordUrl, verseUrl } from '@/lib/geeta';
export default async function WordMeaningPage({params}:{params:Promise<{wordId:string}>}){
 const {wordId}=await params; const w=await getWordMeaning(decodeURIComponent(wordId));
 if(!w)return <main className="container"><h2>Word meaning not found</h2><Link href="/">Home</Link></main>;
 return <main className="container"><div className="headingBg"><div>Occurences of "{w.id}"</div><div style={{textAlign:'center',marginTop:8}}><Link href="/">HOME</Link></div><div className="navrow"><Link href={w.previousWMUrl?wordUrl(String(w.previousWMUrl).split('/').pop()!):'#'}>Previous</Link><Link href={w.nextWMUrl?wordUrl(String(w.nextWMUrl).split('/').pop()!):'#'}>Next</Link></div></div><table className="table"><thead><tr><th>Word</th><th>Meaning</th><th>Reference</th></tr></thead><tbody>{w.data?.map((x:any)=><tr key={x.word+x.referenceHeader}><td>{x.word}</td><td>{x.meaning}</td><td><Link href={x.refLink ? verseUrl(String(x.refLink).split('/verse/')[1]) : '#'}>{x.referenceHeader}</Link></td></tr>)}</tbody></table></main>
}
