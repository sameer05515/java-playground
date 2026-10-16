import type { EmailDetail, EmailPage } from './types';

const API = 'http://localhost:8080';

export async function getAuthStatus(): Promise<{ authenticated: boolean }> {
  const response = await fetch(`${API}/api/auth/status`, { credentials: 'include' });
  if (!response.ok) throw new Error('Could not check login status');
  return response.json();
}

export async function getInbox(): Promise<EmailPage> {
  const response = await fetch(`${API}/api/emails?maxResults=25`, { credentials: 'include' });
  if (response.status === 401) throw new Error('Please connect Gmail first.');
  if (!response.ok) throw new Error('Unable to load inbox. Please reconnect Gmail.');
  return response.json();
}

export async function getEmail(id: string): Promise<EmailDetail> {
  const response = await fetch(`${API}/api/emails/${encodeURIComponent(id)}`, { credentials: 'include' });
  if (!response.ok) throw new Error('Unable to load this email.');
  return response.json();
}

export async function logout(): Promise<void> {
  await fetch(`${API}/api/auth/logout`, {
    method: 'POST',
    credentials: 'include',
    headers: { 'Content-Type': 'application/json' }
  });
}
