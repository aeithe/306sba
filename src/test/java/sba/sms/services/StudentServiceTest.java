package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class StudentServiceTest {
private static Session session;

@BeforeAll
static void initialize(){session = HibernateUtil.getSessionFactory().openSession();}


@Test
    public void testCreateStudent() {
        session.beginTransaction();
        Student student = new Student("test@gmail.com", "test guy","password");
        session.persist(student);
        session.getTransaction().commit();
        assertTrue("test@gmail.com", student.getEmail().equals("test@gmail" + ".com"));
    }

    @Test
    public void testGetStudentEmail(){
        session.beginTransaction();
        Student student = session.get(Student.class, "test@gmail.com");
        session.getTransaction().commit();
        assertTrue("success", student.getEmail().equals("test@gmail.com"));

    }

    @Test
    public void testValidateStudent(){
    session.beginTransaction();
    Student student = session.get(Student.class, "test@gmail.com");
    session.getTransaction().commit();
    assertNotNull(student);
    assertTrue("success", student.getPassword().equals("password") && student.getEmail().equals("test@gmail.com"));
    }

    @Test
    public void testRegisterStudentToCourse(){
    session.beginTransaction();
    Student student = session.get(Student.class, "test@gmail.com");
    session.getTransaction().commit();
    assertTrue("success", !student.getCourses().contains("JAVA"));
    }

    @Test
    public void testGetAllStudents(){
    session.beginTransaction();
    List<Student> students = session.createQuery("FROM Student", Student.class).getResultList();
    session.getTransaction().commit();
    assertTrue("success", students.size() >=1);
    }

    @AfterAll
    public static void shutDown(){if(session != null) session.close();}

}