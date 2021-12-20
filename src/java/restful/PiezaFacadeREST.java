/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.Pieza;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author JonY
 */
@Stateless
@Path("entidades.pieza")
public class PiezaFacadeREST extends AbstractFacade<Pieza> {

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    public PiezaFacadeREST() {
        super(Pieza.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Pieza entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Pieza entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Pieza find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("/findAllPiezaInStock")
    @Produces({MediaType.APPLICATION_XML})
    public List<Pieza> findAllPiezaInStock () {
        List<Pieza> piezas = null;
        try{
            piezas = em.createNamedQuery("findAllPiezaInStock").getResultList();
        }catch(Exception ex){
            System.out.println("error");
        }
        return piezas;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Pieza> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
