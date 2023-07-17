package com.lucas.matches.api.v1.controller

import com.lucas.matches.api.v1.converter.MatchResponseConverter
import com.lucas.matches.api.v1.dto.MatchResponse
import com.lucas.matches.service.MatchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/match")
class MatchController(
    private val matchService: MatchService,
    private val matchResponseConverter: MatchResponseConverter
) {
    @GetMapping("")
    fun getMatches(@RequestParam licenses: List<String>): ResponseEntity<List<MatchResponse>> {
        return ResponseEntity.ok(matchResponseConverter.convert(matchService.getMatches(licenses)))
    }
}