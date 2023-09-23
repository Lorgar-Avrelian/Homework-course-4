package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(student);
        }
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok().body(editedStudent);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/age/{age}")
    public ResponseEntity<Collection<Student>> findByAge(@PathVariable int age) {
        Collection<Student> studentsWithAge = studentService.findByAge(age);
        if (studentsWithAge == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(studentsWithAge);
        }
    }

    @GetMapping(path = "/age")
    public ResponseEntity findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        if (maxAge < minAge) {
            return ResponseEntity.status(406).build();
        }
        Collection<Student> ageBetweenStudents = studentService.findByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok().body(ageBetweenStudents);
    }

    @GetMapping(path = "/faculty")
    public Faculty findStudentFaculty(@RequestBody Student student) {
        return studentService.findStudentFaculty(student);
    }

    @GetMapping(path = "/count")
    public int getStudentsCount() {
        return studentService.getStudentsCount();
    }

    @GetMapping(path = "/ageAverage")
    public double getStudentsAverage() {
        return studentService.getStudentsAgeAverage();
    }

    @GetMapping(path = "/last")
    public List<Student> getLastStudents(@RequestParam int size) {
        return studentService.getLastStudents(size);
    }
}
