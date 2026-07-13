package com.arts.Arts.Tracking.business.controller;

import com.arts.Arts.Tracking.business.Services;
import com.arts.Arts.Tracking.business.dto.Request;
import com.arts.Arts.Tracking.business.dto.Response;
import com.arts.Arts.Tracking.business.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/arts")
public class ArtController {

    public Services services;

    public ArtController(Services services) {
        this.services = services;
    }

    @GetMapping("/ArtsList")
    public ResponseEntity<List<Arts>> getArts(){
        List<Arts> arts = services.getArts();
        return ResponseEntity.ok(arts);
    }

    @PostMapping("/AddArt")
    public ResponseEntity<Response> addArts(@RequestBody Request request){
        Response response = services.addArts(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/SubArtsList/{art}")
    public ResponseEntity<List<SubArts>> getSubArts(@PathVariable String art){
        List<SubArts> subArts = services.getSubArts(art);
        return ResponseEntity.ok(subArts);
    }

    @PostMapping("/AddSubArt/{art}")
    public ResponseEntity<Response> addSubArts(@RequestBody Request request, @PathVariable String art){
        Response response = services.addSubArts(request, art);
        return ResponseEntity.ok(response);
    }




}
