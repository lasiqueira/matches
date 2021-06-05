package com.lucas.matches.service;

import com.lucas.matches.infrastructure.document.MatchDocument;
import com.lucas.matches.infrastructure.document.SummaryTypeDocument;
import com.lucas.matches.infrastructure.repository.MatchRepository;
import com.lucas.matches.service.converter.MatchConverter;
import com.lucas.matches.service.domain.License;
import com.lucas.matches.service.domain.LicenseType;
import com.lucas.matches.service.domain.Match;
import com.lucas.matches.service.domain.SummaryType;
import com.lucas.matches.service.util.LicenseParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchServiceTest {
    private MatchService matchService;
    @MockBean
    private MatchRepository matchRepository;
    @MockBean
    private LicenseParser licenseParser;
    @MockBean
    private MatchConverter matchConverter;

    private List<String> matchLicenseStringList;
    private List<String> tournamentLicenseStringList;
    private List<String> mixedLicenseStringList;

    private List<License> matchLicenseList;
    private List<License> tournamentLicenseList;
    private List<License> mixedLicenseList;

    private List<MatchDocument> matchIdMatchDocumentList;
    private List<MatchDocument> tournamentIdMatchDocumentList;
    private List<MatchDocument> mixedMatchDocumentList;

    private List<Match> matchIdMatchList;
    private List<Match> tournamentIdMatchList;
    private List<Match> mixedMatchList;

    @BeforeAll
    public void setup() {
        matchRepository = Mockito.mock(MatchRepository.class);
        licenseParser = Mockito.mock(LicenseParser.class);
        matchConverter = Mockito.mock(MatchConverter.class);

        matchService = new MatchService(licenseParser, matchRepository, matchConverter);

        matchLicenseStringList = List.of("MATCH-m1", "MATCH-m2");
        tournamentLicenseStringList = List.of("TOURNAMENT-t1", "TOURNAMENT-t2");
        mixedLicenseStringList = List.of("TOURNAMENT-t1", "MATCH-m4");

        matchLicenseList = List.of(new License(LicenseType.MATCH, "m1"), new License(LicenseType.MATCH, "m2"));
        tournamentLicenseList = List.of(new License(LicenseType.TOURNAMENT, "t1"), new License(LicenseType.TOURNAMENT, "t2"));
        mixedLicenseList = List.of(new License(LicenseType.TOURNAMENT, "t1"), new License(LicenseType.MATCH, "m4"));

        matchIdMatchDocumentList = List.of(
                new MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
                new MatchDocument("2", "t3", "m2", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        );
        tournamentIdMatchDocumentList = List.of(
                new MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
                new MatchDocument("3", "t2", "m3", "playerA", "playerB", LocalDateTime.now(), null)
        );
        mixedMatchDocumentList = List.of(
                new MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
                new MatchDocument("4", "t4", "m4", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        );

        matchIdMatchList = List.of(
                new Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
                new Match("m2", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVBTIME)
        );
        tournamentIdMatchList = List.of(
                new Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
                new Match("m3", LocalDateTime.now(), "playerA", "playerB", null)
        );

        mixedMatchList = List.of(
                new Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
                new Match("m4", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVBTIME)
        );
    }


    @Test
    @DisplayName("get matches by match id")
    public void getMatchesByMatchId() {
        when(licenseParser.parse(matchLicenseStringList)).thenReturn(matchLicenseList);
        when(matchRepository.findByMatchIdIn(matchLicenseList.stream().map(License::getId).collect(Collectors.toList()))).thenReturn(matchIdMatchDocumentList);
        when(matchConverter.convert(matchIdMatchDocumentList)).thenReturn(matchIdMatchList);
        List<Match> matches = matchService.getMatches(matchLicenseStringList);
        assertNotNull(matches);
        assertEquals(2, matches.size());
        assertEquals("m1", matches.get(0).getMatchId());
        assertEquals("m2", matches.get(1).getMatchId());

    }

    @Test
    @DisplayName("get matches by tournament id")
    public void getMatchesByTournamentId() {
        when(licenseParser.parse(tournamentLicenseStringList)).thenReturn(tournamentLicenseList);
        when(matchRepository.findByTournamentIdIn(tournamentLicenseList.stream().map(License::getId).collect(Collectors.toList()))).thenReturn(tournamentIdMatchDocumentList);
        when(matchConverter.convert(tournamentIdMatchDocumentList)).thenReturn(tournamentIdMatchList);
        List<Match> matches = matchService.getMatches(tournamentLicenseStringList);
        assertNotNull(matches);
        assertEquals(2, matches.size());
        assertEquals("m1", matches.get(0).getMatchId());
        assertEquals("m3", matches.get(1).getMatchId());

    }

    @Test
    @DisplayName("get matches by match and tournament id")
    public void getMatchesByMatchAndTournamentId() {
        when(licenseParser.parse(mixedLicenseStringList)).thenReturn(mixedLicenseList);
        when(matchRepository.findByMatchIdIn(mixedLicenseList.stream().map(License::getId).filter(s -> s.equals("m4")).collect(Collectors.toList()))).thenReturn(mixedMatchDocumentList.stream().filter(matchDocument -> matchDocument.getMatchId().equals("m4")).collect(Collectors.toList()));
        when(matchRepository.findByTournamentIdIn(mixedLicenseList.stream().map(License::getId).filter(s -> s.equals("t1")).collect(Collectors.toList()))).thenReturn(mixedMatchDocumentList.stream().filter(matchDocument -> matchDocument.getTournamentId().equals("t1")).collect(Collectors.toList()));
        when(matchConverter.convert(mixedMatchDocumentList)).thenReturn(mixedMatchList);
        List<Match> matches = matchService.getMatches(mixedLicenseStringList);
        assertNotNull(matches);
        assertEquals(2, matches.size());
        assertEquals("m1", matches.get(0).getMatchId());
        assertEquals("m4", matches.get(1).getMatchId());

    }
}
