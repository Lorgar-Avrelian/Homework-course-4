package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.constants.Constant.*;

class FacultyServiceImplTest {
    FacultyService facultyService = new FacultyServiceImpl();

    @Test
    void createFaculty() {
        assertEquals(TEST_FACULTY_1, facultyService.createFaculty(TEST_FACULTY_1));
        assertEquals(TEST_FACULTY_2, facultyService.createFaculty(TEST_FACULTY_2));
        assertEquals(TEST_FACULTY_3, facultyService.createFaculty(TEST_FACULTY_3));
        assertEquals(TEST_FACULTY_4, facultyService.createFaculty(TEST_FACULTY_4));
    }

    @Test
    void findFaculty() {
        editTESTFaculties();
        assertEquals(TEST_FACULTY_1, facultyService.findFaculty(1L));
        assertEquals(TEST_FACULTY_2, facultyService.findFaculty(2L));
        assertEquals(TEST_FACULTY_3, facultyService.findFaculty(3L));
        assertEquals(TEST_FACULTY_4, facultyService.findFaculty(4L));
        assertNull(facultyService.findFaculty(5L));
    }

    @Test
    void editFaculty() {
        editTESTFaculties();
        Faculty testEdit = new Faculty(1L, "TEST", "test");
        facultyService.editFaculty(testEdit);
        assertEquals(testEdit, facultyService.findFaculty(1L));
        editTESTFaculties();
    }

    @Test
    void deleteFaculty() {
        editTESTFaculties();
        facultyService.createFaculty(TEST_FACULTY_1);
        facultyService.deleteFaculty(1L);
        Faculty actualFaculty = facultyService.findFaculty(1L);
        assertNull(actualFaculty);
        editTESTFaculties();
    }

    @Test
    void getAll() {
        editTESTFaculties();
        Collection<Faculty> actualCollection = facultyService.getAll();
        assertIterableEquals(TEST_FACULTY_COLLECTION, actualCollection);
    }

    @Test
    void findByColor() {
        editTESTFaculties();
        Collection<Faculty> actualCollection = facultyService.findByColor(TEST_FACULTY_2.getColor());
        Collection<Faculty> expectedCollection = new ArrayList<>(List.of(TEST_FACULTY_2));
        assertIterableEquals(expectedCollection, actualCollection);
    }

    public void editTESTFaculties() {
        facultyService.editFaculty(TEST_FACULTY_1);
        facultyService.editFaculty(TEST_FACULTY_2);
        facultyService.editFaculty(TEST_FACULTY_3);
        facultyService.editFaculty(TEST_FACULTY_4);
    }
}