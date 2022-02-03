/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.Incidencia;
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
 * @author Aritz Arrieta
 */
@Stateless
@Path("entidades.incidencia")
public class IncidenciaFacadeREST extends AbstractFacade<Incidencia> {

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    /**
     * Restfull de la Entidad de Incidencia, con CRUD completo
     */
    public IncidenciaFacadeREST() {
        super(Incidencia.class);
    }

    /**
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Incidencia entity) {
        try {
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(IncidenciaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param id
     * @param entity
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Incidencia entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(IncidenciaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {

        try {
            super.remove(super.find(id));
        } catch (ReadException ex) {
            Logger.getLogger(IncidenciaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteException ex) {
            Logger.getLogger(IncidenciaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Incidencia find(@PathParam("id") Integer id) throws ReadException {

        return super.find(id);

    }
    //findIncidenciaDeUnTrabajador

    /**
     *
     * @param idUsuario
     * @return
     * @throws InternalServerErrorException
     */
    @GET
    @Path("/InciTrabajador/{idUsuario}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Incidencia> findIncidenciaDeUnTrabajador(@PathParam("idUsuario") Integer idUsuario) throws InternalServerErrorException {
        List<Incidencia> incidencias = null;
        try {
            incidencias = em.createNamedQuery("findIncidenciaDeUnTrabajador")
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();

        } catch (Exception e) {

            throw new InternalServerErrorException(e);

        }
        return incidencias;

    }

    //findIncidenciaDeUnUsuario
    /**
     *
     * @param idUsuario
     * @return
     * @throws InternalServerErrorException
     */
    @GET
    @Path("/InciUsuario/{idUsuario}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Incidencia> findIncidenciaDeUnUsuario(@PathParam("idUsuario") Integer idUsuario) throws InternalServerErrorException {
        List<Incidencia> incidencias = null;
        try {
            incidencias = em.createNamedQuery("findIncidenciaDeUnUsuario")
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();

        } catch (Exception e) {

            throw new InternalServerErrorException(e);

        }
        return incidencias;

    }
    //findIncidenciaDeUnUsuarioCerrada

    /**
     *
     * @param idUsuario
     * @return
     * @throws InternalServerErrorException
     */
    @GET
    @Path("/InciAcabadaUsuario/{idUsuario}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Incidencia> findIncidenciaAcabadaDeUnUsuario(@PathParam("idUsuario") Integer idUsuario) throws InternalServerErrorException {
        List<Incidencia> incidencias = null;
        try {
            incidencias = em.createNamedQuery("findIncidenciaDeUnUsuarioAcabada")
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();

        } catch (Exception e) {

            throw new InternalServerErrorException(e);

        }
        return incidencias;

    }

    /**
     *
     * @return
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Incidencia> findAll()/*REVISAR*/ throws ReadException {
        return super.findAll();
    }

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
