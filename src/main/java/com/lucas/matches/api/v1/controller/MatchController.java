package com.lucas.matches.api.v1.controller;
import com.lucas.matches.api.v1.converter.MatchResponseConverter;
import com.lucas.matches.api.v1.dto.MatchResponse;

import com.lucas.matches.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/match")
public class MatchController {
    private final MatchService matchService;
    private final MatchResponseConverter matchResponseConverter;

    public MatchController(MatchService matchService, MatchResponseConverter matchResponseConverter) {
        this.matchService = matchService;
        this.matchResponseConverter = matchResponseConverter;
    }

    @GetMapping("")
    public ResponseEntity<List<MatchResponse>> getMatches(@RequestParam List<String> licenses){
        return ResponseEntity.ok(matchResponseConverter.convert(matchService.getMatches(licenses)));
    }
}
