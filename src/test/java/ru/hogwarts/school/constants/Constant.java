package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Constant {
    public static final Student TEST_STUDENT_1 = new Student(1L, "Harry", 14);
    public static final Student TEST_STUDENT_2 = new Student(2L, "Hermione", 14);
    public static final Student TEST_STUDENT_3 = new Student(3L, "Ron", 15);
    public static final Student TEST_STUDENT_4 = new Student(4L, "Ginny", 12);
    public static final Student TEST_STUDENT_5 = new Student(5L, "Neville", 15);
    public static final Collection<Student> TEST_STUDENT_COLLECTION = new ArrayList<>(List.of(TEST_STUDENT_1,
            TEST_STUDENT_2,
            TEST_STUDENT_3,
            TEST_STUDENT_4,
            TEST_STUDENT_5));
    public static final Faculty TEST_FACULTY_1 = new Faculty(1L, "Gryffindor", "scarlet and gold");
    public static final Faculty TEST_FACULTY_2 = new Faculty(2L, "Hufflepuff", "yellow and black");
    public static final Faculty TEST_FACULTY_3 = new Faculty(3L, "Ravenclaw", "blue and bronze");
    public static final Faculty TEST_FACULTY_4 = new Faculty(4L, "Slytherin", "green and silver");
    public static final Collection<Faculty> TEST_FACULTY_COLLECTION = new ArrayList<>(List.of(TEST_FACULTY_1,
            TEST_FACULTY_2,
            TEST_FACULTY_3,
            TEST_FACULTY_4));
}
