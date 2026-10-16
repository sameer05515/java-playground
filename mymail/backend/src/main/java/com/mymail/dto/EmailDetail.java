package com.mymail.dto;

public record EmailDetail(
    String id,
    String threadId,
    String from,
    String to,
    String subject,
    String date,
    String body
) {}
