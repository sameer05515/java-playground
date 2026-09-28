import './globals.css';
import type { ReactNode } from 'react';
export const metadata = { title: 'श्रीमद्भगवद्गीता', description: 'श्रीमद्भगवद्गीता' };
export default function RootLayout({children}:{children:ReactNode}) { return <html lang="hi"><body>{children}</body></html>; }
