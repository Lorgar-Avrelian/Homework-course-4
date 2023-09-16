package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static ru.hogwarts.school.constants.Constant.*;

@SpringBootTest(classes = SchoolApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
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
        ResponseEntity<Collection> responseStudent = restTemplate.getForEntity("http://localhost:" + port + "/student", Collection.class);
        Collection<Student> studentBody = responseStudent.getBody();
        assertThat(studentBody).isNotEmpty();
        assertThat(responseStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Collection> responseFaculty = restTemplate.getForEntity("http://localhost:" + port + "/faculty", Collection.class);
        Collection<Faculty> facultyBody = responseFaculty.getBody();
        assertThat(facultyBody).isNotEmpty();
        assertThat(responseFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        clearDB();
    }

    @Test
    void getByIdTest() throws Exception {
        long testId = 1L;

        ResponseEntity<Student> responseStudent = restTemplate.getForEntity("http://localhost:" + port + "/student" + "/" + testId, Student.class);
        assertThat(responseStudent).isNotNull();
        assertThat(responseStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student studentBody = responseStudent.getBody();
        assertThat(studentBody).isNotNull();

        ResponseEntity<Faculty> responseFaculty = restTemplate.exchange("http://localhost:" + port + "/faculty" + "/" + testId, HttpMethod.GET, HttpEntity.EMPTY, Faculty.class);
        assertThat(responseFaculty).isNotNull();
        assertThat(responseFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty facultyBody = responseFaculty.getBody();
        assertThat(facultyBody).isNotNull();

        clearDB();
    }

    @Test
    void createTest() throws Exception {
        ResponseEntity<Student> responseStudent = restTemplate.postForEntity("http://localhost:" + port + "/student", TEST_STUDENT_1, Student.class);
        assertThat(responseStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student studentBody = responseStudent.getBody();
        assertThat(studentBody).isNotNull();
        assertThat(studentBody.getName()).contains(TEST_STUDENT_1.getName());
        assertThat(studentBody.getAge()).isEqualTo(TEST_STUDENT_1.getAge());

        Faculty facultyBody = restTemplate.postForObject("http://localhost:" + port + "/faculty", TEST_FACULTY_1, Faculty.class);
        assertThat(facultyBody).isNotNull();
        assertThat(facultyBody.getName()).isEqualTo(TEST_FACULTY_1.getName());
        assertThat(facultyBody.getColor()).isNotEmpty();

        clearDB();
    }

    @Test
    void editTest() throws Exception {
        this.restTemplate.put("http://localhost:" + port + "/student", TEST_STUDENT_1, Student.class);
        Long testId = findStudent(TEST_STUDENT_1);
        ResponseEntity<Student> responseStudent = restTemplate.getForEntity("http://localhost:" + port + "/student" + "/" + testId, Student.class);
        assertThat(responseStudent).isNotNull();
        assertThat(responseStudent.getStatusCodeValue()).isEqualTo(200);
        Student studentBody = responseStudent.getBody();
        assertThat(studentBody).isNotNull();
        assertThat(studentBody.getName()).isNotEmpty();
        assertThat(studentBody.getName()).isNotNull();
        assertThat(studentBody.getName()).isEqualTo(TEST_STUDENT_1.getName());
        assertThat(studentBody.getAge()).isNotNegative();
        assertThat(studentBody.getAge()).isInstanceOf(Integer.class);
        assertThat(studentBody.getAge()).isEqualTo(TEST_STUDENT_1.getAge());

        this.restTemplate.put("http://localhost:" + port + "/faculty", TEST_FACULTY_1, String.class);
        testId = findFaculty(TEST_FACULTY_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/" + testId, String.class)).contains(TEST_FACULTY_1.getName(), TEST_FACULTY_1.getColor());

        clearDB();
    }

    @Test
    void deleteTest() throws Exception {
        studentController.createStudent(TEST_STUDENT_1);
        Long studentTestId = findStudent(TEST_STUDENT_1);
        restTemplate.delete("http://localhost:" + port + "/student" + "/" + studentTestId, String.class);
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:" + port + "/student" + "/" + studentTestId, Student.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        facultyController.createFaculty(TEST_FACULTY_1);
        Long facultyTestId = findFaculty(TEST_FACULTY_1);
        restTemplate.delete("http://localhost:" + port + "/faculty" + "/" + facultyTestId, String.class);
        assertThrows(Exception.class, () -> facultyController.getFaculty(facultyTestId));

        clearDB();
    }

    @Test
    void findStudentByAgeTest() throws Exception {
        studentController.createStudent(TEST_STUDENT_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/age/" + TEST_STUDENT_1.getAge(), String.class))
                .contains(TEST_STUDENT_1.getName());
        clearDB();
    }

    @Test
    void findFacultyByColor() throws Exception {
        facultyController.createFaculty(TEST_FACULTY_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/color/" + TEST_FACULTY_1.getColor(), String.class))
                .contains(TEST_FACULTY_1.getName());
        clearDB();
    }

    @Test
    void findStudentsByAgeBetweenTest() throws Exception {
        studentController.createStudent(TEST_STUDENT_1);
        long minTestAge = TEST_STUDENT_1.getAge() - 1L;
        long maxTestAge = TEST_STUDENT_1.getAge() + 1L;
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student" + "/age?" + "minAge=" + minTestAge + "&" + "maxAge=" + maxTestAge, String.class))
                .contains(TEST_STUDENT_1.getName());
        clearDB();
    }

    @Test
    void findFacultyByColorOrNameTest() throws Exception {
        facultyController.createFaculty(TEST_FACULTY_1);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/find?" + "string=" + TEST_FACULTY_1.getColor(), String.class))
                .contains(TEST_FACULTY_1.getName());
        clearDB();
    }

    private Long findStudent(Student student) {
        return studentController.getAll().stream()
                .filter(e -> e.getName().equals(student.getName()))
                .toList()
                .stream()
                .filter(e -> e.getAge() == student.getAge())
                .toList()
                .stream().findFirst()
                .get()
                .getId();
    }

    private Long findFaculty(Faculty faculty) {
        return facultyController.getAll().stream()
                .filter(e -> e.getName().equals(faculty.getName()))
                .toList()
                .stream()
                .filter(e -> e.getColor().equals(faculty.getColor()))
                .toList()
                .stream().findFirst()
                .get()
                .getId();
    }

    private void clearDB() {
        Collection<Student> students = studentController.getAll();
        for (Student student : students) {
            if (student.getName().equals(TEST_STUDENT_1.getName()) && student.getAge() == TEST_STUDENT_1.getAge() ||
                    student.getName().equals(TEST_STUDENT_2.getName()) && student.getAge() == TEST_STUDENT_2.getAge() ||
                    student.getName().equals(TEST_STUDENT_3.getName()) && student.getAge() == TEST_STUDENT_3.getAge() ||
                    student.getName().equals(TEST_STUDENT_4.getName()) && student.getAge() == TEST_STUDENT_4.getAge() ||
                    student.getName().equals(TEST_STUDENT_5.getName()) && student.getAge() == TEST_STUDENT_5.getAge()
            ) {
                studentController.deleteStudent(student.getId());
            }
        }

        Collection<Faculty> faculties = facultyController.getAll();
        for (Faculty faculty : faculties) {
            if (faculty.getName().equals(TEST_FACULTY_1.getName()) && faculty.getColor().equals(TEST_FACULTY_1.getColor()) ||
                    faculty.getName().equals(TEST_FACULTY_2.getName()) && faculty.getColor().equals(TEST_FACULTY_2.getColor()) ||
                    faculty.getName().equals(TEST_FACULTY_3.getName()) && faculty.getColor().equals(TEST_FACULTY_3.getColor()) ||
                    faculty.getName().equals(TEST_FACULTY_4.getName()) && faculty.getColor().equals(TEST_FACULTY_4.getColor())
            ) {
                facultyController.deleteFaculty(faculty.getId());
            }
        }
    }
}
