/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.Recoge;
import entidades.RecogeId;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Jonathan Viñan y Aritz Arrieta
 */
@Stateless
@Path("entidades.recoge")
public class RecogeFacadeREST extends AbstractFacade<Recoge> {

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    private RecogeId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;trabajador_idUsuario=trabajador_idUsuarioValue;incidenciaId=incidenciaIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entidades.RecogeId key = new entidades.RecogeId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> trabajador_idUsuario = map.get("trabajador_idUsuario");
        if (trabajador_idUsuario != null && !trabajador_idUsuario.isEmpty()) {
            key.setTrabajador_idUsuario(new java.lang.Integer(trabajador_idUsuario.get(0)));
        }
        java.util.List<String> incidenciaId = map.get("incidenciaId");
        if (incidenciaId != null && !incidenciaId.isEmpty()) {
            key.setIncidencia_id(new java.lang.Integer(incidenciaId.get(0)));
        }
        return key;
    }

    public RecogeFacadeREST() {
        super(Recoge.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Recoge entity) {
        try {
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(RecogeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, Recoge entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(RecogeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entidades.RecogeId key = getPrimaryKey(id);
        try {
            super.remove(super.find(key));
        } catch (ReadException ex) {
            Logger.getLogger(RecogeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteException ex) {
            Logger.getLogger(RecogeFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Recoge find(@PathParam("id") PathSegment id) /*REVISAR*/throws ReadException {
        entidades.RecogeId key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Recoge> findAll() /*REVISAR*/ throws ReadException {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
