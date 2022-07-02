package com.lucas.matches.service.util

import com.lucas.matches.service.domain.LicenseType
import com.lucas.matches.service.exception.InvalidLicenseException
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LicenseParserTest {
    private lateinit var licenseParser: LicenseParser
    @BeforeAll
    fun setup() {
        licenseParser = LicenseParser()
    }

    @Test
    @DisplayName("Parse a valid tournament license")
    fun parseValidTournamentLicense() {
        val licenseString = "TOURNAMENT-t1"
        val licenses = licenseParser.parse(listOf(licenseString))
        Assertions.assertNotNull(licenses)
        Assertions.assertNotNull(licenses[0])
        Assertions.assertEquals(LicenseType.TOURNAMENT, licenses[0].licenseType)
    }

    @Test
    @DisplayName("Parse a valid match license")
    fun parseValidMatchLicense() {
        val licenseString = "MATCH-m1"
        val licenses = licenseParser.parse(listOf(licenseString))
        Assertions.assertNotNull(licenses)
        Assertions.assertNotNull(licenses[0])
        Assertions.assertEquals(LicenseType.MATCH, licenses[0].licenseType)
    }

    @Test
    @DisplayName("Parse multiple valid licenses")
    fun parseMultipleValidLicenses() {
        val licenseStringList = listOf("TOURNAMENT-t1", "MATCH-m1")
        val licenses = licenseParser.parse(licenseStringList)
        Assertions.assertNotNull(licenses)
        Assertions.assertEquals(2, licenses.size)
        Assertions.assertEquals(LicenseType.TOURNAMENT, licenses[0].licenseType)
        Assertions.assertEquals(LicenseType.MATCH, licenses[1].licenseType)
    }

    @Test
    @DisplayName("Parse an invalid license")
    fun parseInvalidLicense() {
        val licenseString = "invalid-m1"
        Assertions.assertThrows(InvalidLicenseException::class.java) { licenseParser.parse(listOf(licenseString)) }
    }

    @Test
    @DisplayName("Parse badly formatted license")
    fun parseBadlyFormattedLicense() {
        val licenseString = "MATCHm1"
        Assertions.assertThrows(InvalidLicenseException::class.java) { licenseParser.parse(listOf(licenseString)) }
    }

    @Test
    @DisplayName("Parse empty license")
    fun parseEmptyLicense() {
        val licenseString = ""
        Assertions.assertThrows(InvalidLicenseException::class.java) { licenseParser.parse(listOf(licenseString)) }
    }
}