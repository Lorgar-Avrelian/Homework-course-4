package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constants.Constant.*;

class StudentServiceImplTest {
    StudentService studentService = new StudentServiceImpl();

    @Test
    void createStudent() {
        assertEquals(TEST_STUDENT_1, studentService.createStudent(TEST_STUDENT_1));
        assertEquals(TEST_STUDENT_2, studentService.createStudent(TEST_STUDENT_2));
        assertEquals(TEST_STUDENT_3, studentService.createStudent(TEST_STUDENT_3));
        assertEquals(TEST_STUDENT_4, studentService.createStudent(TEST_STUDENT_4));
        assertEquals(TEST_STUDENT_5, studentService.createStudent(TEST_STUDENT_5));
    }

    @Test
    void findStudent() {
        editTestStudents();
        assertEquals(TEST_STUDENT_1, studentService.findStudent(1L));
        assertEquals(TEST_STUDENT_2, studentService.findStudent(2L));
        assertEquals(TEST_STUDENT_3, studentService.findStudent(3L));
        assertEquals(TEST_STUDENT_4, studentService.findStudent(4L));
        assertEquals(TEST_STUDENT_5, studentService.findStudent(5L));
        assertNull(studentService.findStudent(6L));
    }

    @Test
    void editStudent() {
        editTestStudents();
        Student testEdit = new Student(1L, "TEST", 100);
        studentService.editStudent(testEdit);
        assertEquals(testEdit, studentService.findStudent(1L));
        editTestStudents();
    }

    @Test
    void deleteStudent() {
        editTestStudents();
        studentService.deleteStudent(1L);
        assertNull(studentService.findStudent(1L));
        editTestStudents();
    }

    @Test
    void getAll() {
        editTestStudents();
        Collection<Student> actualCollection = studentService.getAll();
        assertIterableEquals(TEST_STUDENT_COLLECTION, actualCollection);
    }

    @Test
    void findByAge() {
        editTestStudents();
        Collection<Student> actualCollection = studentService.findByAge(TEST_STUDENT_4.getAge());
        Collection<Student> expectedCollection = new ArrayList<>(List.of(TEST_STUDENT_4));
        assertIterableEquals(actualCollection, expectedCollection);
    }

    private void editTestStudents() {
        studentService.editStudent(TEST_STUDENT_1);
        studentService.editStudent(TEST_STUDENT_2);
        studentService.editStudent(TEST_STUDENT_3);
        studentService.editStudent(TEST_STUDENT_4);
        studentService.editStudent(TEST_STUDENT_5);
    }
}