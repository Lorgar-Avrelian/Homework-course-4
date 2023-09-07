package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.Constant.TEST_STUDENT_1;
import static ru.hogwarts.school.constants.Constant.TEST_STUDENT_COLLECTION;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl studentService;

    @Test
    void createStudent() {
        when(studentRepository.save(TEST_STUDENT_1)).thenReturn(TEST_STUDENT_1);
        assertEquals(TEST_STUDENT_1, studentService.createStudent(TEST_STUDENT_1));
    }

    @Test
    void findStudent() {
        Long testId = 1L;
        when(studentRepository.findById(testId)).thenReturn(Optional.of(TEST_STUDENT_1));
        assertEquals(TEST_STUDENT_1, studentService.findStudent(testId));
    }

    @Test
    void editStudent() {
        when(studentRepository.save(TEST_STUDENT_1)).thenReturn(TEST_STUDENT_1);
        assertEquals(TEST_STUDENT_1, studentService.editStudent(TEST_STUDENT_1));
    }

    @Test
    void deleteStudent() {
        Long testId = 1L;
        studentService.deleteStudent(testId);
        verify(studentRepository, times(1)).deleteById(testId);
    }

    @Test
    void getAll() {
        when(studentRepository.findAll()).thenReturn(TEST_STUDENT_COLLECTION);
        assertIterableEquals(TEST_STUDENT_COLLECTION, studentService.getAll());
    }

    @Test
    void findByAge() {
        int testAge = TEST_STUDENT_1.getAge();
        Collection<Student> testCollection = new ArrayList<>(List.of(TEST_STUDENT_1));
        when(studentRepository.findByAge(testAge)).thenReturn(testCollection);
        assertIterableEquals(testCollection, studentService.findByAge(testAge));
    }

    @Test
    void findByAgeBetween() {
        Collection<Student> testCollection = new ArrayList<>(List.of(TEST_STUDENT_1));
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(testCollection);
        assertIterableEquals(testCollection, studentService.findByAgeBetween(1, 10));
    }

    @Test
    void findStudentFaculty() {
        assertNull(studentService.findStudentFaculty(TEST_STUDENT_1));
    }
}