package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> getAll();

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Faculty findStudentFaculty(Student student);

    int getStudentsCount();

    double getStudentsAgeAverage();

    List<Student> getLastStudents(int size);

    List<String> getSortedNames(char letter);

    double getMedianAge();
}
