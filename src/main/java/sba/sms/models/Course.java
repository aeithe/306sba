package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table (name = "course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courses_id")
    private int course_id;

    @Column(name = "course_name", nullable = false, length = 50)
    private String courseName;

    @Column(name = "instructor_name", nullable = false, length = 50)
    private String instructor_name;

    @Column(name = "students")
    private Set<Student> students;

    @ManyToMany(mappedBy = "course")
    public Set<Student> getStudents() {
        return students;
    }
}
