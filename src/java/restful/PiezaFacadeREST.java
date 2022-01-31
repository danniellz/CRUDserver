package restful;

import entidades.Pieza;
import excepciones.CreateException;
import excepciones.DeleteException;
import excepciones.PiezaExisteException;
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
 * RESTful de la entidad Pieza
 *
 * @author Daniel Brizuela
 */
@Stateless
@Path("entidades.pieza")
public class PiezaFacadeREST extends AbstractFacade<Pieza> {

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    //LOGGER
    private static final Logger LOG = Logger.getLogger(Pieza.class.getName());

    /**
     * Contructor Pieza
     */
    public PiezaFacadeREST() {
        super(Pieza.class);
    }

    /**
     * Crear nueva Pieza
     *
     * @param entity la entidad Pieza
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Pieza entity) {
        List<Pieza> piezas;
        try {
            piezas = findAllPiezaByName(entity.getNombre());
            if(piezas.isEmpty()){
                LOG.info("La pieza no existe, el proceso de creación puede continuar");
                super.create(entity);
            }else{
                throw new PiezaExisteException();
            }
        } catch (CreateException ex) {
            Logger.getLogger(PiezaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ReadException ex) {
            Logger.getLogger(PiezaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PiezaExisteException ex) {
            LOG.severe(ex.getMessage());
        }
    }

    /**
     * Modificar Pieza por id
     *
     * @param id id de la Pieza
     * @param entity la entidad Pieza
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Pieza entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(PiezaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Borrar Pieza por id
     *
     * @param id id de la Pieza
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException ex) {
            Logger.getLogger(PiezaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteException ex) {
            Logger.getLogger(PiezaFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Buscar pieza por id
     *
     * @param id id de la Pieza
     * @return devuelve una pieza segun el id
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Pieza find(@PathParam("id") Integer id)/*REVISAR*/ throws ReadException {
        return super.find(id);
    }

    /**
     * Obtener una lista de todas las piezas en stock de un trabajador por su ID
     *
     * @param idUsuario id del Usuario Trabajador
     * @return devuelve todas las piezas en stock de un trabajador
     * @throws excepciones.ReadException
     */
    @GET
    @Path("stock/{idUsuario}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Pieza> findAllPiezaInStock(@PathParam("idUsuario") String idUsuario) throws ReadException {
        List<Pieza> piezas = null;
        try {
            LOG.info("GESREserver/PiezaFacadeRest: Buscando piezas en stock del Trabajador con ID '" + idUsuario + "'");
            piezas = em.createNamedQuery("findAllPiezaInStockByTrabajadorId").setParameter("idUsuario", idUsuario).getResultList();
        } catch (Exception ex) {
            LOG.severe("GESREserver/PiezaFacadeRest: Ha ocurrido un error al buscar piezas en stock");
            throw new ReadException();
        }
        return piezas;
    }

    /**
     * Obtener una lista de todas las piezas con un determinado nombre
     *
     * @param nombre nombre de la Pieza
     * @return devuelve una lista de todas las piezas con un determinado nombre
     * @throws excepciones.ReadException
     */
    @GET
    @Path("nombre/{nombre}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Pieza> findAllPiezaByName(@PathParam("nombre") String nombre) throws ReadException {
        List<Pieza> piezas = null;
        try {
            LOG.info("GESREserver/PiezaFacadeRest: Buscando piezas por el nombre '" + nombre + "'");
            piezas = em.createNamedQuery("findAllPiezaByName").setParameter("nombre", nombre).getResultList();
        } catch (Exception ex) {
            LOG.severe("GESREserver/PiezaFacadeRest: Ha ocurrido un error al buscar piezas por su nombre");
            throw new ReadException();
        }
        return piezas;
    }

    /**
     * Obtener una lista de todas piezas de un trabajador por su ID
     *
     * @param idUsuario id del Usuario Trabajador
     * @return devuelve una lista de todas piezas de un trabajador
     * @throws excepciones.ReadException
     */
    @GET
    @Path("idUsuario/{idUsuario}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Pieza> findAllPiezaByTrabajadorId(@PathParam("idUsuario") String idUsuario) throws ReadException {
        List<Pieza> piezas = null;
        try {
            LOG.info("GESREserver/PiezaFacadeRest: Buscando todas las piezas del trabajador con ID '" + idUsuario + "'");
            piezas = em.createNamedQuery("findAllPiezaByTrabajadorId").setParameter("idUsuario", idUsuario).getResultList();
        } catch (Exception ex) {
            LOG.severe("GESREserver/PiezaFacadeRest: Ha ocurrido un error al buscar todas las piezas del Trabajador con ID '" + idUsuario + "'");
            throw new ReadException();
        }
        return piezas;
    }

    /**
     * Obtener una lista de todas las piezas
     *
     * @return devuelve una lista de todas las piezas
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Pieza> findAll() /*REVISAR*/ throws ReadException {
        return super.findAll();
    }

    /**
     * EntityManager
     *
     * @return devuelve el EntityManager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
