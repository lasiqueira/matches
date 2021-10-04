package com.lucas.matches.api.v1.dto

import java.time.LocalDateTime
import java.util.*

class MatchResponse {
    var matchId: String? = null
    var startDate: LocalDateTime? = null
    var playerA: String? = null
    var playerB: String? = null
    var summary: String? = null

    constructor() {}
    constructor(matchId: String?, startDate: LocalDateTime?, playerA: String?, playerB: String?, summary: String?) {
        this.matchId = matchId
        this.startDate = startDate
        this.playerA = playerA
        this.playerB = playerB
        this.summary = summary
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val matchResponse = o as MatchResponse
        return matchId == matchResponse.matchId && startDate == matchResponse.startDate && playerA == matchResponse.playerA && playerB == matchResponse.playerB && summary == matchResponse.summary
    }

    override fun hashCode(): Int {
        return Objects.hash(matchId, startDate, playerA, playerB, summary)
    }

    override fun toString(): String {
        return "MatchDTO{" +
                "matchId='" + matchId + '\'' +
                ", startDate=" + startDate +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", summary='" + summary + '\'' +
                '}'
    }
}