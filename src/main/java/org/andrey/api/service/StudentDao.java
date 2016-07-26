package org.andrey.api.service;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.andrey.api.exception.ApiNotFoundException;
import org.andrey.api.persistence.model.Student;
import org.andrey.api.rest.bean.StudentDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class StudentDao extends EntityDao<StudentDto, Student> {

    @PersistenceContext
    private EntityManager entityManager;

    public StudentDao() {
    }

    public Long create(StudentDto studentDto) {

        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        entityManager.persist(student);
        return student.getId();
    }

    public Long update(StudentDto studentDto) {

        Student student = get(studentDto.getId());
        if (student != null) {

            if (studentDto.getFirstName() != null) {

                student.setFirstName(studentDto.getFirstName());
            }

            if (studentDto.getLastName() != null) {

                student.setLastName(studentDto.getLastName());
            }
            return student.getId();
        }

        return null;
    }

    @Override
    public void remove(Long id) {

        Student student = get(id);
        entityManager.remove(student);
    }

    @Override
    public List<StudentDto> getAll() {

        List<Student> persistedUsers = entityManager.createQuery("from Student").getResultList();

        Iterable transform = Iterables.transform(persistedUsers, new Function<Student, StudentDto>() {
            @Override
            public StudentDto apply(Student student) {

                return new StudentDto(student);
            }
        });
        List<StudentDto> studentsToReturn = Lists.<StudentDto>newArrayList(transform);

        return studentsToReturn;
    }

    @Override
    public Student get(Long id) {

        Query query = entityManager.createQuery("from Student where id = :id").setParameter("id", id);
        List<Student> students = (List<Student>) query.getResultList();
        if (students.isEmpty()) {

            throw new ApiNotFoundException("Student with id " + id + " does not exist");
        }
        return students.get(0);
    }


    public List<StudentDto> searchByString(String searchString) {

        String queryString = "from Student where (lastName like '%" + searchString + "%' OR firstName like '%" + searchString + "%')";
        Query query = entityManager.createQuery(queryString);
        List<Student> persistedUsers = query.getResultList();

        Iterable<StudentDto> transform = Iterables.transform(persistedUsers, new Function<Student, StudentDto>() {
            @Override
            public StudentDto apply(Student student) {

                return new StudentDto(student);
            }
        });
        List<StudentDto> studentsToReturn = Lists.<StudentDto>newArrayList(transform);

        return studentsToReturn;
    }
}
