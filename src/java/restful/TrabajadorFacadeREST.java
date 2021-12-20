/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.Trabajador;
import java.util.List;
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
 * @author Jonathan
 */
@Stateless
@Path("entidades.trabajador")
public class TrabajadorFacadeREST extends AbstractFacade<Trabajador> {

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    public TrabajadorFacadeREST() {
        super(Trabajador.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Trabajador entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Trabajador entity) {
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
    public Trabajador find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("buscarTodosLosTrabajadores")
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> BuscarTodosLosTrabajadores() {
        List<Trabajador> trabajadores = null;
        try {
            trabajadores = em.createNamedQuery("buscarTodosLosTrabajadores").getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return trabajadores;
    }

    @GET
    @Path("trabajadoresSinIncidenciasPorId")
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> trabajadoresSinIncidenciasPorId() {
        List<Trabajador> trabajadores = null;
        try {
            trabajadores = (List<Trabajador>) em.createNamedQuery("trabajadoresSinIncidenciasPorId").getResultList();
        } catch (Exception e) {
        }
        return trabajadores;
    }
}
