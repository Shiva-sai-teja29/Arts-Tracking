package com.arts.Arts.Tracking.business;

import com.arts.Arts.Tracking.business.dto.Request;
import com.arts.Arts.Tracking.business.dto.Response;
import com.arts.Arts.Tracking.business.entity.*;
import com.arts.Arts.Tracking.business.repo.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class Services {

    public ArtsRepository artsRepo;
    public SubArtsRepository subArtsRepo;
    public AcademyRepository academyRepo;
    public CoachRepository coachRepo;
    public StudentRepository studentRepo;

    public Services(ArtsRepository artsRepo, SubArtsRepository subArtsRepo, AcademyRepository academyRepo, CoachRepository coachRepo, StudentRepository studentRepo) {
        this.artsRepo = artsRepo;
        this.subArtsRepo = subArtsRepo;
        this.academyRepo = academyRepo;
        this.coachRepo = coachRepo;
        this.studentRepo = studentRepo;
    }

    public List<Arts> getArts() {
        return artsRepo.findAll();
    }

    public Response addArts(Request request) {
        Arts arts = new Arts();
        arts.setArtName(request.getRequest());
        arts.setDescription(request.getDescription());
        artsRepo.save(arts);
        return new Response(arts.getArtName(), arts.getDescription());
    }

    public List<SubArts> getSubArts(String art) {
        Arts arts = artsRepo.findByArtName(art).orElseThrow(() -> new RuntimeException("Art not found"));
        return subArtsRepo.findByArtId(arts.getArtId());
    }

    public Response addSubArts(Request request, String art) {
        Arts arts = artsRepo.findByArtName(art).orElseThrow(() -> new RuntimeException("Art not found"));
        SubArts subArts = new SubArts();
        subArts.setArts(arts);
        subArts.setSubArtName(request.getRequest());
        subArts.setDescription(request.getDescription());
        subArtsRepo.save(subArts);
        return new Response(subArts.getSubArtName(), subArts.getDescription());
    }

    public List<Academy> getAcademies(String subArt) {
        SubArts subArts = subArtsRepo.findBySubArtName(subArt).orElseThrow(() -> new RuntimeException("SubArt not found"));
        return academyRepo.findBySubArtId(subArts.getSubArtId());
    }

    public Response addAcademy(Request request, String subArt) {
        SubArts subArts = subArtsRepo.findBySubArtName(subArt).orElseThrow(() -> new RuntimeException("SubArt not found"));
        Academy academy = new Academy();
        academy.setSubArts(new HashSet<>());
        academy.setAcademyName(request.getRequest());
        academy.setDescription(request.getDescription());
        academy.getSubArts().add(subArts);
        academyRepo.save(academy);
        return new Response(academy.getAcademyName(), academy.getDescription());
    }

    public List<Coach> getCoach(String academy) {
        Academy academy1 = academyRepo.findByAcademyName(academy).orElseThrow(() -> new RuntimeException("Academy not found for: "+academy));
        return coachRepo.findByAcademyId(academy1.getAcademyId());
    }

    public Response addCoach(Coach coachRequest, String academy) {
        Academy academy1 = academyRepo.findByAcademyName(academy).orElseThrow(() -> new RuntimeException("Academy not found for: "+academy));
        Coach coach = new Coach();
        //coach.setAcademies(Collections.singletonList(academy1));
        coach.setCoachName(coachRequest.getCoachName());
        coach.setDescription(coachRequest.getDescription());
        coach.setArea(coachRequest.getArea());
        coach.setContact(coachRequest.getContact());
        coachRepo.save(coach);
        return new Response(coach.getCoachName(), "Coach: "+coach.getCoachName()+" with Academy: "+academy+" with desc: "+coach.getDescription()+" with contact: "+coach.getContact()+" in area: "+coach.getArea());
    }

//    public List<Student> getStudentList(String coach) {
//        Coach coach1 = coachRepo.findByCoachName(coach).orElseThrow(() -> new RuntimeException("Coach not found for: "+coach));
//        return studentRepo.findByCoachId(coach1.getCoachId());
//    }

    public Response addStudent(Student student, String coach) {
        Coach coach1 = coachRepo.findByCoachName(coach).orElseThrow(() -> new RuntimeException("Coach not found for: "+coach));
        Student student1 = new Student();
        student1.setStudentName(student.getStudentName());
        student1.setArea(student.getArea());
        student1.setContact(student.getContact());
        //student1.setCoaches(Collections.singletonList(coach1));
        student1.setDescription(student.getDescription());
        studentRepo.save(student1);
        return new Response(student1.getStudentName(), "Coach: "+student1.getStudentName()+" with Coach: "+coach+" with desc: "+student1.getDescription()+" with contact: "+student1.getContact()+" in area: "+student1.getArea());
    }

    public Student getStudent(Long studentId) {
        return studentRepo.findById(studentId).orElseThrow(()-> new RuntimeException("Could not find for Student id: "+studentId));
    }

    public Coach getCoachDetails(Long coachId) {
        return coachRepo.findById(coachId).orElseThrow(()-> new RuntimeException("Could not find for coach id: "+coachId));
    }
}
