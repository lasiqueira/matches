package com.lucas.matches.infrastructure.document

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime

@Document(collection = "matches")
data class MatchDocument(@MongoId var id: String?,
                    var tournamentId: String,
                    var matchId: String,
                    var playerA: String,
                    var playerB: String,
                    var startDate: LocalDateTime,
                    var summaryType: SummaryTypeDocument?)