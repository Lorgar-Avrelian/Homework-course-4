package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static ru.hogwarts.school.constants.Constant.TEST_FACULTY_1;
import static ru.hogwarts.school.constants.Constant.TEST_STUDENT_1;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
        assertThat(facultyController).isNotNull();
    }

    @Test
    void getAllTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotEmpty();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotEmpty();
    }

    @Test
    void getByIdTest() throws Exception {
        Long testId = 1L;
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/" + testId, String.class))
                .isNotNull();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/" + testId, String.class))
                .isNotNull();
    }

    @Test
    void createTest() throws Exception {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", TEST_STUDENT_1, String.class))
                .contains(TEST_STUDENT_1.getName());
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", TEST_FACULTY_1, String.class))
                .contains(TEST_FACULTY_1.getName());
    }

    @Test
    void editTest() throws Exception {
        this.restTemplate.put("http://localhost:" + port + "/student", TEST_STUDENT_1, Student.class);
        Long testId = findStudent(TEST_STUDENT_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/" + testId, String.class)).contains(TEST_STUDENT_1.getName());
        this.restTemplate.put("http://localhost:" + port + "/faculty", TEST_FACULTY_1, String.class);
        testId = findFaculty(TEST_FACULTY_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/" + testId, String.class)).contains(TEST_FACULTY_1.getName());
    }

    @Test
    void deleteTest() throws Exception {
        studentController.createStudent(TEST_STUDENT_1);
        Long studentTestId = findStudent(TEST_STUDENT_1);
        this.restTemplate.delete("http://localhost:" + port + "/student" + "/" + studentTestId, String.class);
        assertThrows(Exception.class, () -> studentController.getStudent(studentTestId));
        facultyController.createFaculty(TEST_FACULTY_1);
        Long facultyTestId = findFaculty(TEST_FACULTY_1);
        this.restTemplate.delete("http://localhost:" + port + "/faculty" + "/" + facultyTestId, String.class);
        assertThrows(Exception.class, () -> facultyController.getFaculty(facultyTestId));
    }

    @Test
    void findStudentByAgeTest() throws Exception {
        studentController.createStudent(TEST_STUDENT_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/age/" + TEST_STUDENT_1.getAge(), String.class))
                .contains(TEST_STUDENT_1.getName());
    }

    @Test
    void findFacultyByColor() throws Exception {
        facultyController.createFaculty(TEST_FACULTY_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/color/" + TEST_FACULTY_1.getColor(), String.class))
                .contains(TEST_FACULTY_1.getName());
    }

    @Test
    void findStudentsByAgeBetweenTest() throws Exception {
        studentController.createStudent(TEST_STUDENT_1);
        Long minTestAge = TEST_STUDENT_1.getAge() - 1L;
        Long maxTestAge = TEST_STUDENT_1.getAge() + 1L;
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/age?" + "minAge=" + minTestAge + "&" + "maxAge=" + maxTestAge, String.class))
                .contains(TEST_STUDENT_1.getName());
    }

    @Test
    void findFacultyByColorOrNameTest() throws Exception {
        facultyController.createFaculty(TEST_FACULTY_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/find?" + "string=" + TEST_FACULTY_1.getColor(), String.class))
                .contains(TEST_FACULTY_1.getName());
    }

    private Long findStudent(Student student) {
        return studentController.getAll().stream()
                .filter(e -> e.getName().equals(student.getName()))
                .collect(Collectors.toList())
                .stream()
                .filter(e -> e.getAge() == student.getAge())
                .collect(Collectors.toList())
                .stream().findFirst()
                .get()
                .getId();
    }

    private Long findFaculty(Faculty faculty) {
        return facultyController.getAll().stream()
                .filter(e -> e.getName().equals(faculty.getName()))
                .collect(Collectors.toList())
                .stream()
                .filter(e -> e.getColor().equals(faculty.getColor()))
                .collect(Collectors.toList())
                .stream().findFirst()
                .get()
                .getId();
    }
}
