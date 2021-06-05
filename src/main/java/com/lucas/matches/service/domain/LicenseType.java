package com.lucas.matches.service.domain;

import java.util.HashMap;
import java.util.Map;

public enum LicenseType {
    TOURNAMENT("TOURNAMENT"),
    MATCH("MATCH");

    private final String licenseTypeText;
    private static final Map<String, LicenseType> map = new HashMap<>();
    LicenseType(String licenseTypeText) {
        this.licenseTypeText = licenseTypeText;
    }
    static {
        for (LicenseType licenseType : LicenseType.values()) {
            map.put(licenseType.licenseTypeText, licenseType);
        }
    }

    public static LicenseType value(String licenseTypeText) {
        return map.get(licenseTypeText);
    }
}
