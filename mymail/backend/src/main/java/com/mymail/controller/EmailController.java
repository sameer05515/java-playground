package com.mymail.controller;

import com.mymail.dto.EmailDetail;
import com.mymail.dto.EmailPage;
import com.mymail.service.GmailService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final GmailService gmailService;

    public EmailController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @GetMapping
    public EmailPage inbox(
            Authentication authentication,

            @RequestParam(
                name = "pageToken",
                required = false
            )
            String pageToken,

            @RequestParam(
                name = "maxResults",
                defaultValue = "20"
            )
            int maxResults
    ) {
        return gmailService.listInbox(
                authentication,
                pageToken,
                maxResults
        );
    }

    @GetMapping("/{id}")
    public EmailDetail detail(
            Authentication authentication,

            @PathVariable("id")
            String id
    ) {
        return gmailService.getMessage(
                authentication,
                id
        );
    }
}