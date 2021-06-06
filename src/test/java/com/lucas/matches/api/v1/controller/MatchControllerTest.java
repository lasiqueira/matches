package com.lucas.matches.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.matches.api.v1.converter.MatchResponseConverter;
import com.lucas.matches.api.v1.dto.MatchResponse;
import com.lucas.matches.service.MatchService;
import com.lucas.matches.service.domain.Match;
import com.lucas.matches.service.domain.SummaryType;
import com.lucas.matches.service.exception.InvalidLicenseException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MatchController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MatchControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MatchService matchService;
    @MockBean
    private MatchResponseConverter matchResponseConverter;

    private List<String> matchLicenseStringList;
    private List<String> tournamentLicenseStringList;
    private List<String> mixedLicenseStringList;
    private List<String> invalidLicenseStringList;

    private List<Match> matchIdMatchList;
    private List<Match> tournamentIdMatchList;
    private List<Match> mixedMatchList;

    private List<MatchResponse> matchIdMatchResponseList;
    private List<MatchResponse> tournamentIdMatchResponseList;
    private List<MatchResponse> mixedMatchResponseList;

    @BeforeAll
    public void setup(){
        matchLicenseStringList = List.of("MATCH-m1", "MATCH-m2");
        tournamentLicenseStringList = List.of("TOURNAMENT-t1", "TOURNAMENT-t2");
        mixedLicenseStringList = List.of("TOURNAMENT-t1", "MATCH-m4");
        invalidLicenseStringList = List.of("invalid");


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

        matchIdMatchResponseList = List.of(
                new MatchResponse("m1", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB"),
                new MatchResponse("m2", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB, started 1 minutes ago")
        );

        tournamentIdMatchResponseList = List.of(
                new MatchResponse("m1", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB"),
                new MatchResponse("m3", LocalDateTime.now(), "playerA", "playerB", "")
        );

        mixedMatchResponseList = List.of(
                new MatchResponse("m1", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB"),
                new MatchResponse("m4", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB, started 1 minutes ago")
        );
    }
    @Test
    @DisplayName("Get matches with Match License")
    public void getMatchesWithMatchLicense() throws Exception {
        when(matchService.getMatches(matchLicenseStringList)).thenReturn(matchIdMatchList);
        when(matchResponseConverter.convert(matchIdMatchList)).thenReturn(matchIdMatchResponseList);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("licenses",matchLicenseStringList);
        mockMvc.perform(get("/v1/match")
                .params(params)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(matchIdMatchResponseList)));
    }

    @Test
    @DisplayName("Get Matches with Tournament License")
    public void getMatchesWithTournamentLicense() throws Exception {
        when(matchService.getMatches(tournamentLicenseStringList)).thenReturn(tournamentIdMatchList);
        when(matchResponseConverter.convert(tournamentIdMatchList)).thenReturn(tournamentIdMatchResponseList);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("licenses",tournamentLicenseStringList);
        mockMvc.perform(get("/v1/match")
                .params(params)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(tournamentIdMatchResponseList)));
    }

    @Test
    @DisplayName("Get Matches with Mixed License")
    public void getMatchesWithMixedLicense() throws Exception {
        when(matchService.getMatches(mixedLicenseStringList)).thenReturn(mixedMatchList);
        when(matchResponseConverter.convert(mixedMatchList)).thenReturn(mixedMatchResponseList);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("licenses", mixedLicenseStringList);
        mockMvc.perform(get("/v1/match")
                .params(params)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string(objectMapper.writeValueAsString(mixedMatchResponseList)));
    }


    @Test
    @DisplayName("Bad Request when trying to get matches without a License")
    public void getMatchesWithNoLicense() throws Exception {
        mockMvc.perform(get("/v1/match")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Bad Request when trying to get matches with an invalid License")
    public void getMatchesWithInvalidLicense() throws Exception {
        when(matchService.getMatches(invalidLicenseStringList)).thenThrow(new InvalidLicenseException("Invalid license."));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("licenses", invalidLicenseStringList);
        mockMvc.perform(get("/v1/match")
                .params(params)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("Get empty list")
    public void getEmptyList() throws Exception {
        when(matchService.getMatches(matchLicenseStringList)).thenReturn(new ArrayList<>());
        when(matchResponseConverter.convert(new ArrayList<>())).thenReturn(new ArrayList<>());
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("licenses", matchLicenseStringList);
        mockMvc.perform(get("/v1/match")
                .params(params)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(content().string(objectMapper.writeValueAsString(new ArrayList<>())));;
    }

}
