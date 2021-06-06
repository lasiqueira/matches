package com.lucas.matches.api.v1.converter;

import com.lucas.matches.api.v1.dto.MatchResponse;
import com.lucas.matches.service.domain.Match;
import com.lucas.matches.service.domain.SummaryType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class MatchResponseConverter {
    public List<MatchResponse> convert(List<Match> matches) {
       List<MatchResponse> matchResponses = new ArrayList<>();
        matches.forEach(match -> matchResponses.add(convert(match)));
        return matchResponses;
    }

    private MatchResponse convert(Match match){
        MatchResponse matchResponse = new MatchResponse();
        matchResponse.setMatchId(match.getMatchId());
        matchResponse.setStartDate(match.getStartDate());
        matchResponse.setPlayerA(match.getPlayerA());
        matchResponse.setPlayerB(match.getPlayerB());
        matchResponse.setSummary(buildSummary(match));
        return matchResponse;
    }
    private String buildSummary(Match match){
        String summary = "";
        if(SummaryType.AVB.equals(match.getSummaryType())){
            summary = buildAvbSummary(match.getPlayerA(), match.getPlayerB());
        } else if(SummaryType.AVBTIME.equals(match.getSummaryType())){
            summary = buildAvbTimeSummary(match.getPlayerA(), match.getPlayerB(), match.getStartDate());
        }
        return summary;
    }

    private String buildAvbSummary(String playerA, String playerB){
        return String.format("%s vs %s", playerA, playerB);
    }
    private String buildAvbTimeSummary(String playerA, String playerB, LocalDateTime startDate){
        String timeSummary = null;
        //what about matches days ago or days in the future? Should these be filtered out or give different time units?
        if(startDate.isAfter(LocalDateTime.now())){
            timeSummary = String.format("starts in %d minutes", ChronoUnit.MINUTES.between(LocalDateTime.now(), startDate));
        } else if((startDate.isBefore(LocalDateTime.now()))) {
            timeSummary = String.format("started %d minutes ago", ChronoUnit.MINUTES.between(startDate, LocalDateTime.now()));
        }
        return String.format("%s, %s", buildAvbSummary(playerA, playerB), timeSummary);
    }


}
