package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long facultyId = 0;
    @Override
    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++facultyId);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(Long id) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        return faculties.get(id);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(Long id) {
        return faculties.remove(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        return faculties.values();
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        return faculties.values().stream()
                .filter(e -> e.getColor().equals(color))
                .toList();
    }
}
