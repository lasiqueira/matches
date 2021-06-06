package com.lucas.matches.api.v1.converter;

import com.lucas.matches.api.v1.dto.MatchResponse;
import com.lucas.matches.infrastructure.document.MatchDocument;
import com.lucas.matches.infrastructure.document.SummaryTypeDocument;
import com.lucas.matches.service.domain.Match;
import com.lucas.matches.service.domain.SummaryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchResponseConverterTest {
    private MatchResponseConverter matchResponseConverter;
    private Match avbMatch;
    private Match avbTimeAfterMatch;
    private Match avbTimeBeforeMatch;
    private Match noSummaryTypeMatch;
    @BeforeAll
    public void setup(){
        matchResponseConverter = new MatchResponseConverter();
        avbMatch = new Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB);
        //had to add a few seconds otherwise it would report as 1 minute less if it's 59 seconds.
        avbTimeAfterMatch = new Match("m1", LocalDateTime.now().plusMinutes(20).plusSeconds(5), "playerA", "playerB", SummaryType.AVBTIME);
        avbTimeBeforeMatch = new Match("m1", LocalDateTime.now().minusMinutes(20), "playerA", "playerB",  SummaryType.AVBTIME);
        noSummaryTypeMatch = new Match("m1", LocalDateTime.now(), "playerA", "playerB", null);
    }

    @Test
    @DisplayName("convert Match with AVB SummaryType to MatchResponse")
    public void convertMatchAvbToMatchResponse(){
        List<MatchResponse> matchResponses = matchResponseConverter.convert(List.of(avbMatch));
        assertNotNull(matchResponses);
        assertEquals(1, matchResponses.size());
        assertEquals("playerA vs playerB", matchResponses.get(0).getSummary());
    }

    @Test
    @DisplayName("convert Match with AVBTime SummaryType with startTime after now to MatchResponse")
    public void convertMatchAvbTimeAfterToMatchResponse(){
        List<MatchResponse> matchResponses = matchResponseConverter.convert(List.of(avbTimeAfterMatch));
        assertNotNull(matchResponses);
        assertEquals(1, matchResponses.size());
        assertEquals("playerA vs playerB, starts in 20 minutes", matchResponses.get(0).getSummary());
    }

    @Test
    @DisplayName("convert Match with AVBTime SummaryType with startTime before now to MatchResponse")
    public void convertMatchAvbTimeBeforeToMatchResponse(){
        List<MatchResponse> matchResponses = matchResponseConverter.convert(List.of(avbTimeBeforeMatch));
        assertNotNull(matchResponses);
        assertEquals(1, matchResponses.size());
        assertEquals("playerA vs playerB, started 20 minutes ago", matchResponses.get(0).getSummary());
    }

    @Test
    @DisplayName("convert Match with no SummaryType to MatchResponse")
    public void convertMatchNoSummaryTypeToMatchResponse(){
        List<MatchResponse> matchResponses = matchResponseConverter.convert(List.of(noSummaryTypeMatch));
        assertNotNull(matchResponses);
        assertEquals(1, matchResponses.size());
        assertTrue(matchResponses.get(0).getSummary().isEmpty());
    }

    @Test
    @DisplayName("convert empty List")
    public void convertEmptyList(){
        List<MatchResponse> matchResponses = matchResponseConverter.convert(new ArrayList<>());
        assertNotNull(matchResponses);
        assertEquals(0, matchResponses.size());

    }
}
