package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@Entity
@Table (name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Set<Student> students = new HashSet<>();


    public Course(String name, String instructor) {
        this.instructor = instructor;
        this.name = name;
    }

    public Course(String name, String instructor, HashSet<Student> students) {
        this.name = name;
        this.instructor = instructor;
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor);
    }
}
