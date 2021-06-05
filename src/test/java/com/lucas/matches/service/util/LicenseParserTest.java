package com.lucas.matches.service.util;

import com.lucas.matches.service.exception.InvalidLicenseException;
import com.lucas.matches.service.domain.License;
import com.lucas.matches.service.domain.LicenseType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LicenseParserTest {
    private LicenseParser licenseParser;
    @BeforeAll
    public void setup(){
        licenseParser = new LicenseParser();
    }


    @Test
    @DisplayName("Parse a valid tournament license")
    public void parseValidTournamentLicense(){
        String licenseString = "TOURNAMENT-t1";
        License license = licenseParser.parse(licenseString);

        assertNotNull(license);
        assertEquals(LicenseType.TOURNAMENT, license.getLicenseType());
    }

    @Test
    @DisplayName("Parse a valid match license")
    public void parseValidMatchLicense(){
        String licenseString = "MATCH-m1";
        License license = licenseParser.parse(licenseString);

        assertNotNull(license);
        assertEquals(LicenseType.MATCH, license.getLicenseType());
    }

    @Test
    @DisplayName("Parse an invalid license")
    public void parseInvalidLicense(){
        String licenseString = "invalid-m1";
        assertThrows(InvalidLicenseException.class, ()-> licenseParser.parse(licenseString));

    }

    @Test
    @DisplayName("Parse badly formatted license")
    public void parseBadlyFormattedLicense(){
        String licenseString = "MATCHm1";
        assertThrows(InvalidLicenseException.class, ()-> licenseParser.parse(licenseString));

    }


}
