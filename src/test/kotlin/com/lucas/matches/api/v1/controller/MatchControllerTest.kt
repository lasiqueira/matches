package com.lucas.matches.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lucas.matches.api.v1.converter.MatchResponseConverter
import com.lucas.matches.api.v1.dto.MatchResponse
import com.lucas.matches.service.MatchService
import com.lucas.matches.service.domain.Match
import com.lucas.matches.service.domain.SummaryType
import com.lucas.matches.service.exception.InvalidLicenseException
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(MatchController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchControllerTest {
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var matchService: MatchService

    @MockBean
    private lateinit var matchResponseConverter: MatchResponseConverter
    private lateinit var matchLicenseStringList: List<String>
    private lateinit var tournamentLicenseStringList: List<String>
    private lateinit var mixedLicenseStringList: List<String>
    private lateinit var invalidLicenseStringList: List<String>
    private lateinit var matchIdMatchList: List<Match>
    private lateinit var tournamentIdMatchList: List<Match>
    private lateinit var mixedMatchList: List<Match>
    private lateinit var matchIdMatchResponseList: List<MatchResponse>
    private lateinit var tournamentIdMatchResponseList: List<MatchResponse>
    private lateinit var mixedMatchResponseList: List<MatchResponse>
    @BeforeAll
    fun setup() {
        matchLicenseStringList = listOf("MATCH-m1", "MATCH-m2")
        tournamentLicenseStringList = listOf("TOURNAMENT-t1", "TOURNAMENT-t2")
        mixedLicenseStringList = listOf("TOURNAMENT-t1", "MATCH-m4")
        invalidLicenseStringList = listOf("invalid")
        matchIdMatchList = listOf(
            Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
            Match("m2", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVBTIME)
        )
        tournamentIdMatchList = listOf(
            Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
            Match("m3", LocalDateTime.now(), "playerA", "playerB", null)
        )
        mixedMatchList = listOf(
            Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
            Match("m4", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVBTIME)
        )
        matchIdMatchResponseList = listOf(
            MatchResponse("m1", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB"),
            MatchResponse("m2", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB, started 1 minutes ago")
        )
        tournamentIdMatchResponseList = listOf(
            MatchResponse("m1", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB"),
            MatchResponse("m3", LocalDateTime.now(), "playerA", "playerB", "")
        )
        mixedMatchResponseList = listOf(
            MatchResponse("m1", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB"),
            MatchResponse("m4", LocalDateTime.now(), "playerA", "playerB", "playerA vs playerB, started 1 minutes ago")
        )
    }

    @get:Throws(Exception::class)
    @get:DisplayName("Get matches with Match License")
    @get:Test
    val matchesWithMatchLicense: Unit
        get() {
            Mockito.`when`(
                matchService.getMatches(
                    matchLicenseStringList
                )
            ).thenReturn(matchIdMatchList)
            Mockito.`when`(matchResponseConverter.convert(matchIdMatchList)).thenReturn(matchIdMatchResponseList)
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.addAll("licenses", matchLicenseStringList)
            mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/match")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
                .andExpect(
                    MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(matchIdMatchResponseList))
                )
        }

    @get:Throws(Exception::class)
    @get:DisplayName("Get Matches with Tournament License")
    @get:Test
    val matchesWithTournamentLicense: Unit
        get() {
            Mockito.`when`(
                matchService.getMatches(
                    tournamentLicenseStringList
                )
            ).thenReturn(tournamentIdMatchList)
            Mockito.`when`(matchResponseConverter.convert(tournamentIdMatchList))
                .thenReturn(tournamentIdMatchResponseList)
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.addAll("licenses", tournamentLicenseStringList)
            mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/match")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
                .andExpect(
                    MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(tournamentIdMatchResponseList))
                )
        }

    @get:Throws(Exception::class)
    @get:DisplayName("Get Matches with Mixed License")
    @get:Test
    val matchesWithMixedLicense: Unit
        get() {
            Mockito.`when`(
                matchService.getMatches(
                    mixedLicenseStringList
                )
            ).thenReturn(mixedMatchList)
            Mockito.`when`(matchResponseConverter.convert(mixedMatchList)).thenReturn(mixedMatchResponseList)
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.addAll("licenses", mixedLicenseStringList)
            mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/match")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(2)))
                .andExpect(
                    MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(mixedMatchResponseList))
                )
        }

    @get:Throws(
        Exception::class
    )
    @get:DisplayName("Bad Request when trying to get matches without a License")
    @get:Test
    val matchesWithNoLicense: Unit
        get() {
            mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/match")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
        }

    @get:Throws(
        Exception::class
    )
    @get:DisplayName("Bad Request when trying to get matches with an invalid License")
    @get:Test
    val matchesWithInvalidLicense: Unit
        get() {
            Mockito.`when`(
                matchService.getMatches(
                    invalidLicenseStringList
                )
            ).thenThrow(InvalidLicenseException("Invalid license."))
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.addAll("licenses", invalidLicenseStringList)
            mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/match")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
        }

    @get:Throws(Exception::class)
    @get:DisplayName("Get empty list")
    @get:Test
    val emptyList: Unit
        get() {
            Mockito.`when`(
                matchService.getMatches(
                    matchLicenseStringList
                )
            ).thenReturn(ArrayList())
            Mockito.`when`(matchResponseConverter.convert(ArrayList())).thenReturn(ArrayList())
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.addAll("licenses", matchLicenseStringList)
            mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/match")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize<Any>(0)))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ArrayList<Any>())))
        }
}