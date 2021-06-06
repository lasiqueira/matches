package com.lucas.matches.api.v1.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class MatchResponse {
    private String matchId;
    private LocalDateTime startDate;
    private String playerA;
    private String playerB;
    private String summary;

    public MatchResponse() {
    }

    public MatchResponse(String matchId, LocalDateTime startDate, String playerA, String playerB, String summary) {
        this.matchId = matchId;
        this.startDate = startDate;
        this.playerA = playerA;
        this.playerB = playerB;
        this.summary = summary;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchResponse matchResponse = (MatchResponse) o;
        return Objects.equals(matchId, matchResponse.matchId) && Objects.equals(startDate, matchResponse.startDate) && Objects.equals(playerA, matchResponse.playerA) && Objects.equals(playerB, matchResponse.playerB) && Objects.equals(summary, matchResponse.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, startDate, playerA, playerB, summary);
    }

    @Override
    public String toString() {
        return "MatchDTO{" +
                "matchId='" + matchId + '\'' +
                ", startDate=" + startDate +
                ", playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
