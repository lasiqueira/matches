package com.lucas.matches.service.domain

import java.time.LocalDateTime

data class Match(
    val matchId: String,
    val startDate: LocalDateTime,
    val playerA: String,
    val playerB: String,
    val summaryType: SummaryType?
)