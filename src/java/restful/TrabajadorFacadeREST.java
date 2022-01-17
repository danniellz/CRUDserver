/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.Trabajador;
import excepciones.CreateException;
import excepciones.DeleteException;
import excepciones.ReadException;
import excepciones.UpdateException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Jonathan Viñan
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
        try {
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(TrabajadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Trabajador entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(TrabajadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException ex) {
            Logger.getLogger(TrabajadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteException ex) {
            Logger.getLogger(TrabajadorFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Trabajador find(@PathParam("id") Integer id) /*REVISAR*/ throws ReadException {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> findAll()/*REVISAR*/ throws ReadException {
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
