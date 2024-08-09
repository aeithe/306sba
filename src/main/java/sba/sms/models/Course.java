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
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Getter
    @Column(name = "course_name", nullable = false, length = 50)
    private String name;

    @Getter
    @Column(name = "instructor_name", nullable = false, length = 50)
    private String instructor;

    @Column(name = "students")
    private Set<Student> students;


    @ManyToMany(mappedBy = "course")
    public Set<Student> getStudents() {
        return students;
    }

    public Course(String instructor, String name) {
        this.instructor = instructor;
        this.name = name;
    }
}
