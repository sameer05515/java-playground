import Image from 'next/image';
import Link from 'next/link';
import { getChapterSummaries, chapterUrl } from '@/lib/geeta';
export default async function Home(){
 const chapters=await getChapterSummaries();
 return <main className="container">
  <h2>Home Page</h2>
  <Image className="hero" src="/data/images/krishna_arjun2.jpg" width={760} height={450} alt="Krishna and Arjuna" priority />
  <p>श्रीमद्भगवद्‌गीता हिन्दुओं के पवित्रतम ग्रन्थों में से एक है। महाभारत के अनुसार कुरुक्षेत्र युद्ध में भगवान श्री कृष्ण ने गीता का सन्देश अर्जुन को सुनाया था। यह महाभारत के भीष्मपर्व के अन्तर्गत दिया गया एक उपनिषद् है। भगवत गीता में एकेश्वरवाद, कर्म योग, ज्ञानयोग, भक्ति योग की बहुत सुन्दर ढंग से चर्चा हुई है।</p>
  <p>श्रीमद्भगवद्‌गीता की पृष्ठभूमि महाभारत का युद्ध है। जिस प्रकार एक सामान्य मनुष्य अपने जीवन की समस्याओं में उलझकर किंकर्तव्यविमूढ़ हो जाता है उसी प्रकार अर्जुन भी अपने सामने आने वाली समस्याओं से भयभीत होकर जीवन और क्षत्रिय धर्म से निराश हो गए थे।</p>
  <p>नीचे दिए गए टेबल में हर अध्याय और उसमें उल्लेखित विशेषताओं का लिंक दिया गया है जिसे आप क्लिक करके पढ़ सकते हैं:</p>
  <div className="grid">{chapters.map(c=><div className="card" key={c.id}><h3><Link href={chapterUrl(String(c.id))}>{c.chapterTitle}</Link></h3><div className="card-body">{c.briefDescription}</div></div>)}</div>
 </main>
}
