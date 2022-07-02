package com.lucas.matches.api.v1.dto

import java.time.LocalDateTime

data class MatchResponse(val matchId: String, val startDate: LocalDateTime, val playerA: String, val playerB: String, val summary: String)