package com.lucas.matches.service.util;

import com.lucas.matches.service.exception.InvalidLicenseException;
import com.lucas.matches.service.domain.License;
import com.lucas.matches.service.domain.LicenseType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

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
        List<License> licenses = licenseParser.parse(List.of(licenseString));

        assertNotNull(licenses);
        assertNotNull(licenses.get(0));
        assertEquals(LicenseType.TOURNAMENT, licenses.get(0).getLicenseType());
    }

    @Test
    @DisplayName("Parse a valid match license")
    public void parseValidMatchLicense(){
        String licenseString = "MATCH-m1";
        List<License> licenses = licenseParser.parse(List.of(licenseString));

        assertNotNull(licenses);
        assertNotNull(licenses.get(0));
        assertEquals(LicenseType.MATCH, licenses.get(0).getLicenseType());
    }
    @Test
    @DisplayName("Parse multiple valid licenses")
    public void parseMultipleValidLicenses(){
        List<String> licenseStringList = List.of("TOURNAMENT-t1", "MATCH-m1");
        List<License> licenses = licenseParser.parse(licenseStringList);

        assertNotNull(licenses);
        assertEquals(2, licenses.size());
        assertEquals(LicenseType.TOURNAMENT, licenses.get(0).getLicenseType());
        assertEquals(LicenseType.MATCH, licenses.get(1).getLicenseType());
    }

    @Test
    @DisplayName("Parse an invalid license")
    public void parseInvalidLicense(){
        String licenseString = "invalid-m1";
        assertThrows(InvalidLicenseException.class, ()-> licenseParser.parse(List.of(licenseString)));

    }

    @Test
    @DisplayName("Parse badly formatted license")
    public void parseBadlyFormattedLicense(){
        String licenseString = "MATCHm1";
        assertThrows(InvalidLicenseException.class, ()-> licenseParser.parse(List.of(licenseString)));

    }


}
