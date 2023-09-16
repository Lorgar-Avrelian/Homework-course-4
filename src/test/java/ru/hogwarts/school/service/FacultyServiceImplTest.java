package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.hogwarts.school.constants.Constant.TEST_FACULTY_1;
import static ru.hogwarts.school.constants.Constant.TEST_FACULTY_COLLECTION;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl facultyService;

    @Test
    void createFaculty() {
        when(facultyRepository.save(TEST_FACULTY_1)).thenReturn(TEST_FACULTY_1);
        assertEquals(TEST_FACULTY_1, facultyService.createFaculty(TEST_FACULTY_1));
    }

    @Test
    void findFaculty() {
        Long testId = 1L;
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(TEST_FACULTY_1));
        assertEquals(TEST_FACULTY_1, facultyService.findFaculty(testId));
    }

    @Test
    void editFaculty() {
        when(facultyRepository.save(TEST_FACULTY_1)).thenReturn(TEST_FACULTY_1);
        assertEquals(TEST_FACULTY_1, facultyService.editFaculty(TEST_FACULTY_1));
    }

    @Test
    void deleteFaculty() {
        facultyService.deleteFaculty(TEST_FACULTY_1.getId());
        verify(facultyRepository, times(1)).deleteById(TEST_FACULTY_1.getId());
    }

    @Test
    void getAll() {
        when(facultyRepository.findAll()).thenReturn(TEST_FACULTY_COLLECTION);
        assertIterableEquals(TEST_FACULTY_COLLECTION, facultyService.getAll());
    }

    @Test
    void findByColor() {
        String testColor = TEST_FACULTY_1.getColor();
        Collection<Faculty> testCollection = new ArrayList<>(List.of(TEST_FACULTY_1));
        when(facultyRepository.findByColor(testColor)).thenReturn(testCollection);
        assertIterableEquals(testCollection, facultyService.findByColor(testColor));
    }

    @Test
    void findByColorContainsIgnoreCaseOrNameContainsIgnoreCase() {
        String testString = TEST_FACULTY_1.getColor();
        Collection<Faculty> testCollection = new ArrayList<>(List.of(TEST_FACULTY_1));
        when(facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(anyString(), anyString())).thenReturn(testCollection);
        assertIterableEquals(testCollection, facultyService.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(testString));
    }

    @Test
    void findFacultyStudents() {
        assertNull(facultyService.findFacultyStudents(TEST_FACULTY_1));
    }
}