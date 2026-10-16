package com.mymail.dto;

import java.util.List;

public record EmailPage(List<EmailSummary> messages, String nextPageToken) {}
