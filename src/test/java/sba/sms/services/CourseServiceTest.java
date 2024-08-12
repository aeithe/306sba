package sba.sms.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;
import sba.sms.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class CourseServiceTest {
    private static Session session;

    @BeforeAll
    static void initialize(){session = HibernateUtil.getSessionFactory().openSession();}

    @Test
    public void testCreateCourse(){
    session.beginTransaction();
    Course course = new Course("Webservice", "Mr.Guy");
    session.persist(course);
    session.getTransaction().commit();
    assertTrue("Create Course", course.getName().equals("Webservice"));
    }

    @Test
    public void testGetCourseById(){
        session.beginTransaction();
        Course course = new Course("Webservices", "Mr.Dude", new HashSet<>());
        session.persist(course);
        Course getCourse = session.get(Course.class, 1);
        System.out.println(getCourse);
        session.getTransaction().commit();
        assertTrue("success", getCourse.getName().equals("Webservices"));
    }
    @Test
    public void getAllCourses(){
        session.beginTransaction();
        Course course = new Course("Webservices", "Mr. Guy", new HashSet<>());
        session.persist(course);
        List<Course> courses = session.createQuery("FROM Course", Course.class).getResultList();
        session.getTransaction().commit();
        System.out.println(courses.size());
        assertTrue("success", courses.size() >=1);
    }

    @AfterAll
    public static void shutDown(){if (session != null)session.close();}


}
