/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import java.util.Collection;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import com.sun.jersey.api.core.ResourceContext;
import converter.NetworksConverter;
import converter.NetworkConverter;
import persistence.Network;

/**
 *
 * @author dave
 */

@Path("/networks/")
public class NetworksResource extends Base {
    @Context
    protected UriInfo uriInfo;
    @Context
    protected ResourceContext resourceContext;
  
    /** Creates a new instance of NetworksResource */
    public NetworksResource() {
    }

    /**
     * Get method for retrieving a collection of Network instance in XML format.
     *
     * @return an instance of NetworksConverter
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public NetworksConverter get(@QueryParam("start")
    @DefaultValue("0")
    int start, @QueryParam("max")
    @DefaultValue("10")
    int max, @QueryParam("expandLevel")
    @DefaultValue("1")
    int expandLevel, @QueryParam("query")
    @DefaultValue("SELECT e FROM Network e")
    String query) {
                return new NetworksConverter(getEntities(start, max, query), uriInfo.getAbsolutePath(), expandLevel);
    }

    /**
     * Post method for creating an instance of Network using XML as the input format.
     *
     * @param data an NetworkConverter entity that is deserialized from an XML stream
     * @return an instance of NetworkConverter
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response post(NetworkConverter data) {
            Network entity = data.getEntity();
            createEntity(entity);
            return Response.created(uriInfo.getAbsolutePath().resolve(entity.getId() + "/")).build();
    }

    /**
     * Returns a dynamic instance of NetworkResource used for entity navigation.
     *
     * @return an instance of NetworkResource
     */
    @Path("{id}/")
    public service.NetworkResource getNetworkResource(@PathParam("id")
    String id) {
        NetworkResource resource = resourceContext.getResource(NetworkResource.class);
        resource.setId(id);
        return resource;
    }

    /**
     * Returns all the entities associated with this resource.
     *
     * @return a collection of Network instances
     */
    @Override
    protected Collection<Network> getEntities(int start, int max, String query) {
        return (Collection<Network>) super.getEntities(start, max, query);
    }
}
