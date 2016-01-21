package eu.europeana.redirects.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europeana.redirects.model.RedirectRequest;
import eu.europeana.redirects.model.RedirectRequestList;
import eu.europeana.redirects.service.RedirectService;
import eu.europeana.redirects.service.mongo.MongoRedirectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * REST Endpoint for Europeana Redirects Service module
 * Created by ymamakis on 1/15/16.
 */
@Path("/")
public class RedirectResource {
    @Inject private  RedirectService redirectService;



    @POST
    @Path("/redirect/single")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirectSingle(@FormParam("record")String request){
        try {
            return Response.ok().entity(redirectService.createRedirect(new ObjectMapper().readValue(request, RedirectRequest.class))).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @POST
    @Path("/redirect/batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirectBatch(@FormParam("records")RedirectRequestList requestList){
        return Response.ok().entity(redirectService.createRedirects(requestList)).build();
    }

}
