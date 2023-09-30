package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Creating student {}", student);
        Student answer = studentRepository.save(student);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Student findStudent(Long id) {
        logger.info("Searching student with id {}", id);
        Student answer = studentRepository.findById(id).get();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Editing student {}", student);
        Student answer = studentRepository.save(student);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Deleting student with id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAll() {
        logger.info("Trying to get list of students");
        List<Student> answer = studentRepository.findAll();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Collection<Student> findByAge(int age) {
        logger.info("Trying to find all students with age {}", age);
        Collection<Student> answer = studentRepository.findByAge(age);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Trying to find all students in age between {} and {}", minAge, maxAge);
        Collection<Student> answer = studentRepository.findByAgeBetween(minAge, maxAge);
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public Faculty findStudentFaculty(Student student) {
        logger.info("Getting student {} faculty", student);
        Faculty answer = student.getFaculty();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public int getStudentsCount() {
        logger.info("Getting students count");
        int answer = studentRepository.studentsCount();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public double getStudentsAgeAverage() {
        logger.info("Getting students average");
        double answer = studentRepository.studentAgeAverage();
        logger.debug("Getting answer {}", answer);
        return answer;
    }

    @Override
    public List<Student> getLastStudents(int size) {
        logger.info("Getting last {} students", size);
        List<Student> answer = studentRepository.lastStudents(size);
        logger.debug("Getting answer {}", answer);
        return answer;
    }
}
