package com.lucas.matches.infrastructure.repository

import com.lucas.matches.infrastructure.document.MatchDocument
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository : MongoRepository<MatchDocument, String> {
    fun findByMatchIdIn(matchIds: List<String>): List<MatchDocument>
    fun findByTournamentIdIn(tournamentIds: List<String>): List<MatchDocument>
}