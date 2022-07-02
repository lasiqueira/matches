package com.lucas.matches.service

import com.lucas.matches.infrastructure.document.MatchDocument
import com.lucas.matches.infrastructure.repository.MatchRepository
import com.lucas.matches.service.converter.MatchConverter
import com.lucas.matches.service.domain.License
import com.lucas.matches.service.domain.LicenseType
import com.lucas.matches.service.domain.Match
import com.lucas.matches.service.util.LicenseParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["match"])
class MatchService(licenseParser: LicenseParser, matchRepository: MatchRepository, matchConverter: MatchConverter) {
    private val logger: Logger = LoggerFactory.getLogger(MatchService::class.java)
    private val licenseParser: LicenseParser
    private val matchRepository: MatchRepository
    private val matchConverter: MatchConverter
    @Cacheable(key = "#licenseStringList")
    fun getMatches(licenseStringList: List<String>): List<Match> {
        logger.info("Parsing Licenses...")
        logger.debug(licenseStringList.toString())
        val licenses = licenseParser.parse(licenseStringList)
        logger.info("Fetching matches...")
        val tournamentLicenses =
            licenses.filter { it.licenseType == LicenseType.TOURNAMENT }

        val matchLicenses =
            licenses.filter {it.licenseType == LicenseType.MATCH }
        return matchConverter.convert(
                getMatchesByTournamentId(tournamentLicenses)+
                getMatchesByMatchId(matchLicenses)
            )

    }

    private fun getMatchesByTournamentId(tournamentLicenses: List<License>): List<MatchDocument> {
        logger.info("Fetching Matches by Tournament ID...")
        val ids = tournamentLicenses.map { it.id }
        return matchRepository.findByTournamentIdIn(ids)


    }

    private fun getMatchesByMatchId(matchLicenses: List<License>): List<MatchDocument> {
        logger.info("Fetching Matches by Match ID...")
        val ids = matchLicenses.map { it.id }
        return matchRepository.findByMatchIdIn(ids)
    }

    init {
        this.licenseParser = licenseParser
        this.matchRepository = matchRepository
        this.matchConverter = matchConverter
    }
}