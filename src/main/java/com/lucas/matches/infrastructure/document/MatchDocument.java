package com.lucas.matches.infrastructure.document;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Objects;

@Document(collection = "matches")
public class MatchDocument {
    @MongoId
    private String id;
    private String tournamentId;
    private String matchId;
    private String playerA;
    private String playerB;
    private LocalDateTime startDate;
    private SummaryType summaryType;

    public MatchDocument() {
    }

    public MatchDocument(String id, String tournamentId, String matchId, String playerA, String playerB, LocalDateTime startDate, SummaryType summaryType) {
        this.id = id;
        this.tournamentId = tournamentId;
        this.matchId = matchId;
        this.playerA = playerA;
        this.playerB = playerB;
        this.startDate = startDate;
        this.summaryType = summaryType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getPlayerA() {
        return playerA;
    }

    public void setPlayerA(String playerA) {
        this.playerA = playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public void setPlayerB(String playerB) {
        this.playerB = playerB;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public SummaryType getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(SummaryType summaryType) {
        this.summaryType = summaryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchDocument that = (MatchDocument) o;
        return Objects.equals(id, that.id) && Objects.equals(tournamentId, that.tournamentId) && Objects.equals(matchId, that.matchId) && Objects.equals(playerA, that.playerA) && Objects.equals(playerB, that.playerB) && Objects.equals(startDate, that.startDate) && summaryType == that.summaryType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tournamentId, matchId, playerA, playerB, startDate, summaryType);
    }

    @Override
    public String toString() {
        return "MatchDocument{" +
                "id='" + id + '\'' +
                ", tournamentId='" + tournamentId + '\'' +
                ", matchId='" + matchId + '\'' +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", startDate=" + startDate +
                ", summaryType=" + summaryType +
                '}';
    }
}
