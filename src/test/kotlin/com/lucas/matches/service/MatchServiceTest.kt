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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchServiceTest {
    private lateinit var matchService: MatchService

    @MockBean
    private var matchRepository: MatchRepository = Mockito.mock(MatchRepository::class.java)

    @MockBean
    private var licenseParser: LicenseParser = Mockito.mock(LicenseParser::class.java)

    @MockBean
    private var matchConverter: MatchConverter = Mockito.mock(MatchConverter::class.java)

    private lateinit var matchLicenseStringList: List<String>
    private lateinit var tournamentLicenseStringList: List<String>
    private lateinit var mixedLicenseStringList: List<String>
    private lateinit var matchLicenseList: List<License>
    private lateinit var tournamentLicenseList: List<License>
    private lateinit var mixedLicenseList: List<License>
    private lateinit var matchIdMatchDocumentList: List<MatchDocument>
    private lateinit var tournamentIdMatchDocumentList: List<MatchDocument>
    private lateinit var mixedMatchDocumentList: List<MatchDocument>
    private lateinit var matchIdMatchList: List<Match>
    private lateinit var tournamentIdMatchList: List<Match>
    private lateinit var mixedMatchList: List<Match>
    @BeforeAll
    fun setup() {
        matchService = MatchService(licenseParser, matchRepository, matchConverter)
        matchLicenseStringList = listOf("MATCH-m1", "MATCH-m2")
        tournamentLicenseStringList = listOf("TOURNAMENT-t1", "TOURNAMENT-t2")
        mixedLicenseStringList = listOf("TOURNAMENT-t1", "MATCH-m4")
        matchLicenseList = listOf(License(LicenseType.MATCH, "m1"), License(LicenseType.MATCH, "m2"))
        tournamentLicenseList =
            listOf(License(LicenseType.TOURNAMENT, "t1"), License(LicenseType.TOURNAMENT, "t2"))
        mixedLicenseList = listOf(License(LicenseType.TOURNAMENT, "t1"), License(LicenseType.MATCH, "m4"))
        matchIdMatchDocumentList = listOf(
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
            MatchDocument("2", "t3", "m2", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        )
        tournamentIdMatchDocumentList = listOf(
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
            MatchDocument("3", "t2", "m3", "playerA", "playerB", LocalDateTime.now(), null)
        )
        mixedMatchDocumentList = listOf(
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
            MatchDocument("4", "t4", "m4", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        )
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
    }

    @get:DisplayName("get matches by match id")
    @get:Test
    val matchesByMatchId: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    matchLicenseStringList
                )
            ).thenReturn(matchLicenseList)
            Mockito.`when`(
                matchRepository.findByMatchIdIn(
                    matchLicenseList.map(License::id)
                )
            ).thenReturn(matchIdMatchDocumentList)
            Mockito.`when`(
                matchConverter.convert(
                    matchIdMatchDocumentList
                )
            ).thenReturn(matchIdMatchList)
            val matches = matchService.getMatches(matchLicenseStringList)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(2, matches.size)
            Assertions.assertEquals("m1", matches[0].matchId)
            Assertions.assertEquals("m2", matches[1].matchId)
        }

    @get:DisplayName("get matches by tournament id")
    @get:Test
    val matchesByTournamentId: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    tournamentLicenseStringList
                )
            ).thenReturn(tournamentLicenseList)
            Mockito.`when`(
                matchRepository.findByTournamentIdIn(
                    tournamentLicenseList.map(License::id)
                )
            ).thenReturn(tournamentIdMatchDocumentList)
            Mockito.`when`(
                matchConverter.convert(
                    tournamentIdMatchDocumentList
                )
            ).thenReturn(tournamentIdMatchList)
            val matches = matchService.getMatches(tournamentLicenseStringList)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(2, matches.size)
            Assertions.assertEquals("m1", matches[0].matchId)
            Assertions.assertEquals("m3", matches[1].matchId)
        }

    @get:DisplayName("get matches by match and tournament id")
    @get:Test
    val matchesByMatchAndTournamentId: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    mixedLicenseStringList
                )
            ).thenReturn(mixedLicenseList)
            Mockito.`when`(
                matchRepository.findByMatchIdIn(
                    mixedLicenseList.map(License::id).filter { it == "m4" }
                        )).thenReturn(
                mixedMatchDocumentList
                    .filter { it.matchId == "m4" }
                    )
            Mockito.`when`(
                matchRepository.findByTournamentIdIn(
                    mixedLicenseList.map(License::id).filter { it == "t1" }
                        )).thenReturn(
                mixedMatchDocumentList
                    .filter { it.tournamentId == "t1" }
                    )
            Mockito.`when`(
                matchConverter.convert(
                    mixedMatchDocumentList
                )
            ).thenReturn(mixedMatchList)
            val matches = matchService.getMatches(mixedLicenseStringList)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(2, matches.size)
            Assertions.assertEquals("m1", matches[0].matchId)
            Assertions.assertEquals("m4", matches[1].matchId)
        }

    @get:DisplayName("get no Matches")
    @get:Test
    val noMatches: Unit
        get() {
            Mockito.`when`(
                licenseParser.parse(
                    mixedLicenseStringList
                )
            ).thenReturn(mixedLicenseList)
            Mockito.`when`(
                matchRepository.findByMatchIdIn(
                    mixedLicenseList.map(License::id).filter { it == "m4" }
                        )).thenReturn(ArrayList())
            Mockito.`when`(
                matchRepository.findByTournamentIdIn(
                    mixedLicenseList.map(License::id).filter { it == "t1" }
                        )).thenReturn(ArrayList())
            Mockito.`when`(matchConverter.convert(ArrayList())).thenReturn(ArrayList())
            val matches = matchService.getMatches(mixedLicenseStringList)
            Assertions.assertNotNull(matches)
            Assertions.assertEquals(0, matches.size)
        }
}