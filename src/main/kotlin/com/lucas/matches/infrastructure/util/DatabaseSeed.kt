package com.lucas.matches.infrastructure.util

import com.lucas.matches.infrastructure.document.MatchDocument
import com.lucas.matches.infrastructure.document.SummaryTypeDocument
import com.lucas.matches.infrastructure.repository.MatchRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DatabaseSeed(private val matchRepository: MatchRepository) {
    fun seedDatabase() {
        matchRepository.deleteAll()
        matchRepository.insert(
            listOf(
                MatchDocument(null, "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
                MatchDocument(null, "t2", "m2", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME),
                MatchDocument(
                    null,
                    "t1",
                    "m3",
                    "playerA",
                    "playerB",
                    LocalDateTime.now().minusMinutes(10),
                    SummaryTypeDocument.AVBTIME
                ),
                MatchDocument(
                    null,
                    "t2",
                    "m4",
                    "playerA",
                    "playerB",
                    LocalDateTime.now().plusMinutes(10),
                    SummaryTypeDocument.AVBTIME
                ),
                MatchDocument(null, "t2", "m5", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
            )
        )
    }
}