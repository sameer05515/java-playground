package com.mymail.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mymail.dto.EmailDetail;
import com.mymail.dto.EmailPage;
import com.mymail.dto.EmailSummary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class GmailService {
    private static final String GMAIL_API = "https://gmail.googleapis.com/gmail/v1/users/me";
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public GmailService(OAuth2AuthorizedClientService authorizedClientService,
                        ObjectMapper objectMapper) {
        this.authorizedClientService = authorizedClientService;
        this.objectMapper = objectMapper;
        this.restClient = RestClient.builder().build();
    }

    private String accessToken(Authentication authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
            "google", authentication.getName());
        if (client == null || client.getAccessToken() == null) {
            throw new IllegalStateException("Google authorization is missing. Connect Gmail again.");
        }
        return client.getAccessToken().getTokenValue();
    }

    private JsonNode getJson(String url, Authentication authentication) {
        String token = accessToken(authentication);
        String response = restClient.get()
            .uri(url)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(String.class);
        try {
            return objectMapper.readTree(response);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not parse Gmail API response", ex);
        }
    }

    public EmailPage listInbox(Authentication authentication, String pageToken, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(GMAIL_API + "/messages")
            .queryParam("labelIds", "INBOX")
            .queryParam("maxResults", Math.max(1, Math.min(maxResults, 50)));
        if (pageToken != null && !pageToken.isBlank()) {
            builder.queryParam("pageToken", pageToken);
        }

        JsonNode list = getJson(builder.build().encode().toUriString(), authentication);
        List<EmailSummary> messages = new ArrayList<>();
        JsonNode items = list.path("messages");
        if (items.isArray()) {
            for (JsonNode item : items) {
                JsonNode message = getJson(GMAIL_API + "/messages/" + item.path("id").asText()
                    + "?format=metadata&metadataHeaders=From&metadataHeaders=Subject&metadataHeaders=Date",
                    authentication);
                JsonNode headers = message.path("payload").path("headers");
                String from = header(headers, "From");
                String subject = header(headers, "Subject");
                String date = header(headers, "Date");
                boolean unread = containsLabel(message.path("labelIds"), "UNREAD");
                messages.add(new EmailSummary(
                    message.path("id").asText(),
                    message.path("threadId").asText(),
                    from, subject, date, unread,
                    message.path("snippet").asText()
                ));
            }
        }
        return new EmailPage(messages, list.path("nextPageToken").asText(null));
    }

    public EmailDetail getMessage(Authentication authentication, String id) {
        JsonNode message = getJson(GMAIL_API + "/messages/" + id + "?format=full", authentication);
        JsonNode headers = message.path("payload").path("headers");
        String body = extractText(message.path("payload"));
        return new EmailDetail(
            message.path("id").asText(),
            message.path("threadId").asText(),
            header(headers, "From"),
            header(headers, "To"),
            header(headers, "Subject"),
            header(headers, "Date"),
            body
        );
    }

    private String header(JsonNode headers, String name) {
        if (headers.isArray()) {
            for (JsonNode header : headers) {
                if (name.equalsIgnoreCase(header.path("name").asText())) {
                    return header.path("value").asText("");
                }
            }
        }
        return "";
    }

    private boolean containsLabel(JsonNode labels, String expected) {
        if (labels.isArray()) {
            for (JsonNode label : labels) {
                if (expected.equals(label.asText())) return true;
            }
        }
        return false;
    }

    private String extractText(JsonNode part) {
        String mimeType = part.path("mimeType").asText("");
        String data = part.path("body").path("data").asText("");
        if (!data.isBlank() && (mimeType.equals("text/plain") || mimeType.isBlank())) {
            return decodeBase64Url(data);
        }
        JsonNode parts = part.path("parts");
        if (parts.isArray()) {
            for (JsonNode child : parts) {
                String text = extractText(child);
                if (!text.isBlank()) return text;
            }
        }
        return "";
    }

    private String decodeBase64Url(String data) {
        byte[] decoded = Base64.getUrlDecoder().decode(data);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
