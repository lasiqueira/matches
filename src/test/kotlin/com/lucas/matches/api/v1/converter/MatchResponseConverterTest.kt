package com.lucas.matches.api.v1.converter

import com.lucas.matches.service.domain.Match
import com.lucas.matches.service.domain.SummaryType
import org.junit.jupiter.api.*
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchResponseConverterTest {
    private lateinit var matchResponseConverter: MatchResponseConverter
    private lateinit var avbMatch: Match
    private lateinit var avbTimeAfterMatch: Match
    private lateinit var avbTimeBeforeMatch: Match
    private lateinit var noSummaryTypeMatch: Match
    @BeforeAll
    fun setup() {
        matchResponseConverter = MatchResponseConverter()
        avbMatch = Match("m1", LocalDateTime.now(), "playerA", "playerB", SummaryType.AVB)
        //had to add a few seconds otherwise it would report as 1 minute less if it's 59 seconds.
        avbTimeAfterMatch =
            Match("m1", LocalDateTime.now().plusMinutes(20).plusSeconds(5), "playerA", "playerB", SummaryType.AVBTIME)
        avbTimeBeforeMatch =
            Match("m1", LocalDateTime.now().minusMinutes(20), "playerA", "playerB", SummaryType.AVBTIME)
        noSummaryTypeMatch = Match("m1", LocalDateTime.now(), "playerA", "playerB", null)
    }

    @Test
    @DisplayName("convert Match with AVB SummaryType to MatchResponse")
    fun convertMatchAvbToMatchResponse() {
        val matchResponses = matchResponseConverter.convert(listOf(avbMatch))
        Assertions.assertNotNull(matchResponses)
        Assertions.assertEquals(1, matchResponses.size)
        Assertions.assertEquals("playerA vs playerB", matchResponses[0].summary)
    }

    @Test
    @DisplayName("convert Match with AVBTime SummaryType with startTime after now to MatchResponse")
    fun convertMatchAvbTimeAfterToMatchResponse() {
        val matchResponses = matchResponseConverter.convert(listOf(avbTimeAfterMatch))
        Assertions.assertNotNull(matchResponses)
        Assertions.assertEquals(1, matchResponses.size)
        Assertions.assertEquals("playerA vs playerB, starts in 20 minutes", matchResponses[0].summary)
    }

    @Test
    @DisplayName("convert Match with AVBTime SummaryType with startTime before now to MatchResponse")
    fun convertMatchAvbTimeBeforeToMatchResponse() {
        val matchResponses = matchResponseConverter.convert(listOf(avbTimeBeforeMatch))
        Assertions.assertNotNull(matchResponses)
        Assertions.assertEquals(1, matchResponses.size)
        Assertions.assertEquals("playerA vs playerB, started 20 minutes ago", matchResponses[0].summary)
    }

    @Test
    @DisplayName("convert Match with no SummaryType to MatchResponse")
    fun convertMatchNoSummaryTypeToMatchResponse() {
        val matchResponses = matchResponseConverter.convert(listOf(noSummaryTypeMatch))
        Assertions.assertNotNull(matchResponses)
        Assertions.assertEquals(1, matchResponses.size)
        Assertions.assertTrue(matchResponses[0].summary.isEmpty())
    }

    @Test
    @DisplayName("convert empty List")
    fun convertEmptyList() {
        val matchResponses = matchResponseConverter.convert(ArrayList())
        Assertions.assertNotNull(matchResponses)
        Assertions.assertEquals(0, matchResponses.size)
    }
}