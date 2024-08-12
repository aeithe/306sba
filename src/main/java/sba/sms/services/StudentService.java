package sba.sms.services;

import jakarta.persistence.TypedQuery;
import lombok.extern.java.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;
import java.util.ArrayList;
import java.util.List;


public class StudentService implements StudentI{
    SessionFactory factory =  new Configuration().configure().buildSessionFactory();;


    @Override
    public List<Student> getAllStudents(){
        Session session = factory.openSession();
        Transaction transaction =null;
        try {
            transaction = session.beginTransaction();

            String hql = "FROM student";
            TypedQuery<Student> query = session.createNamedQuery(hql, Student.class);
            List<Student> students = query.getResultList();
            transaction.commit();
            return students;
        }
        catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public void createStudent(Student student){
        Session session = factory.openSession();
        Transaction transaction =null;
        try{
            transaction = session.beginTransaction();

            session.persist(student);
            transaction.commit();
        }catch (Exception e){
            if(transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    @Override
    public Student getStudentByEmail(String email){
        Session session = factory.openSession();
        Transaction transaction =null;
        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, email);
            transaction.commit();
            return student;
        }catch (Exception e){
            if (transaction !=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;

    }

    @Override
    public boolean validateStudent(String email, String password){
        Session session = factory.openSession();
        Transaction transaction =null;
        try{
            transaction = session.beginTransaction();

            String hql = "FROM student s WHERE s.email = :email AND s" + "s.password = :password";
            TypedQuery<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            Student student = query.getSingleResult();

            transaction.commit();
            return student != null;
        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            factory.close();
        }
        return false;
    }

    @Override
    public void registerStudentToCourse(String email, int courseId){
        Session session = factory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            Student student = getStudentByEmail(email);
            CourseService service = new CourseService();
            Course course = service.getCourseById(courseId);

            student.getCourses().add(course);
            session.merge(student);
            transaction.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();

        }
    }
    @Override
    public List<Course> getStudentCourses(String email){
        try{
            Student student = getStudentByEmail(email);
            List<Course> course = new ArrayList<>();
            course.addAll(student.getCourses());
            return course;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
