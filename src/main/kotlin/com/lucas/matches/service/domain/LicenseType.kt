package com.lucas.matches.service.domain

enum class LicenseType(private val licenseTypeText: String) {
    TOURNAMENT("TOURNAMENT"), MATCH("MATCH");

    companion object {
        private val map: MutableMap<String, LicenseType> = HashMap()
        fun value(licenseTypeText: String): LicenseType? {
            return map[licenseTypeText]
        }

        init {
            for (licenseType in values()) {
                map[licenseType.licenseTypeText] =
                    licenseType
            }
        }
    }
}