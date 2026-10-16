# MyMail

A starter Gmail read-only client with React + TypeScript + MUI and Spring Boot 3 / Java 21.

## Prerequisites
- JDK 21
- Maven 3.9+
- Node.js 20+
- Google Cloud project with Gmail API enabled
- OAuth consent screen configured, your Gmail account added as a test user, and the scope
  `https://www.googleapis.com/auth/gmail.readonly` added.
- OAuth 2.0 Web application client with redirect URI:
  `http://localhost:8080/login/oauth2/code/google`

## Configure Google credentials

Set environment variables before starting the backend.

PowerShell:
```powershell
$env:GOOGLE_CLIENT_ID="YOUR_CLIENT_ID"
$env:GOOGLE_CLIENT_SECRET="YOUR_CLIENT_SECRET"
```

Do not commit credentials or put the client secret in the frontend.

## Run backend

```powershell
cd backend
mvn spring-boot:run
```

Backend: http://localhost:8080

## Run frontend

Open a second terminal:

```powershell
cd frontend
npm install
npm run dev
```

Frontend: http://localhost:5173

Click **Connect Gmail**. Google will ask you to sign in and authorize read-only access.
After successful authorization, MyMail redirects to the frontend.

## API endpoints
- `GET /api/auth/status` - checks whether the current browser session is authenticated.
- `GET /api/emails?pageToken=&maxResults=20` - lists inbox messages and fetches basic headers.
- `GET /api/emails/{id}` - retrieves a message's headers and decoded plain-text body.
- `GET /oauth2/authorization/google` - starts Google OAuth login.
- `POST /api/auth/logout` - logs out and invalidates the session.

## Notes
- This starter uses a server-side OAuth login and keeps the client secret on the backend.
- Gmail API calls use the OAuth access token held by Spring Security's authorized-client service.
- This is a development starter, not a production-hardened email client. Add CSRF protection appropriate to your deployment, secure session-cookie settings, rate limits, robust MIME/attachment handling, and operational monitoring before production.
- Gmail's `gmail.readonly` scope is restricted. Public distribution can require Google verification and additional security review.
