package com.lucas.matches.service;

import com.lucas.matches.infrastructure.document.MatchDocument;
import com.lucas.matches.infrastructure.repository.MatchRepository;
import com.lucas.matches.service.converter.MatchConverter;
import com.lucas.matches.service.domain.License;
import com.lucas.matches.service.domain.LicenseType;
import com.lucas.matches.service.domain.Match;
import com.lucas.matches.service.util.LicenseParser;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@CacheConfig(cacheNames = {"match"})
public class MatchService {
    private final LicenseParser licenseParser;
    private final MatchRepository matchRepository;
    private final MatchConverter matchConverter;

    public MatchService(LicenseParser licenseParser, MatchRepository matchRepository, MatchConverter matchConverter) {
        this.licenseParser = licenseParser;
        this.matchRepository = matchRepository;
        this.matchConverter = matchConverter;
    }
    @Cacheable(key = "#licenseStringList")
    public List<Match> getMatches(List<String> licenseStringList) {
        List<License> licenses = licenseParser.parse(licenseStringList);
        List<License> tournamentLicenses = licenses.stream().filter(license -> license.getLicenseType().equals(LicenseType.TOURNAMENT)).collect(Collectors.toList());
        List<License> matchLicenses = licenses.stream().filter(license -> license.getLicenseType().equals(LicenseType.MATCH)).collect(Collectors.toList());

        return matchConverter.convert(Stream.concat(
                getMatchesByTournamentId(tournamentLicenses).stream(),
                getMatchesByMatchId(matchLicenses).stream()).collect(Collectors.toList()));
    }

    private List<MatchDocument> getMatchesByTournamentId(List<License> tournamentLicenses) {
        List<MatchDocument> matchDocuments = new ArrayList<>();
        if (tournamentLicenses.size() > 0) {
            List<String> ids = tournamentLicenses.stream().map(License::getId).collect(Collectors.toList());
            matchDocuments = matchRepository.findByTournamentIdIn(ids);
        }
        return matchDocuments;
    }

    private List<MatchDocument> getMatchesByMatchId(List<License> matchLicenses) {
        List<MatchDocument> matchDocuments = new ArrayList<>();
        if (matchLicenses.size() > 0) {
            List<String> ids = matchLicenses.stream().map(License::getId).collect(Collectors.toList());
            matchDocuments = matchRepository.findByMatchIdIn(ids);
        }
        return matchDocuments;
    }

}
