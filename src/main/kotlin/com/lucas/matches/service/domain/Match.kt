package com.lucas.matches.service.domain

import java.time.LocalDateTime
import java.util.*

class Match {
    var matchId: String? = null
    var startDate: LocalDateTime? = null
    var playerA: String? = null
    var playerB: String? = null
    var summaryType: SummaryType? = null

    constructor() {}
    constructor(
        matchId: String?,
        startDate: LocalDateTime?,
        playerA: String?,
        playerB: String?,
        summaryType: SummaryType?
    ) {
        this.matchId = matchId
        this.startDate = startDate
        this.playerA = playerA
        this.playerB = playerB
        this.summaryType = summaryType
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val match = o as Match
        return matchId == match.matchId && startDate == match.startDate && playerA == match.playerA && playerB == match.playerB && summaryType == match.summaryType
    }

    override fun hashCode(): Int {
        return Objects.hash(matchId, startDate, playerA, playerB, summaryType)
    }

    override fun toString(): String {
        return "Match{" +
                "matchId='" + matchId + '\'' +
                ", startDate=" + startDate +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", summaryType=" + summaryType +
                '}'
    }
}