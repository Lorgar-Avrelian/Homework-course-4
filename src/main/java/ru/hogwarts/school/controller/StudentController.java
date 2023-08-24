package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

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
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        Student deletedStudent = studentService.deleteStudent(id);
        if (deletedStudent == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(deletedStudent);
        }
    }

    @GetMapping(path = "{age}")
    public ResponseEntity<Collection<Student>> findByAge(@PathVariable int age) {
        Collection<Student> studentsWithAge = studentService.findByAge(age);
        if (studentsWithAge == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(studentsWithAge);
        }
    }
}
