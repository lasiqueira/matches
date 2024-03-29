package com.lucas.matches.service.converter

import com.lucas.matches.infrastructure.document.MatchDocument
import com.lucas.matches.service.domain.Match
import com.lucas.matches.service.domain.SummaryType
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class MatchConverter {
    fun convert(matchDocuments: List<MatchDocument>): List<Match> {
        val matches: MutableList<Match> = ArrayList()
        matchDocuments.forEach(Consumer { matchDocument: MatchDocument -> matches.add(convert(matchDocument)) })
        return matches
    }

    private fun convert(matchDocument: MatchDocument): Match {
        return Match(matchDocument.matchId,
            matchDocument.startDate,
                    matchDocument.playerA,
            matchDocument.playerB,
            matchDocument.summaryType?.let{SummaryType.valueOf(it.name)}
        )

    }
}