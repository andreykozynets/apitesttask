package org.andrey.api.rest.resource;

import org.andrey.api.rest.bean.ClazzDto;
import org.andrey.api.rest.Try;
import org.andrey.api.service.ClazzDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Named
@Path("/class")
public class ClazzResource extends BaseResource{

    @Autowired
    private ClazzDao clazzDao;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(Map<String, String> postData) {

        Try aTry = Try.wrap(new Callable<Long>() {

            @Override
            public Long call() throws Exception {

                ClazzDto clazzDto = new ClazzDto();
                //// TODO: get rid of magic strings
                String code = postData.get("title");
                clazzDto.setTitle(code);
                String description = postData.get("description");
                clazzDto.setDescription(description);
                return clazzDao.create(clazzDto);
            }
        });

        return tryToResponse(aTry).build();
    }

    @PUT
    @Path("/{code}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response editClazz(@PathParam("code") final Long clazzCode, Map<String, String> postData) {

        Try aTry = Try.wrap(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                Long idOfEditedClazz = null;

                ClazzDto clazzDto = new ClazzDto();
                clazzDto.setCode(clazzCode);
                String title = postData.get("title");
                clazzDto.setTitle(title);
                String description = postData.get("description");
                clazzDto.setDescription(description);

                idOfEditedClazz = clazzDao.update(clazzDto);

                return idOfEditedClazz != null;

            }
        });

        return tryToResponse(aTry).build();
    }

    @DELETE
    @Path("/{code}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteClazz(@PathParam("code") final Long clazzCode) {

        Try aTry = Try.wrap(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                clazzDao.remove(clazzCode);
                return Boolean.TRUE;
            }
        });

        return tryToResponse(aTry).build();

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    public Response getAllClasses() {

        Try aTry = Try.wrap(new Callable<List<ClazzDto>>() {

            @Override
            public List<ClazzDto> call() throws Exception {

                return clazzDao.getAll();
            }
        });

        return tryToResponse(aTry).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("search/{searchString}")
    public Response searchByString(@PathParam("searchString") final String searchString) {

        Try aTry = Try.wrap(new Callable<List<ClazzDto>>() {

            @Override
            public List<ClazzDto> call() throws Exception {

                return clazzDao.searchByString(searchString);
            }
        });

        return tryToResponse(aTry).build();

    }

}
