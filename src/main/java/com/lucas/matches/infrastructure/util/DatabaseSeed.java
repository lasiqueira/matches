package com.lucas.matches.infrastructure.util;

import com.lucas.matches.infrastructure.document.MatchDocument;
import com.lucas.matches.infrastructure.document.SummaryTypeDocument;
import com.lucas.matches.infrastructure.repository.MatchRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DatabaseSeed {
    private final MatchRepository matchRepository;

    public DatabaseSeed(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void seedDatabase(){
        matchRepository.deleteAll();
        matchRepository.insert(List.of(
                new MatchDocument(null, "t1", "m1", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVB),
                new MatchDocument(null, "t2", "m2", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME),
                new MatchDocument(null, "t1", "m3", "playerA", "playerB", LocalDateTime.now().minusMinutes(10), SummaryTypeDocument.AVBTIME),
                new MatchDocument(null, "t2", "m4", "playerA", "playerB", LocalDateTime.now().plusMinutes(10), SummaryTypeDocument.AVBTIME),
                new MatchDocument(null, "t2", "m5", "playerA", "playerB", LocalDateTime.now(), SummaryTypeDocument.AVBTIME)
        ));
    }
}
