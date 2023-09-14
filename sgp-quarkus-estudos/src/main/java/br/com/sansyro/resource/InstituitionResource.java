package br.com.sansyro.resource;

import br.com.sansyro.entity.Instituition;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/instituitions")
public class InstituitionResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity(Instituition.listAll(Sort.by("name"))).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.ok().entity(Instituition.findById(id)).build();
    }

    @POST
    @Transactional
    public Response create(Instituition instituition) {
        instituition.persist();
        return Response.ok().entity(instituition).build();
    }

    @PUT
    @Transactional
    public Response update(Instituition instituition) {
        Instituition instituitionUpdate = Instituition.findById(instituition.getId());
        instituitionUpdate.setName(instituition.getName());
        instituitionUpdate.setAddress(instituition.getAddress());
        instituitionUpdate.setQuantity(instituition.getQuantity());
        instituitionUpdate.persist();
        return Response.ok().entity(instituitionUpdate).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        Instituition instituition = Instituition.findById(id);
        instituition.delete();
        return Response.ok().build();
    }

}
