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
import javax.ws.rs.InternalServerErrorException;
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

    /**
     * Atributo estático y constante que guarda los loggers de esta clase.
     */
    private static final Logger LOGGER = Logger.getLogger("restful.TrabajadorFacadeREST");

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
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(Trabajador entity) {
        try {
            LOGGER.info("TrabajadorFacadeREST: Editando trabajador");
            super.edit(entity);
        } catch (UpdateException ex) {
            LOGGER.severe(ex.getMessage());
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("BuscarUnTrabajador/{fullName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> buscarTrabajadorPorNombre(@PathParam("fullName") String fullName) throws ReadException {
        List<Trabajador> trabajador = null;
        try {
            LOGGER.info("TrabajadorFacadeREST: Buscando trabajador por el nombre");
            trabajador = em.createNamedQuery("buscarTrabajadorPorNombre").setParameter("fullName", fullName).getResultList();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException();
        }
        return trabajador;
    }

    @GET
    @Path("BuscarTodosLosTrabajadores")
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> buscarTodosLosTrabajadores() {
        List<Trabajador> trabajadores = null;
        try {
            trabajadores = em.createNamedQuery("buscarTodosLosTrabajadores").getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return trabajadores;
    }

    @GET
    @Path("BuscarTrabajadoresSinIncidencias")
    @Produces({MediaType.APPLICATION_XML})
    public List<Trabajador> buscarTrabajadoresSinIncidencias() {
        List<Trabajador> trabajadores = null;
        try {
            trabajadores = (List<Trabajador>) em.createNamedQuery("buscarTrabajadoresSinIncidencias").getResultList();
        } catch (Exception e) {
        }
        return trabajadores;
    }
}
