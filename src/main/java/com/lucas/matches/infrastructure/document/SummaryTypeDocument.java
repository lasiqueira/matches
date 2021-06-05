package com.lucas.matches.infrastructure.document;

import java.util.HashMap;
import java.util.Map;

public enum SummaryTypeDocument {
    AVB("AvB"),
    AVBTIME("AvBTime");

    private final String summaryTypeText;
    private static final Map<String, SummaryTypeDocument> map = new HashMap<>();
    SummaryTypeDocument(String summaryTypeText) {
        this.summaryTypeText = summaryTypeText;
    }
    static {
        for (SummaryTypeDocument summaryType : SummaryTypeDocument.values()) {
            map.put(summaryType.summaryTypeText, summaryType);
        }
    }

    public static SummaryTypeDocument value(String summaryTypeText) {
        return map.get(summaryTypeText);
    }
}
