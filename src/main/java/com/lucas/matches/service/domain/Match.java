package com.lucas.matches.service.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Match {
    private String matchId;
    private LocalDateTime startDate;
    private String playerA;
    private String playerB;
    private SummaryType summaryType;

    public Match() {
    }

    public Match(String matchId, LocalDateTime startDate, String playerA, String playerB, SummaryType summaryType) {
        this.matchId = matchId;
        this.startDate = startDate;
        this.playerA = playerA;
        this.playerB = playerB;
        this.summaryType = summaryType;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
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
        Match match = (Match) o;
        return Objects.equals(matchId, match.matchId) && Objects.equals(startDate, match.startDate) && Objects.equals(playerA, match.playerA) && Objects.equals(playerB, match.playerB) && summaryType == match.summaryType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, startDate, playerA, playerB, summaryType);
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId='" + matchId + '\'' +
                ", startDate=" + startDate +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", summaryType=" + summaryType +
                '}';
    }
}
