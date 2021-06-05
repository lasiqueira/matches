package com.lucas.matches.infrastructure.document;

import java.util.HashMap;
import java.util.Map;

public enum SummaryType {
    AVB("AvB"),
    AVBTIME("AvBTime");

    private final String summaryTypeText;
    private static final Map<String, SummaryType> map = new HashMap<>();
    SummaryType(String summaryTypeText) {
        this.summaryTypeText = summaryTypeText;
    }
    static {
        for (SummaryType summaryType : SummaryType.values()) {
            map.put(summaryType.summaryTypeText, summaryType);
        }
    }

    public static SummaryType value(String summaryTypeText) {
        return map.get(summaryTypeText);
    }
}
