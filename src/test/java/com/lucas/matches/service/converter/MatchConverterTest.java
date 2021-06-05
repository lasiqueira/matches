package com.lucas.matches.service.converter;

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
public class MatchConverterTest {
    private MatchDocument avbMatchDocument;
    private MatchDocument avbTimeMatchDocument;
    private MatchDocument noSummaryTypeMatchDocument;
    private MatchConverter matchConverter;

    @BeforeAll
    public void setup(){
        avbMatchDocument = new MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB);
        avbTimeMatchDocument = new MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME);
        noSummaryTypeMatchDocument = new MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), null);
        matchConverter = new MatchConverter();
    }

    @Test
    @DisplayName("convert MatchDocument with AVB SummaryType to Match")
    public void convertMatchDocumentAvbToMatch(){
        List<Match> match = matchConverter.convert(List.of(avbMatchDocument));
        assertNotNull(match);
        assertEquals(1, match.size());
        assertEquals(SummaryType.AVB, match.get(0).getSummaryType());
    }

    @Test
    @DisplayName("convert MatchDocument with AVBTime SummaryType to Match")
    public void convertMatchDocumentAvbTimeToMatch(){
        List<Match> match = matchConverter.convert(List.of(avbTimeMatchDocument));
        assertNotNull(match);
        assertEquals(1, match.size());
        assertEquals(SummaryType.AVBTIME, match.get(0).getSummaryType());
    }
    @Test
    @DisplayName("convert MatchDocument with no SummaryType to Match")
    public void convertMatchDocumentNoSummaryTypeToMatch(){
        List<Match> match = matchConverter.convert(List.of(noSummaryTypeMatchDocument));
        assertNotNull(match);
        assertEquals(1, match.size());
        assertNull(match.get(0).getSummaryType());
    }

    @Test
    @DisplayName("convert empty List")
    public void convertEmptyList(){
        List<Match> match = matchConverter.convert(new ArrayList<>());
        assertNotNull(match);
        assertEquals(0, match.size());

    }
}
