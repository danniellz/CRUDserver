/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cripto.Cripto;
import cripto.Hash;
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
        // entity.setPassword(descifrarContrasena(entity.getPassword()));
        // entity.setPassword(cifrarContrasena(descifrarContrasena(entity.getPassword())));
        entity.setPassword(descifrarContrasena(entity.getPassword()));
        entity.setPassword(cifrarContrasena(entity.getPassword()));
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
        // entity.setPassword(Hash.cifradoSha(Hash.desencriptarContrasenia(entity.getPassword())));
        entity.setPassword(descifrarContrasena(entity.getPassword()));
        entity.setPassword(cifrarContrasena(entity.getPassword()));
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

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Trabajador find(@PathParam("id") Integer id) throws ReadException {
        return super.find(id);
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
            trabajadores = (List<Trabajador>) em.createNamedQuery("buscarTodosLosTrabajadores").getResultList();
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

    /**
     * Cifra la contraseña para guardarla en la base de datos.
     *
     * @param contrasena La contraseña del usuario.
     * @return La contraseña cifrada.
     */
    private String cifrarContrasena(String contrasena) {
        LOGGER.info("UsuarioFacadeREST: Cifrando contraseña");
        Hash cifrarHash = new Hash();
        return cifrarHash.cifrarTextoEnHash(contrasena);
    }

    /**
     * Descifra la contraseña que le ha llegado del cliente.
     *
     * @param contrasena La contraseña cifrada del usuario.
     * @return La contraseña descifrada.
     */
    private String descifrarContrasena(String contrasena) {
        LOGGER.info("UsuarioFacadeREST: Descifrando contraseña");
        return Hash.cifradoSha(contrasena);
        
    }
}
