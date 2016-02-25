package eu.europeana.redirects.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.europeana.redirects.model.RedirectRequest;
import eu.europeana.redirects.model.RedirectRequestList;
import eu.europeana.redirects.model.RedirectResponse;
import eu.europeana.redirects.model.RedirectResponseList;
import eu.europeana.redirects.service.RedirectService;
import eu.europeana.redirects.service.mongo.MongoRedirectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
@Api("/")
public class RedirectResource {
    @Inject private  RedirectService redirectService;



    @POST
    @Path("/redirect/single")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Generate a single redirect",response = RedirectResponse.class)
    public Response redirectSingle(@ApiParam("record") @FormParam("record")String request){
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
    @ApiOperation(value="Generate batch redirects",response = RedirectResponseList.class)
    public Response redirectBatch(@ApiParam("records") @FormParam("records")RedirectRequestList requestList){
        return Response.ok().entity(redirectService.createRedirects(requestList)).build();
    }

}
