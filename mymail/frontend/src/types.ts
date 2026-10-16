export interface EmailSummary {
  id: string;
  threadId: string;
  from: string;
  subject: string;
  date: string;
  unread: boolean;
  snippet: string;
}
export interface EmailPage {
  messages: EmailSummary[];
  nextPageToken?: string | null;
}
export interface EmailDetail extends Omit<EmailSummary, 'unread' | 'snippet'> {
  to: string;
  body: string;
}
