package com.lucas.matches.infrastructure.document

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime
import java.util.*

@Document(collection = "matches")
class MatchDocument(@MongoId var id: String?,
                    var tournamentId: String,
                    var matchId: String,
                    var playerA: String,
                    var playerB: String,
                    var startDate: LocalDateTime,
                    var summaryType: SummaryTypeDocument?) {


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as MatchDocument
        return id == that.id && tournamentId == that.tournamentId && matchId == that.matchId && playerA == that.playerA && playerB == that.playerB && startDate == that.startDate && summaryType == that.summaryType
    }

    override fun hashCode(): Int {
        return Objects.hash(id, tournamentId, matchId, playerA, playerB, startDate, summaryType)
    }

    override fun toString(): String {
        return "MatchDocument{" +
                "id='" + id + '\'' +
                ", tournamentId='" + tournamentId + '\'' +
                ", matchId='" + matchId + '\'' +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", startDate=" + startDate +
                ", summaryType=" + summaryType +
                '}'
    }
}