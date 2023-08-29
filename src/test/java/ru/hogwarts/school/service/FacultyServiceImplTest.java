package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.repository.FacultyRepository;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl facultyService;

    @Test
    void createFaculty() {
    }

    @Test
    void findFaculty() {
    }

    @Test
    void editFaculty() {
    }

    @Test
    void deleteFaculty() {
    }

    @Test
    void getAll() {
    }

    @Test
    void findByColor() {
    }
}