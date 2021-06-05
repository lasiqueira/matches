package com.lucas.matches.infrastructure.repository;

import com.lucas.matches.infrastructure.document.MatchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<MatchDocument, String> {
    List<MatchDocument> findByMatchIdIn(List<String> matchIds);
    List<MatchDocument> findByTournamentIdIn(List<String> tournamentIds);
}
