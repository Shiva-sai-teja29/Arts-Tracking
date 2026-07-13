package com.arts.Arts.Tracking.business.controller;

import com.arts.Arts.Tracking.business.Services;
import com.arts.Arts.Tracking.business.dto.Request;
import com.arts.Arts.Tracking.business.dto.Response;
import com.arts.Arts.Tracking.business.entity.Academy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/arts")
public class AcademyController {

    public Services services;

    public AcademyController(Services services) {
        this.services = services;
    }

    @GetMapping("/AcademyList/{subArt}")
    public ResponseEntity<List<Academy>> getAcademy(@PathVariable String subArt){
        List<Academy> academies = services.getAcademies(subArt);
        return ResponseEntity.ok(academies);
    }

//    @PostMapping("/AddAcademy/{subArt}")
//    public ResponseEntity<Response> addAcademy(@RequestBodycRequest request, @PathVariable String subArt){
//        Response response = services.addAcademy(request, subArt);
//        return ResponseEntity.ok(response);
//    }
}
