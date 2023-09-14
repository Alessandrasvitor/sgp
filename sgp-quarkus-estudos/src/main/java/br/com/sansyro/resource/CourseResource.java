package br.com.sansyro.resource;

import br.com.sansyro.entity.Course;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/courses")
public class CourseResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity(Course.listAll(Sort.by("name"))).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.ok().entity(Course.findById(id)).build();
    }


    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        Course course = Course.findById(id);
        course.delete();
        return Response.ok().build();
    }

}
