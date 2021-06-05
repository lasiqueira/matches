package com.lucas.matches.service.converter;

import com.lucas.matches.infrastructure.document.MatchDocument;
import com.lucas.matches.service.domain.Match;
import com.lucas.matches.service.domain.SummaryType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MatchConverter {
    public List<Match> convert(List<MatchDocument> matchDocuments) {
        List<Match> matches = new ArrayList<>();
        matchDocuments.forEach(matchDocument -> matches.add(convert(matchDocument)));
        return matches;
    }

    private Match convert(MatchDocument matchDocument){
        Match match = new Match();
        match.setMatchId(matchDocument.getMatchId());
        match.setStartDate(matchDocument.getStartDate());
        match.setPlayerA(matchDocument.getPlayerA());
        match.setPlayerB(matchDocument.getPlayerB());
        Optional.ofNullable(matchDocument.getSummaryType()).ifPresent(summaryType -> match.setSummaryType(SummaryType.valueOf(summaryType.name())));

        return match;
    }
}
