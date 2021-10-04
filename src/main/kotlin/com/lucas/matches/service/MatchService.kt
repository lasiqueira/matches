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
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
@CacheConfig(cacheNames = ["match"])
class MatchService(licenseParser: LicenseParser, matchRepository: MatchRepository, matchConverter: MatchConverter) {
    private val logger: Logger = LoggerFactory.getLogger(MatchService::class.java)
    private val licenseParser: LicenseParser
    private val matchRepository: MatchRepository
    private val matchConverter: MatchConverter
    @Cacheable(key = "#licenseStringList")
    fun getMatches(licenseStringList: List<String>): List<Match?>? {
        logger.info("Parsing Licenses...")
        logger.debug(licenseStringList.toString())
        val licenses = licenseParser.parse(licenseStringList)
        logger.info("Fetching matches...")
        val tournamentLicenses =
            licenses!!.stream().filter { license: License? -> license?.licenseType == LicenseType.TOURNAMENT }
                .collect(Collectors.toList())
        val matchLicenses =
            licenses.stream().filter { license: License? -> license?.licenseType == LicenseType.MATCH }
                .collect(Collectors.toList())
        return matchConverter.convert(
            Stream.concat(
                getMatchesByTournamentId(tournamentLicenses)!!.stream(),
                getMatchesByMatchId(matchLicenses)!!.stream()
            ).collect(Collectors.toList())
        )
    }

    private fun getMatchesByTournamentId(tournamentLicenses: List<License?>): List<MatchDocument?>? {
        var matchDocuments: List<MatchDocument?>? = ArrayList()
        if (tournamentLicenses.size > 0) {
            logger.info("Fetching Matches by Tournament ID...")
            val ids = tournamentLicenses.stream().map { obj: License? -> obj?.id }
                .collect(Collectors.toList())
            matchDocuments = matchRepository.findByTournamentIdIn(ids)
        }
        return matchDocuments
    }

    private fun getMatchesByMatchId(matchLicenses: List<License?>): List<MatchDocument?>? {
        var matchDocuments: List<MatchDocument?>? = ArrayList()
        if (matchLicenses.size > 0) {
            logger.info("Fetching Matches by Match ID...")
            val ids = matchLicenses.stream().map { obj: License? -> obj?.id }
                .collect(Collectors.toList())
            matchDocuments = matchRepository.findByMatchIdIn(ids)
        }
        return matchDocuments
    }

    init {
        this.licenseParser = licenseParser
        this.matchRepository = matchRepository
        this.matchConverter = matchConverter
    }
}