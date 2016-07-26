package org.andrey.api.rest.resource;

import org.andrey.api.exception.*;
import org.andrey.api.rest.bean.StudentDto;
import org.andrey.api.rest.Try;
import org.andrey.api.service.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Named
@Path("/student")
public class StudentResource extends BaseResource {

    @Autowired
    private StudentDao studentDao;


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Map<String, String> postData) {

        Try aTry = Try.wrap(new Callable<Long>() {

            @Override
            public Long call() throws Exception {

                StudentDto studentDto = new StudentDto();
                //// TODO: get rid of magic strings
                String firstName = postData.get("firstName");
                studentDto.setFirstName(firstName);
                String lastName = postData.get("lastName");
                studentDto.setLastName(lastName);
                return studentDao.create(studentDto);
            }
        });

        return tryToResponse(aTry).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editStudent(@PathParam("id") final Long studentId, Map<String, String> postData) {

        Try aTry = Try.wrap(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                Long idOfEditedStudent = null;

                StudentDto studentDto = new StudentDto();
                studentDto.setId(studentId);
                if (postData == null){

                    throw new ApiInvalidCallException("The request body is empty, there is nothing to update!");
                }
                String firstName = postData.get("firstName");
                studentDto.setFirstName(firstName);
                String lastName = postData.get("lastName");
                studentDto.setLastName(lastName);

                idOfEditedStudent = studentDao.update(studentDto);

                return idOfEditedStudent != null;

            }
        });

        return tryToResponse(aTry).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteStudent(@PathParam("id") final Long studentId) {

        Try aTry = Try.wrap(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                studentDao.remove(studentId);
                return Boolean.TRUE;
            }
        });

        return tryToResponse(aTry).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response getAllStudents() {

        Try aTry = Try.wrap(new Callable<List<StudentDto>>() {

            @Override
            public List<StudentDto> call() throws Exception {

                return studentDao.getAll();
            }
        });

        return tryToResponse(aTry).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{searchString}")
    public Response searchByString(@PathParam("searchString") final String searchString) {

        Try aTry = Try.wrap(new Callable<List<StudentDto>>() {

            @Override
            public List<StudentDto> call() throws Exception {

                return studentDao.searchByString(searchString);
            }
        });

        return tryToResponse(aTry).build();
    }
}
