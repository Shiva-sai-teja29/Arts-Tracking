package com.arts.Arts.Tracking.business.controller;

import com.arts.Arts.Tracking.business.Services;
import com.arts.Arts.Tracking.business.dto.Response;
import com.arts.Arts.Tracking.business.entity.Coach;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/coach")
public class CoachController {

    public Services services;

    public CoachController(Services services) {
        this.services = services;
    }

    @GetMapping("/CoachList/{academy}")
    public ResponseEntity<List<Coach>> getCoach(@PathVariable String academy){
        List<Coach> coaches = services.getCoach(academy);
        return ResponseEntity.ok(coaches);
    }

    @GetMapping("/Coach/{coachId}")
    public ResponseEntity<Coach> getCoachDetails(@PathVariable Long coachId){
        Coach coaches = services.getCoachDetails(coachId);
        return ResponseEntity.ok(coaches);
    }

    @PostMapping("/AddCoach/{academy}")
    public ResponseEntity<Response> addCoach(@RequestBody Coach coach, @PathVariable String academy){
        Response response = services.addCoach(coach, academy);
        return ResponseEntity.ok(response);
    }
}
