package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl studentService;

    @Test
    void createStudent() {
    }

    @Test
    void findStudent() {
    }

    @Test
    void editStudent() {
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void getAll() {
    }

    @Test
    void findByAge() {
    }
}