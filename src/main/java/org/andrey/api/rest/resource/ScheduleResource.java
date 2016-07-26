package org.andrey.api.rest.resource;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.andrey.api.persistence.model.Clazz;
import org.andrey.api.persistence.model.Student;
import org.andrey.api.rest.Try;
import org.andrey.api.rest.bean.ClazzDto;
import org.andrey.api.rest.bean.StudentClazzDto;
import org.andrey.api.rest.bean.StudentDto;
import org.andrey.api.service.StudentClazzDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.Callable;

@Named
@Path("/schedule")
public class ScheduleResource extends BaseResource {

    @Autowired
    private StudentClazzDao studentClazzDao;


    @POST
    @Path("/student/{studentId}/class/{classId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addSchedule(@PathParam("studentId") final Long studentId, @PathParam("classId") final Long clazzCode) {

        Try aTry = Try.wrap(new Callable<Long>() {

            @Override
            public Long call() throws Exception {

                StudentClazzDto studentClazzDto = new StudentClazzDto();
                studentClazzDto.setClazzCode(clazzCode);
                studentClazzDto.setStudentId(studentId);

                return studentClazzDao.create(studentClazzDto);
            }
        });

        return tryToResponse(aTry).build();
    }

    @GET
    @Path("/student/{studentId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClassesForStudent(@PathParam("studentId") final Long studentId) {

        Try aTry = Try.wrap(new Callable<List<ClazzDto>>() {

            @Override
            public List<ClazzDto> call() throws Exception {

                List<Clazz> classes = studentClazzDao.getClasses(studentId);
                List<ClazzDto> classesToReturn = Lists.newArrayList((Iterables.transform(classes, new Function<Clazz, ClazzDto>() {
                    @Override
                    public ClazzDto apply(Clazz clazz) {
                        return new ClazzDto(clazz);
                    }
                })));

                return classesToReturn;
            }
        });

        return tryToResponse(aTry).build();
    }

    @GET
    @Path("/class/{clazzCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStudentsForClass(@PathParam("clazzCode") final Long clazzCode) {

        Try aTry = Try.wrap(new Callable<List<StudentDto>>() {

            @Override
            public List<StudentDto> call() throws Exception {

                List<Student> students = studentClazzDao.getStudents(clazzCode);
                List<StudentDto> studentsToReturn = Lists.newArrayList((Iterables.transform(students, new Function<Student, StudentDto>() {
                    @Override
                    public StudentDto apply(Student student) {
                        return new StudentDto(student);
                    }
                })));

                return studentsToReturn;
            }
        });

        return tryToResponse(aTry).build();
    }

    @DELETE
    @Path("/student/{studentId}/class/{classId}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteSchedule(@PathParam("studentId") final Long studentId, @PathParam("classId") final Long clazzCode) {

        Try aTry = Try.wrap(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                studentClazzDao.remove(studentId, clazzCode);
                return Boolean.TRUE;
            }
        });

        return tryToResponse(aTry).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response getAllSchedules() {

        Try aTry = Try.wrap(new Callable<List<StudentClazzDto>>() {

            @Override
            public List<StudentClazzDto> call() throws Exception {

                return studentClazzDao.getAll();
            }
        });

        return tryToResponse(aTry).build();

    }
}
