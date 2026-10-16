package com.mymail.dto;

public record EmailSummary(
    String id,
    String threadId,
    String from,
    String subject,
    String date,
    boolean unread,
    String snippet
) {}
