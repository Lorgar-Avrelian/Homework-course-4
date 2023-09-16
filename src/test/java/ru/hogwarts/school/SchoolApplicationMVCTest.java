package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.constants.Constant.*;

@WebMvcTest
public class SchoolApplicationMVCTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentServiceImpl studentService;
    @SpyBean
    private AvatarServiceImpl avatarService;
    @SpyBean
    private FacultyServiceImpl facultyService;
    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private FacultyController facultyController;
    @InjectMocks
    private AvatarController avatarController;

    @BeforeEach
    void initTest() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(TEST_STUDENT_1);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(TEST_FACULTY_1);

        when(studentRepository.findAll()).thenReturn(TEST_STUDENT_COLLECTION);
        when(facultyRepository.findAll()).thenReturn(TEST_FACULTY_COLLECTION);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(TEST_STUDENT_1));
        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(TEST_FACULTY_1));

        when(studentRepository.findByAge(anyInt())).thenReturn(TEST_STUDENT_COLLECTION);
        when(facultyRepository.findByColor(anyString())).thenReturn(TEST_FACULTY_COLLECTION);

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(TEST_STUDENT_COLLECTION);
        when(facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(anyString(), anyString())).thenReturn(TEST_FACULTY_COLLECTION);
    }

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TEST_STUDENT_1.getId()))
                .andExpect(jsonPath("$[0].name").value(TEST_STUDENT_1.getName()))
                .andExpect(jsonPath("$[0].age").value(TEST_STUDENT_1.getAge()))
                .andExpect(jsonPath("$[1].id").value(TEST_STUDENT_2.getId()))
                .andExpect(jsonPath("$[1].name").value(TEST_STUDENT_2.getName()))
                .andExpect(jsonPath("$[1].age").value(TEST_STUDENT_2.getAge()))
                .andExpect(jsonPath("$[2].id").value(TEST_STUDENT_3.getId()))
                .andExpect(jsonPath("$[2].name").value(TEST_STUDENT_3.getName()))
                .andExpect(jsonPath("$[2].age").value(TEST_STUDENT_3.getAge()))
                .andExpect(jsonPath("$[3].id").value(TEST_STUDENT_4.getId()))
                .andExpect(jsonPath("$[3].name").value(TEST_STUDENT_4.getName()))
                .andExpect(jsonPath("$[3].age").value(TEST_STUDENT_4.getAge()))
                .andExpect(jsonPath("$[4].id").value(TEST_STUDENT_5.getId()))
                .andExpect(jsonPath("$[4].name").value(TEST_STUDENT_5.getName()))
                .andExpect(jsonPath("$[4].age").value(TEST_STUDENT_5.getAge()));
        mockMvc.perform(get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TEST_FACULTY_1.getId()))
                .andExpect(jsonPath("$[0].name").value(TEST_FACULTY_1.getName()))
                .andExpect(jsonPath("$[0].color").value(TEST_FACULTY_1.getColor()))
                .andExpect(jsonPath("$[1].id").value(TEST_FACULTY_2.getId()))
                .andExpect(jsonPath("$[1].name").value(TEST_FACULTY_2.getName()))
                .andExpect(jsonPath("$[1].color").value(TEST_FACULTY_2.getColor()))
                .andExpect(jsonPath("$[2].id").value(TEST_FACULTY_3.getId()))
                .andExpect(jsonPath("$[2].name").value(TEST_FACULTY_3.getName()))
                .andExpect(jsonPath("$[2].color").value(TEST_FACULTY_3.getColor()))
                .andExpect(jsonPath("$[3].id").value(TEST_FACULTY_4.getId()))
                .andExpect(jsonPath("$[3].name").value(TEST_FACULTY_4.getName()))
                .andExpect(jsonPath("$[3].color").value(TEST_FACULTY_4.getColor()));
    }

    @Test
    void getByIdTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        JSONObject facultyObject = new JSONObject();

        studentObject.put("id", TEST_STUDENT_1.getId());
        studentObject.put("name", TEST_STUDENT_1.getName());
        studentObject.put("age", TEST_STUDENT_1.getAge());

        facultyObject.put("id", TEST_FACULTY_1.getId());
        facultyObject.put("name", TEST_FACULTY_1.getName());
        facultyObject.put("color", TEST_FACULTY_1.getColor());

        mockMvc.perform(get("/student" + "/" + TEST_STUDENT_1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_STUDENT_1.getId()))
                .andExpect(jsonPath("$.name").value(TEST_STUDENT_1.getName()))
                .andExpect(jsonPath("$.age").value(TEST_STUDENT_1.getAge()));
        mockMvc.perform(get("/faculty" + "/" + TEST_FACULTY_1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_FACULTY_1.getId()))
                .andExpect(jsonPath("$.name").value(TEST_FACULTY_1.getName()))
                .andExpect(jsonPath("$.color").value(TEST_FACULTY_1.getColor()));
    }

    @Test
    void createTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        JSONObject facultyObject = new JSONObject();

        studentObject.put("id", TEST_STUDENT_1.getId());
        studentObject.put("name", TEST_STUDENT_1.getName());
        studentObject.put("age", TEST_STUDENT_1.getAge());

        facultyObject.put("id", TEST_FACULTY_1.getId());
        facultyObject.put("name", TEST_FACULTY_1.getName());
        facultyObject.put("color", TEST_FACULTY_1.getColor());

        mockMvc.perform(post("/student")
                .content(studentObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_STUDENT_1.getId()))
                .andExpect(jsonPath("$.name").value(TEST_STUDENT_1.getName()))
                .andExpect(jsonPath("$.age").value(TEST_STUDENT_1.getAge()));
        mockMvc.perform(post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_FACULTY_1.getId()))
                .andExpect(jsonPath("$.name").value(TEST_FACULTY_1.getName()))
                .andExpect(jsonPath("$.color").value(TEST_FACULTY_1.getColor()));
    }

    @Test
    void editTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        JSONObject facultyObject = new JSONObject();

        studentObject.put("id", TEST_STUDENT_1.getId());
        studentObject.put("name", TEST_STUDENT_1.getName());
        studentObject.put("age", TEST_STUDENT_1.getAge());

        facultyObject.put("id", TEST_FACULTY_1.getId());
        facultyObject.put("name", TEST_FACULTY_1.getName());
        facultyObject.put("color", TEST_FACULTY_1.getColor());

        mockMvc.perform(put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_STUDENT_1.getId()))
                .andExpect(jsonPath("$.name").value(TEST_STUDENT_1.getName()))
                .andExpect(jsonPath("$.age").value(TEST_STUDENT_1.getAge()));
        mockMvc.perform(put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_FACULTY_1.getId()))
                .andExpect(jsonPath("$.name").value(TEST_FACULTY_1.getName()))
                .andExpect(jsonPath("$.color").value(TEST_FACULTY_1.getColor()));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/student" + "/" + TEST_STUDENT_1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/faculty"+ "/" + TEST_FACULTY_1.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findStudentByAgeTest() throws Exception {
        JSONObject studentObject = new JSONObject();

        studentObject.put("id", TEST_STUDENT_1.getId());
        studentObject.put("name", TEST_STUDENT_1.getName());
        studentObject.put("age", TEST_STUDENT_1.getAge());

        mockMvc.perform(get("/student" + "/age/" + TEST_STUDENT_1.getAge())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TEST_STUDENT_1.getId()))
                .andExpect(jsonPath("$[0].name").value(TEST_STUDENT_1.getName()))
                .andExpect(jsonPath("$[0].age").value(TEST_STUDENT_1.getAge()))
                .andExpect(jsonPath("$[1].id").value(TEST_STUDENT_2.getId()))
                .andExpect(jsonPath("$[1].name").value(TEST_STUDENT_2.getName()))
                .andExpect(jsonPath("$[1].age").value(TEST_STUDENT_2.getAge()))
                .andExpect(jsonPath("$[2].id").value(TEST_STUDENT_3.getId()))
                .andExpect(jsonPath("$[2].name").value(TEST_STUDENT_3.getName()))
                .andExpect(jsonPath("$[2].age").value(TEST_STUDENT_3.getAge()))
                .andExpect(jsonPath("$[3].id").value(TEST_STUDENT_4.getId()))
                .andExpect(jsonPath("$[3].name").value(TEST_STUDENT_4.getName()))
                .andExpect(jsonPath("$[3].age").value(TEST_STUDENT_4.getAge()))
                .andExpect(jsonPath("$[4].id").value(TEST_STUDENT_5.getId()))
                .andExpect(jsonPath("$[4].name").value(TEST_STUDENT_5.getName()))
                .andExpect(jsonPath("$[4].age").value(TEST_STUDENT_5.getAge()));
    }

    @Test
    void findFacultyByColor() throws Exception {
        JSONObject facultyObject = new JSONObject();

        facultyObject.put("id", TEST_FACULTY_1.getId());
        facultyObject.put("name", TEST_FACULTY_1.getName());
        facultyObject.put("color", TEST_FACULTY_1.getColor());

        mockMvc.perform(get("/faculty" + "/color/" + TEST_FACULTY_1.getColor())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TEST_FACULTY_1.getId()))
                .andExpect(jsonPath("$[0].name").value(TEST_FACULTY_1.getName()))
                .andExpect(jsonPath("$[0].color").value(TEST_FACULTY_1.getColor()))
                .andExpect(jsonPath("$[1].id").value(TEST_FACULTY_2.getId()))
                .andExpect(jsonPath("$[1].name").value(TEST_FACULTY_2.getName()))
                .andExpect(jsonPath("$[1].color").value(TEST_FACULTY_2.getColor()))
                .andExpect(jsonPath("$[2].id").value(TEST_FACULTY_3.getId()))
                .andExpect(jsonPath("$[2].name").value(TEST_FACULTY_3.getName()))
                .andExpect(jsonPath("$[2].color").value(TEST_FACULTY_3.getColor()))
                .andExpect(jsonPath("$[3].id").value(TEST_FACULTY_4.getId()))
                .andExpect(jsonPath("$[3].name").value(TEST_FACULTY_4.getName()))
                .andExpect(jsonPath("$[3].color").value(TEST_FACULTY_4.getColor()));
    }

    @Test
    void findStudentsByAgeBetweenTest() throws Exception {
        JSONObject studentObject = new JSONObject();

        studentObject.put("id", TEST_STUDENT_1.getId());
        studentObject.put("name", TEST_STUDENT_1.getName());
        studentObject.put("age", TEST_STUDENT_1.getAge());

        int minAge = TEST_STUDENT_1.getAge() - 1;
        int maxAge = TEST_STUDENT_1.getAge() + 1;

        mockMvc.perform(get("/student" + "/age?" + "minAge=" + minAge + "&" + "maxAge=" + maxAge)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TEST_STUDENT_1.getId()))
                .andExpect(jsonPath("$[0].name").value(TEST_STUDENT_1.getName()))
                .andExpect(jsonPath("$[0].age").value(TEST_STUDENT_1.getAge()))
                .andExpect(jsonPath("$[1].id").value(TEST_STUDENT_2.getId()))
                .andExpect(jsonPath("$[1].name").value(TEST_STUDENT_2.getName()))
                .andExpect(jsonPath("$[1].age").value(TEST_STUDENT_2.getAge()))
                .andExpect(jsonPath("$[2].id").value(TEST_STUDENT_3.getId()))
                .andExpect(jsonPath("$[2].name").value(TEST_STUDENT_3.getName()))
                .andExpect(jsonPath("$[2].age").value(TEST_STUDENT_3.getAge()))
                .andExpect(jsonPath("$[3].id").value(TEST_STUDENT_4.getId()))
                .andExpect(jsonPath("$[3].name").value(TEST_STUDENT_4.getName()))
                .andExpect(jsonPath("$[3].age").value(TEST_STUDENT_4.getAge()))
                .andExpect(jsonPath("$[4].id").value(TEST_STUDENT_5.getId()))
                .andExpect(jsonPath("$[4].name").value(TEST_STUDENT_5.getName()))
                .andExpect(jsonPath("$[4].age").value(TEST_STUDENT_5.getAge()));
    }

    @Test
    void findFacultyByColorOrNameTest() throws Exception {
        JSONObject facultyObject = new JSONObject();

        facultyObject.put("id", TEST_FACULTY_1.getId());
        facultyObject.put("name", TEST_FACULTY_1.getName());
        facultyObject.put("color", TEST_FACULTY_1.getColor());

        mockMvc.perform(get("/faculty" + "/find?" + "string=" + TEST_FACULTY_1.getColor())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(TEST_FACULTY_1.getId()))
                .andExpect(jsonPath("$[0].name").value(TEST_FACULTY_1.getName()))
                .andExpect(jsonPath("$[0].color").value(TEST_FACULTY_1.getColor()))
                .andExpect(jsonPath("$[1].id").value(TEST_FACULTY_2.getId()))
                .andExpect(jsonPath("$[1].name").value(TEST_FACULTY_2.getName()))
                .andExpect(jsonPath("$[1].color").value(TEST_FACULTY_2.getColor()))
                .andExpect(jsonPath("$[2].id").value(TEST_FACULTY_3.getId()))
                .andExpect(jsonPath("$[2].name").value(TEST_FACULTY_3.getName()))
                .andExpect(jsonPath("$[2].color").value(TEST_FACULTY_3.getColor()))
                .andExpect(jsonPath("$[3].id").value(TEST_FACULTY_4.getId()))
                .andExpect(jsonPath("$[3].name").value(TEST_FACULTY_4.getName()))
                .andExpect(jsonPath("$[3].color").value(TEST_FACULTY_4.getColor()));
    }
}
