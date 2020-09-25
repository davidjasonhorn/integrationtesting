package isoft.testing.utest.resource;

import isoft.testing.utest.product.service.InventoryTransactionListTO;
import isoft.testing.utest.product.service.InventoryTransactionTO;
import isoft.testing.utest.product.service.ProductListTO;
import isoft.testing.utest.product.service.ProductService;
import isoft.testing.utest.product.service.ProductTO;
import isoft.testing.utest.product.validation.ValidationError;
import java.math.BigDecimal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Rest API for managing attachments.
 *
 * @author hornd
 */
@Path("product")
@RequestScoped
public class ProductResource {

    @Inject
    private ProductService productService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(ProductTO att) {
        try {
            productService.addProduct(att);
            return Response.status(Response.Status.CREATED).build();
        } catch (ValidationError be) {
            return Response.status(Response.Status.BAD_REQUEST).entity(be.getViolations()).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loadProductByProductId(@PathParam(value = "id") String productId) {

        ProductTO pr = productService.loadProductByProductId(productId);
        return Response.status(Response.Status.OK).entity(pr).build();

    }


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loadAllProducts() {
        ProductListTO pl = productService.loadAllProducts();
        return Response.status(Response.Status.OK).entity(pl).build();

    }

    @GET
    @Path("/salesprice")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSalePrice(@QueryParam(value = "productId") String productId, 
            @QueryParam(value = "quantity") Integer quantity,
            @QueryParam(value = "currency") String currency) {

        InventoryTransactionTO it = new InventoryTransactionTO(java.time.LocalDateTime.now(),quantity,productId);
        try {
            BigDecimal salsesprice = productService.getSalePrice(it, currency);
            return Response.status(Response.Status.OK).entity(salsesprice.toString()).build();
        } catch (ValidationError be) {
            return Response.status(Response.Status.BAD_REQUEST).entity(be.getViolations()).build();
        }
    }

    @GET
    @Path("/{id}/inv")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loadTransactionsForProductId(@PathParam(value = "id") String productId) { 
        InventoryTransactionListTO to = productService.loadTransactionsForProductId(productId);
        return Response.status(Response.Status.OK).entity(to).build();
    }
    
    @POST
    @Path("/trans")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response performTransaction(InventoryTransactionTO trans) { 
        try {
            productService.performTransaction(trans);
            return Response.status(Response.Status.CREATED).build();
        } catch (ValidationError be) {
            return Response.status(Response.Status.BAD_REQUEST).entity(be.getViolations()).build();
        }        
    }    
        
    

}
