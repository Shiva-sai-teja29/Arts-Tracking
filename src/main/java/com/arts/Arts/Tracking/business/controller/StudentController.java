package com.arts.Arts.Tracking.business.controller;

import com.arts.Arts.Tracking.business.Services;
import com.arts.Arts.Tracking.business.dto.Response;
import com.arts.Arts.Tracking.business.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/arts")
public class StudentController {

    public Services services;

    public StudentController(Services services) {
        this.services = services;
    }


//    @GetMapping("/StudentList/{coach}")
//    public ResponseEntity<List<Student>> getStudentList(@PathVariable String coach){
//        List<Student> students = services.getStudentList(coach);
//        return ResponseEntity.ok(students);
//    }

    @PostMapping("/AddStudent/{academy}")
    public ResponseEntity<Response> addStudent(@RequestBody Student student, @PathVariable String coach){
        Response response = services.addStudent(student, coach);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/Student/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId){
        Student students = services.getStudent(studentId);
        return ResponseEntity.ok(students);
    }
}
