package com.lucas.matches.service

import com.lucas.matches.infrastructure.document.MatchDocument
import com.lucas.matches.infrastructure.document.SummaryTypeDocument
import com.lucas.matches.infrastructure.repository.MatchRepository
import com.lucas.matches.service.converter.MatchConverter
import com.lucas.matches.service.domain.License
import com.lucas.matches.service.domain.LicenseType
import com.lucas.matches.service.domain.Match
import com.lucas.matches.service.domain.SummaryType
import com.lucas.matches.service.util.LicenseParser
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime
import java.util.stream.Collectors

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchServiceTest {
    private var matchService: MatchService? = null

    @MockBean
    private val matchRepository: MatchRepository = Mockito.mock(MatchRepository::class.java)

    @MockBean
    private var licenseParser: LicenseParser = Mockito.mock(LicenseParser::class.java)

    @MockBean
    private var matchConverter: MatchConverter = Mockito.mock(MatchConverter::class.java)

    private var matchLicenseStringList: List<String>? = null
    private var tournamentLicenseStringList: List<String>? = null
    private var mixedLicenseStringList: List<String>? = null
    private var matchLicenseList: List<License>? = null
    private var tournamentLicenseList: List<License>? = null
    private var mixedLicenseList: List<License>? = null
    private var matchIdMatchDocumentList: List<MatchDocument?>? = null
    private var tournamentIdMatchDocumentList: List<MatchDocument?>? = null
    private var mixedMatchDocumentList: List<MatchDocument?>? = null
    private var matchIdMatchList: List<Match>? = null
    private var tournamentIdMatchList: List<Match>? = null
    private var mixedMatchList: List<Match>? = null
    @BeforeAll
    fun setup() {
        matchService = MatchService(licenseParser, matchRepository, matchConverter)
        matchLicenseStringList = java.util.List.of("MATCH-m1", "MATCH-m2")
        tournamentLicenseStringList = java.util.List.of("TOURNAMENT-t1", "TOURNAMENT-t2")
        mixedLicenseStringList = java.util.List.of("TOURNAMENT-t1", "MATCH-m4")
        matchLicenseList = java.util.List.of(License(LicenseType.MATCH, "m1"), License(LicenseType.MATCH, "m2"))
        tournamentLicenseList =
            java.util.List.of(License(LicenseType.TOURNAMENT, "t1"), License(LicenseType.TOURNAMENT, "t2"))
        mixedLicenseList = java.util.List.of(License(LicenseType.TOURNAMENT, "t1"), License(LicenseType.MATCH, "m4"))
        matchIdMatchDocumentList = java.util.List.of(
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
            MatchDocument("2", "t3", "m2", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        )
        tournamentIdMatchDocumentList = java.util.List.of(
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
            MatchDocument("3", "t2", "m3", "playerA", "playerB", LocalDateTime.now(), null)
        )
        mixedMatchDocumentList = java.util.List.of(
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
            MatchDocument("4", "t4", "m4", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        )
        matchIdMatchList = java.util.List.of(
            Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
            Match("m2", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVBTIME)
        )
        tournamentIdMatchList = java.util.List.of(
            Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
            Match("m3", LocalDateTime.now(), "playerA", "playerB", null)
        )
        mixedMatchList = java.util.List.of(
            Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB),
            Match("m4", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVBTIME)
        )
    }

    @get:DisplayName("get matches by match id")
    @get:Test
    val matchesByMatchId: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    matchLicenseStringList!!
                )
            ).thenReturn(matchLicenseList)
            Mockito.`when`(
                matchRepository.findByMatchIdIn(
                    matchLicenseList!!.stream().map(License::id).collect(Collectors.toList())
                )
            ).thenReturn(matchIdMatchDocumentList)
            Mockito.`when`(
                matchConverter.convert(
                    matchIdMatchDocumentList!!
                )
            ).thenReturn(matchIdMatchList)
            val matches = matchService!!.getMatches(matchLicenseStringList!!)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(2, matches!!.size)
            Assertions.assertEquals("m1", matches[0]!!.matchId)
            Assertions.assertEquals("m2", matches[1]!!.matchId)
        }

    @get:DisplayName("get matches by tournament id")
    @get:Test
    val matchesByTournamentId: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    tournamentLicenseStringList!!
                )
            ).thenReturn(tournamentLicenseList)
            Mockito.`when`(
                matchRepository.findByTournamentIdIn(
                    tournamentLicenseList!!.stream().map(License::id).collect(Collectors.toList())
                )
            ).thenReturn(tournamentIdMatchDocumentList)
            Mockito.`when`(
                matchConverter.convert(
                    tournamentIdMatchDocumentList!!
                )
            ).thenReturn(tournamentIdMatchList)
            val matches = matchService!!.getMatches(tournamentLicenseStringList!!)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(2, matches!!.size)
            Assertions.assertEquals("m1", matches[0]!!.matchId)
            Assertions.assertEquals("m3", matches[1]!!.matchId)
        }

    @get:DisplayName("get matches by match and tournament id")
    @get:Test
    val matchesByMatchAndTournamentId: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    mixedLicenseStringList!!
                )
            ).thenReturn(mixedLicenseList)
            Mockito.`when`(
                matchRepository.findByMatchIdIn(
                    mixedLicenseList!!.stream().map(License::id).filter { s: String? -> s == "m4" }
                        .collect(Collectors.toList()))).thenReturn(
                mixedMatchDocumentList!!.stream()
                    .filter { matchDocument: MatchDocument? -> matchDocument!!.matchId == "m4" }
                    .collect(Collectors.toList()))
            Mockito.`when`(
                matchRepository.findByTournamentIdIn(
                    mixedLicenseList!!.stream().map(License::id).filter { s: String? -> s == "t1" }
                        .collect(Collectors.toList()))).thenReturn(
                mixedMatchDocumentList!!.stream()
                    .filter { matchDocument: MatchDocument? -> matchDocument!!.tournamentId == "t1" }
                    .collect(Collectors.toList()))
            Mockito.`when`(
                matchConverter.convert(
                    mixedMatchDocumentList!!
                )
            ).thenReturn(mixedMatchList)
            val matches = matchService!!.getMatches(mixedLicenseStringList!!)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(2, matches!!.size)
            Assertions.assertEquals("m1", matches[0]!!.matchId)
            Assertions.assertEquals("m4", matches[1]!!.matchId)
        }

    @get:DisplayName("get no Matches")
    @get:Test
    val noMatches: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    mixedLicenseStringList!!
                )
            ).thenReturn(mixedLicenseList)
            Mockito.`when`(
                matchRepository.findByMatchIdIn(
                    mixedLicenseList!!.stream().map(License::id).filter { s: String? -> s == "m4" }
                        .collect(Collectors.toList()))).thenReturn(ArrayList())
            Mockito.`when`(
                matchRepository.findByTournamentIdIn(
                    mixedLicenseList!!.stream().map(License::id).filter { s: String? -> s == "t1" }
                        .collect(Collectors.toList()))).thenReturn(ArrayList())
            Mockito.`when`(matchConverter.convert(ArrayList())).thenReturn(ArrayList())
            val matches = matchService!!.getMatches(mixedLicenseStringList!!)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(0, matches!!.size)
        }
}