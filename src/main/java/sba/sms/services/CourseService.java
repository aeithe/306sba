package sba.sms.services;

import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements CourseI{
    SessionFactory factory = null;
    Session session = null;
    Transaction transaction = null;

    @Override
    public void createCourse(Course course){
        try {factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            factory.close();
            session.close();
        }
    }
    @Override
    public Course getCourseById(int courseId){
        Course course = null;

        try {
            factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
            transaction = session.beginTransaction();
            course = session.get(Course.class, (courseId));
            transaction.commit();

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            factory.close();
            session.close();
        }
        return course;
    }
    @Override
    public List<Course> getAllCourses(){

        try {factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "FROM course";
            TypedQuery<Course> query = session.createNamedQuery(hql, Course.class);
            List<Course> courses = query.getResultList();
            transaction.commit();
            return courses;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            factory.close();
            session.close();
        }
        return null;
    }

}
