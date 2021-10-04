package com.lucas.matches.infrastructure.document

enum class SummaryTypeDocument(private val summaryTypeText: String) {
    AVB("AvB"), AVBTIME("AvBTime");

    companion object {
        private val map: MutableMap<String, SummaryTypeDocument> = HashMap()
        fun value(summaryTypeText: String): SummaryTypeDocument? {
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