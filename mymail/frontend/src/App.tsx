import { useEffect, useState } from 'react';
import {
  AppBar, Avatar, Box, Button, CircularProgress, Container, Divider, Drawer,
  IconButton, List, ListItemButton, ListItemText, Paper, Stack, Toolbar, Typography,
  Alert, Chip
} from '@mui/material';
import MailOutlineIcon from '@mui/icons-material/MailOutline';
import InboxIcon from '@mui/icons-material/Inbox';
import RefreshIcon from '@mui/icons-material/Refresh';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import LogoutIcon from '@mui/icons-material/Logout';
import { getAuthStatus, getInbox, getEmail, logout } from './api';
import type { EmailDetail, EmailSummary } from './types';

const drawerWidth = 230;
const API = 'http://localhost:8080';

export default function App() {
  const [authenticated, setAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [messages, setMessages] = useState<EmailSummary[]>([]);
  const [selected, setSelected] = useState<EmailDetail | null>(null);
  const [error, setError] = useState('');

  async function refresh() {
    setLoading(true);
    setError('');
    try {
      const status = await getAuthStatus();
      setAuthenticated(status.authenticated);
      if (status.authenticated) {
        const page = await getInbox();
        setMessages(page.messages);
      }
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Something went wrong');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => { void refresh(); }, []);

  async function openEmail(message: EmailSummary) {
    setLoading(true);
    setError('');
    try {
      setSelected(await getEmail(message.id));
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Unable to open email');
    } finally {
      setLoading(false);
    }
  }

  async function handleLogout() {
    await logout();
    setSelected(null);
    setMessages([]);
    setAuthenticated(false);
  }

  return (
    <Box sx={{ minHeight: '100vh', bgcolor: 'background.default' }}>
      <AppBar position="static" elevation={0} color="inherit" sx={{ borderBottom: '1px solid #e5e7eb' }}>
        <Toolbar>
          <MailOutlineIcon color="primary" sx={{ mr: 1.5 }} />
          <Typography variant="h6" color="text.primary" fontWeight={700}>MyMail</Typography>
          <Box sx={{ flexGrow: 1 }} />
          {authenticated && <Button startIcon={<LogoutIcon />} onClick={handleLogout}>Disconnect</Button>}
        </Toolbar>
      </AppBar>

      {!authenticated ? (
        <Container maxWidth="sm" sx={{ py: 12 }}>
          <Paper sx={{ p: 5, textAlign: 'center' }}>
            <Avatar sx={{ bgcolor: 'primary.light', width: 64, height: 64, mx: 'auto', mb: 2 }}>
              <MailOutlineIcon fontSize="large" />
            </Avatar>
            <Typography variant="h4" fontWeight={700} gutterBottom>Welcome to MyMail</Typography>
            <Typography color="text.secondary" sx={{ mb: 3 }}>
              Connect your Google account to securely view your Gmail inbox.
            </Typography>
            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
            <Button variant="contained" size="large"
              onClick={() => window.location.href = `${API}/oauth2/authorization/google`}>
              Connect Gmail
            </Button>
            <Typography variant="caption" display="block" color="text.secondary" sx={{ mt: 2 }}>
              Read-only access. MyMail cannot send or modify your emails.
            </Typography>
          </Paper>
        </Container>
      ) : (
        <Box sx={{ display: 'flex' }}>
          <Drawer variant="permanent" sx={{
            width: drawerWidth, flexShrink: 0,
            '& .MuiDrawer-paper': { width: drawerWidth, boxSizing: 'border-box', top: 64, height: 'calc(100% - 64px)', bgcolor: '#f6f8fc', border: 0, p: 2 }
          }}>
            <List>
              <ListItemButton selected sx={{ borderRadius: 3 }}>
                <InboxIcon color="primary" sx={{ mr: 2 }} />
                <ListItemText primary="Inbox" />
                <Chip size="small" label={messages.length} />
              </ListItemButton>
            </List>
          </Drawer>

          <Box component="main" sx={{ flexGrow: 1, p: { xs: 2, md: 4 }, ml: { sm: `${drawerWidth}px` } }}>
            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
            {selected ? (
              <Paper sx={{ p: { xs: 2, md: 4 }, minHeight: 500 }}>
                <Button startIcon={<ArrowBackIcon />} onClick={() => setSelected(null)} sx={{ mb: 2 }}>Back to inbox</Button>
                <Typography variant="h5" fontWeight={700} sx={{ mb: 2 }}>{selected.subject || '(No subject)'}</Typography>
                <Typography variant="body2" color="text.secondary">From: {selected.from}</Typography>
                <Typography variant="body2" color="text.secondary">To: {selected.to}</Typography>
                <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>Date: {selected.date}</Typography>
                <Divider sx={{ mb: 3 }} />
                <Box component="pre" sx={{ whiteSpace: 'pre-wrap', overflowWrap: 'anywhere', font: 'inherit' }}>
                  {selected.body || 'No plain-text body was found. HTML-only and complex MIME messages will be supported in a later milestone.'}
                </Box>
              </Paper>
            ) : (
              <Paper sx={{ overflow: 'hidden' }}>
                <Stack direction="row" alignItems="center" spacing={2} sx={{ p: 2 }}>
                  <Typography variant="h5" fontWeight={700} sx={{ flexGrow: 1 }}>Inbox</Typography>
                  <IconButton onClick={() => void refresh()} aria-label="Refresh inbox"><RefreshIcon /></IconButton>
                </Stack>
                <Divider />
                {loading ? <Box sx={{ p: 6, textAlign: 'center' }}><CircularProgress /></Box> :
                  messages.length === 0 ? <Box sx={{ p: 6, textAlign: 'center' }}><Typography color="text.secondary">Your inbox is empty.</Typography></Box> :
                  messages.map(message => (
                    <ListItemButton key={message.id} onClick={() => void openEmail(message)}
                      sx={{ px: 3, py: 2, bgcolor: message.unread ? '#fff' : 'transparent', borderBottom: '1px solid #edf0f4' }}>
                      <ListItemText
                        primary={<Typography fontWeight={message.unread ? 700 : 400} noWrap>{message.from || '(Unknown sender)'}</Typography>}
                        secondary={<>
                          <Typography component="span" display="block" fontWeight={message.unread ? 700 : 400} color="text.primary" noWrap>{message.subject || '(No subject)'}</Typography>
                          <Typography component="span" variant="body2" color="text.secondary" noWrap>{message.snippet}</Typography>
                        </>}
                      />
                      <Typography variant="caption" color="text.secondary" sx={{ ml: 2, whiteSpace: 'nowrap' }}>{message.date}</Typography>
                    </ListItemButton>
                  ))
                }
              </Paper>
            )}
          </Box>
        </Box>
      )}
    </Box>
  );
}
