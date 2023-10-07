package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    Object sync = new Object();
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

    @Override
    public List<String> getSortedNames(char letter) {
        return studentRepository.findAll().stream()
                .parallel()
                .map(Student::getName)
                .toList()
                .stream().distinct()
                .toList()
                .stream()
                .parallel()
                .filter(name -> name.startsWith(String.valueOf(letter)))
                .toList()
                .stream().sorted(Comparator.naturalOrder())
                .toList()
                .stream().map(String::toUpperCase)
                .toList();
    }

    @Override
    public double getMedianAge() {
        int summaryAge = studentRepository.findAll().stream()
                .parallel()
                .map(Student::getAge)
                .toList()
                .stream()
                .parallel()
                .mapToInt(age -> age)
                .sum();
        return (double) (summaryAge / studentRepository.studentsCount());
    }

    @Override
    public void printStudentsNames() {
        List<String> studentsNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .toList();
        new Thread(() -> {
            System.out.println(studentsNames.get(0));
            System.out.println(studentsNames.get(1));;
        }).start();
        new Thread(() -> {
            System.out.println(studentsNames.get(2));
            System.out.println(studentsNames.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(studentsNames.get(4));
            System.out.println(studentsNames.get(5));
        }).start();
    }
    @Override
    public void syncPrintStudentsNames() {
        List<String> studentsNames = studentRepository.findAll().stream()
                .map(Student::getName)
                .toList();
        new Thread(() -> {
            syncPrint(studentsNames.get(0));
            syncPrint(studentsNames.get(1));;
        }).start();
        new Thread(() -> {
            syncPrint(studentsNames.get(2));
            syncPrint(studentsNames.get(3));
        }).start();
        new Thread(() -> {
            syncPrint(studentsNames.get(4));
            syncPrint(studentsNames.get(5));
        }).start();
    }

    private void syncPrint(String name) {
        synchronized (sync) {
            System.out.println(name);
        }
    }
}
