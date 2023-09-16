package ru.hogwarts.school.constants;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Constant {
    public static final Student TEST_STUDENT_1 = new Student(101L, "Harry", 14);
    public static final Student TEST_STUDENT_2 = new Student(102L, "Hermione", 14);
    public static final Student TEST_STUDENT_3 = new Student(103L, "Ron", 15);
    public static final Student TEST_STUDENT_4 = new Student(104L, "Ginny", 12);
    public static final Student TEST_STUDENT_5 = new Student(105L, "Neville", 15);
    public static final List<Student> TEST_STUDENT_COLLECTION = new ArrayList<>(List.of(TEST_STUDENT_1,
            TEST_STUDENT_2,
            TEST_STUDENT_3,
            TEST_STUDENT_4,
            TEST_STUDENT_5));
    public static final Faculty TEST_FACULTY_1 = new Faculty(101L, "Gryffindor", "scarlet and gold");
    public static final Faculty TEST_FACULTY_2 = new Faculty(102L, "Hufflepuff", "yellow and black");
    public static final Faculty TEST_FACULTY_3 = new Faculty(103L, "Ravenclaw", "blue and bronze");
    public static final Faculty TEST_FACULTY_4 = new Faculty(104L, "Slytherin", "green and silver");
    public static final List<Faculty> TEST_FACULTY_COLLECTION = new ArrayList<>(List.of(TEST_FACULTY_1,
            TEST_FACULTY_2,
            TEST_FACULTY_3,
            TEST_FACULTY_4));
}
