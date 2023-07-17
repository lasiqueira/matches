package com.lucas.matches.service.converter

import com.lucas.matches.infrastructure.document.MatchDocument
import com.lucas.matches.infrastructure.document.SummaryTypeDocument
import com.lucas.matches.service.domain.SummaryType
import org.junit.jupiter.api.*
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MatchConverterTest {
    private lateinit var avbMatchDocument: MatchDocument
    private lateinit var avbTimeMatchDocument: MatchDocument
    private lateinit var noSummaryTypeMatchDocument: MatchDocument
    private lateinit var matchConverter: MatchConverter
    @BeforeAll
    fun setup() {
        avbMatchDocument =
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB)
        avbTimeMatchDocument =
            MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        noSummaryTypeMatchDocument = MatchDocument("1", "t1", "m1", "playerA", "playerB", LocalDateTime.now(), null)
        matchConverter = MatchConverter()
    }

    @Test
    @DisplayName("convert MatchDocument with AVB SummaryType to Match")
    fun convertMatchDocumentAvbToMatch() {
        val match = matchConverter.convert(listOf(avbMatchDocument))
        Assertions.assertNotNull(match)
        Assertions.assertEquals(1, match.size)
        Assertions.assertEquals(SummaryType.AVB, match[0].summaryType)
    }

    @Test
    @DisplayName("convert MatchDocument with AVBTime SummaryType to Match")
    fun convertMatchDocumentAvbTimeToMatch() {
        val match = matchConverter.convert(listOf(avbTimeMatchDocument))
        Assertions.assertNotNull(match)
        Assertions.assertEquals(1, match.size)
        Assertions.assertEquals(SummaryType.AVBTIME, match[0].summaryType)
    }

    @Test
    @DisplayName("convert MatchDocument with no SummaryType to Match")
    fun convertMatchDocumentNoSummaryTypeToMatch() {
        val match = matchConverter.convert(listOf(noSummaryTypeMatchDocument))
        Assertions.assertNotNull(match)
        Assertions.assertEquals(1, match.size)
        Assertions.assertNull(match[0].summaryType)
    }

    @Test
    @DisplayName("convert empty List")
    fun convertEmptyList() {
        val match = matchConverter.convert(ArrayList())
        Assertions.assertNotNull(match)
        Assertions.assertEquals(0, match.size)
    }
}