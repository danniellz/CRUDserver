/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.Cliente;
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
 * @author Mikel Matilla
 */
@Stateless
@Path("entidades.cliente")
public class ClienteFacadeREST extends AbstractFacade<Cliente> {

    private static final Logger LOG = Logger.getLogger("restful.ClienteFacadeREST");

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    public ClienteFacadeREST() {
        super(Cliente.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Cliente entity) {
        try {
            LOG.info("Creando Cliente");
            super.create(entity);
        } catch (CreateException e) {
            LOG.severe(e.getMessage());
            Logger.getLogger(ClienteFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Cliente entity) {
        try {
            LOG.info("Modificar Cliente");
            super.edit(entity);
        } catch (UpdateException e) {
            LOG.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws ReadException {
        try {
            LOG.info("Borrar Cliente");
            super.remove(super.find(id));
        } catch (ReadException | DeleteException e) {
            LOG.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Cliente find(@PathParam("id") Integer id) {
        try {
            LOG.info("Buscando Cliente");
            return super.find(id);
        } catch (ReadException e) {
            LOG.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Cliente> findAll() {

        try {
            return super.findAll();
        } catch (ReadException e) {
            LOG.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("findAllClienteWithIncidencia")
    @Produces({MediaType.APPLICATION_XML})
    public List<Cliente> findAllClienteWithIncidencia() throws ReadException{
        List<Cliente> clientes = null;
        try {
            clientes = em.createNamedQuery("findAllClienteWithIncidencia").getResultList();
        }catch (InternalServerErrorException e) {
            LOG.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return clientes;
    }

    @GET
    @Path("findClienteByFullName/{fullName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Cliente> findClienteByFullName(@PathParam("fullName") String fullName)throws  ReadException{
        List<Cliente> clientes = null;
        try {
            clientes = em.createNamedQuery("findClienteByFullName").setParameter("fullName", fullName).getResultList();
        } catch (InternalServerErrorException e) {
                LOG.severe(e.getMessage());
                throw new ReadException(e.getMessage());
        }
        return clientes;
    }

}
