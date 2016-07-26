package org.andrey.api.service;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.andrey.api.exception.ApiNotFoundException;
import org.andrey.api.persistence.model.Clazz;
import org.andrey.api.persistence.model.Student;
import org.andrey.api.persistence.model.StudentClazz;
import org.andrey.api.rest.bean.StudentClazzDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class StudentClazzDao extends EntityDao<StudentClazzDto, StudentClazz> {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private StudentDao studentDao;

    @Inject
    private ClazzDao clazzDao;


    public StudentClazzDao() {
    }

    public Long create(StudentClazzDto studentClazzDto) {

        StudentClazz studentClazz = new StudentClazz();
        studentClazz.setClazzCode(studentClazzDto.getClazzCode());
        studentClazz.setStudentId(studentClazzDto.getStudentId());
        StudentClazz existingSchedule = get(studentClazzDto.getStudentId(), studentClazzDto.getClazzCode());
        if (existingSchedule == null){

            //todo these will throw ApiNotFoundException if the resources (student or class) do not exist
            studentDao.get(studentClazzDto.getStudentId());
            clazzDao.get(studentClazzDto.getClazzCode());

            entityManager.persist(studentClazz);
        } else {

            studentClazz = existingSchedule;
        }
        return studentClazz.getId();
    }

    public Long update(StudentClazzDto studentClazzDto) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Long id) {

        StudentClazz studentClazz = get(id);
        entityManager.remove(studentClazz);
    }

    public void remove(Long studentId, Long clazzCode) {

        StudentClazz studentClazz = get(studentId, clazzCode);
        if (studentClazz != null){

            entityManager.remove(studentClazz);
        }
    }


    public List<StudentClazzDto> getAll() {

        List<StudentClazz> persistedSchedules = entityManager.createQuery("from StudentClazz").getResultList();

        Iterable transform = Iterables.transform(persistedSchedules, new Function<StudentClazz, StudentClazzDto>() {
            @Override
            public StudentClazzDto apply(StudentClazz studentClazz) {

                return new StudentClazzDto(studentClazz);
            }
        });
        List<StudentClazzDto> studentsToReturn = Lists.<StudentClazzDto>newArrayList(transform);

        return studentsToReturn;
    }

    @Override
    public StudentClazz get(Long id) {

        Query query = entityManager.createQuery("from StudentClazz where id = :id").setParameter("id", id);
        StudentClazz studentClazz = (StudentClazz) query.getSingleResult();
        if (studentClazz == null) {

            throw new ApiNotFoundException("Schedule with id " + id + " does not exist");
        }
        return studentClazz;
    }

    public StudentClazz get(Long studentId, Long clazzCode) {

        Query query = entityManager.createQuery("from StudentClazz where studentId = :studentId and clazzCode = :clazzCode");
        query.setParameter("studentId", studentId);
        query.setParameter("clazzCode", clazzCode);
        List<StudentClazz> studentSchedule = query.getResultList();
        StudentClazz studentClazz = studentSchedule.isEmpty() ? null : studentSchedule.get(0);
        return studentClazz;
    }

    public List<Clazz> getClasses(Long studentId) {

        Query query = entityManager.createNativeQuery("SELECT * from class c JOIN student_class sc ON (c.code = sc.clazzCode) WHERE sc.studentId = :studentId", Clazz.class);
        query.setParameter("studentId", studentId);
        List<Clazz> classes = query.getResultList();
        return classes;
    }

    public List<Student> getStudents(Long clazzCode) {

        Query query = entityManager.createNativeQuery("SELECT * from student s JOIN student_class sc ON (s.id = sc.studentId) WHERE sc.clazzCode = :clazzCode", Student.class);
        query.setParameter("clazzCode", clazzCode);
        List<Student> students = query.getResultList();
        return students;
    }
}
