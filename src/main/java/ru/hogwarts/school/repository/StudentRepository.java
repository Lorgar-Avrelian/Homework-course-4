package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int studentsCount();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    double studentAgeAverage();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT :size", nativeQuery = true)
    List<Student> lastStudents(int size);
}
