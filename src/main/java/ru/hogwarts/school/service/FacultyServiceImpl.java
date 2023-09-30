package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Creating faculty {}", faculty);
        Faculty answer = facultyRepository.save(faculty);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Faculty findFaculty(Long id) {
        logger.info("Searching faculty with id {}", id);
        Faculty answer = facultyRepository.findById(id).get();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Editing faculty {}", faculty);
        Faculty answer = facultyRepository.save(faculty);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Deleting faculty with id {}", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAll() {
        logger.info("Trying to get list of faculties");
        List<Faculty> answer = facultyRepository.findAll();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        logger.info("Searching all faculties with color {}", color);
        Collection<Faculty> answer = facultyRepository.findByColor(color);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Collection<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String string) {
        logger.info("Searching faculties that name or color contains string {}", string);
        Collection<Faculty> answer = facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(string, string);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Collection<Student> findFacultyStudents(Faculty faculty) {
        logger.info("Trying to get all students that studying on the faculty {}", faculty);
        Collection<Student> answer = faculty.getStudents();
        logger.debug("Getting answer {}", answer);
        return answer;
    }
}
