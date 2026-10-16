package com.p.chat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DatewiseMessageService {

    private final ObjectMapper mapper;

    public DatewiseMessageService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Groups user message IDs by UTC date and sorts each day's messages
     * chronologically. UTC matches JavaScript Date#toISOString date extraction.
     */
    public ObjectNode buildDatewiseUserMessageIds(JsonNode messages) {
        Map<LocalDate, List<JsonNode>> grouped = new TreeMap<>();

        for (JsonNode message : messages) {
            if (!message.path("isUserMessage").asBoolean(false)) {
                continue;
            }

            LocalDate date = parseUtcDate(message.path("createdOn").asText(null));
            if (date != null) {
                grouped.computeIfAbsent(date, ignored -> new ArrayList<>()).add(message);
            }
        }

        ObjectNode result = mapper.createObjectNode();
        grouped.forEach((date, dayMessages) -> {
            dayMessages.sort(Comparator.comparing(
                    message -> parseInstant(message.path("createdOn").asText(null)),
                    Comparator.nullsLast(Comparator.naturalOrder())));

            ArrayNode ids = mapper.createArrayNode();
            dayMessages.forEach(message -> ids.add(message.path("id").deepCopy()));
            result.set(date.toString(), ids);
        });

        return result;
    }

    private Instant parseInstant(String value) {
        if (value == null || value.isBlank() || "null".equals(value)) {
            return null;
        }
        try {
            return Instant.parse(value);
        } catch (DateTimeParseException ignored) {
            // OffsetDateTime strings are also accepted.
        }
        try {
            return OffsetDateTime.parse(value).toInstant();
        } catch (DateTimeParseException ignored) {
            return null;
        }
    }

    private LocalDate parseUtcDate(String value) {
        if (value == null || value.isBlank() || "null".equals(value)) {
            return null;
        }

        try {
            return OffsetDateTime.parse(value).withOffsetSameInstant(ZoneOffset.UTC).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // Try the next supported representation.
        }

        try {
            return Instant.parse(value).atZone(ZoneOffset.UTC).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // Try legacy formatted values.
        }

        for (DateTimeFormatter formatter : List.of(
                DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", java.util.Locale.ENGLISH),
                DateTimeFormatter.ofPattern("dd-MMM-yyyy", java.util.Locale.ENGLISH))) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // Continue trying supported formats.
            }
        }

        return null;
    }
}
