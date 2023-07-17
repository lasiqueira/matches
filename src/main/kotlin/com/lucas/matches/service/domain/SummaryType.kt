package com.lucas.matches.service.domain

enum class SummaryType(private val summaryTypeText: String) {
    AVB("AvB"), AVBTIME("AvBTime");

    companion object {
        private val map: MutableMap<String, SummaryType> = HashMap()
        fun value(summaryTypeText: String): SummaryType? {
            return map[summaryTypeText]
        }

        init {
            for (summaryType in values()) {
                map[summaryType.summaryTypeText] =
                    summaryType
            }
        }
    }
}