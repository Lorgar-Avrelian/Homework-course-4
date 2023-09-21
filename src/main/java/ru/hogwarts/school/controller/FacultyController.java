package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAll();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(faculty);
        }
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        Faculty editedFaculty = facultyService.editFaculty(faculty);
        if (editedFaculty == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.ok().body(editedFaculty);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/color/{color}")
    public ResponseEntity<Collection<Faculty>> facultiesByColor(@PathVariable String color) {
        Collection<Faculty> facultiesWithColor = facultyService.findByColor(color);
        if (facultiesWithColor == null) {
            return ResponseEntity.status(400).build();
        } else {
            return ResponseEntity.ok().body(facultiesWithColor);
        }
    }

    @GetMapping(path = "/find")
    public ResponseEntity findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(@RequestParam String string) {
        if (string.isBlank()) {
            return ResponseEntity.status(406).build();
        }
        Collection<Faculty> facultyCollectionByColorOrName = facultyService.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(string);
        return ResponseEntity.ok().body(facultyCollectionByColorOrName);
    }

    @GetMapping(path = "/students")
    public Collection<Student> findFacultyStudents(@RequestBody Faculty faculty) {
        return facultyService.findFacultyStudents(faculty);
    }
}
