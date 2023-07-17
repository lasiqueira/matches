package com.lucas.matches.api.v1.converter

import com.lucas.matches.api.v1.dto.MatchResponse
import com.lucas.matches.service.domain.Match
import com.lucas.matches.service.domain.SummaryType
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class MatchResponseConverter {
    fun convert(matches: List<Match>): List<MatchResponse> {
        return matches.map { convert(it) }
    }

    private fun convert(match: Match): MatchResponse {
        return MatchResponse(
            match.matchId,
            match.startDate,
            match.playerA,
            match.playerB,
            buildSummary(match)
        )
    }

    private fun buildSummary(match: Match): String {
        var summary = ""
        if (SummaryType.AVB == match.summaryType) {
            summary = buildAvbSummary(match.playerA, match.playerB)
        } else if (SummaryType.AVBTIME == match.summaryType) {
            summary = buildAvbTimeSummary(match.playerA, match.playerB, match.startDate)
        }
        return summary
    }

    private fun buildAvbSummary(playerA: String, playerB: String): String {
        return String.format("%s vs %s", playerA, playerB)
    }

    private fun buildAvbTimeSummary(playerA: String, playerB: String, startDate: LocalDateTime): String {
        var timeSummary: String? = null
        //what about matches days ago or days in the future? Should these be filtered out or give different time units?
        //if it's 1 minute, should the text still say 'minutes'?
        if (startDate.isAfter(LocalDateTime.now())) {
            timeSummary =
                String.format("starts in %d minutes", ChronoUnit.MINUTES.between(LocalDateTime.now(), startDate))
        } else if (startDate.isBefore(LocalDateTime.now())) {
            timeSummary =
                String.format("started %d minutes ago", ChronoUnit.MINUTES.between(startDate, LocalDateTime.now()))
        }
        return String.format("%s, %s", buildAvbSummary(playerA, playerB), timeSummary)
    }
}