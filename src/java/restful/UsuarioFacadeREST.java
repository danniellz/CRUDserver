/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cripto.Hash;
import entidades.Usuario;
import excepciones.*;
import java.util.Collection;
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
 * @author Jonathan Viñan y Aritz Arrieta
 */
@Stateless
@Path("entidades.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    private static final Logger LOGGER = Logger.getLogger("restful.UsuarioFacadeREST");

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Usuario entity) {
        try {
            LOGGER.info("UsuarioFacadeREST: Creando usuario");
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(UsuarioFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            super.remove(super.find(id));
        } catch (ReadException ex) {
            Logger.getLogger(UsuarioFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DeleteException ex) {
            Logger.getLogger(UsuarioFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario find(@PathParam("id") Integer id) throws ReadException {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> findAll() throws ReadException {
        return super.findAll();
    }

    //**************************************************************************************************************//
    //FUNCIONA
    @GET
    @Path("buscarUsuarioPorLogin/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario buscarUserPorLogin(@PathParam("login") String login) {
        Usuario usuarios = null;
        try {
            usuarios = (Usuario) em.createNamedQuery("buscarUserPorLogin")
                    .setParameter("login", login)
                    .getSingleResult();

        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return usuarios;
    }

    //FUNCIONA
    @GET
    @Path("buscarUsuarioPorEmail/{correo}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario buscarUsuarioPorEmail(@PathParam("correo") String email) {
        Usuario usuario;
        try {
            usuario = (Usuario) em.createNamedQuery("buscarUsuarioPorEmail").setParameter("correo", email).getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return usuario;
    }

    //FUNCIONA
    @GET
    @Path("InicioSecion/{login},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario buscarUsuarioPorLoginYContraseniav1(@PathParam("login") String login, @PathParam("password") String password) throws Exception {

        LOGGER.info("*********DESIFRANDO CONTRASEÑA*****");
        String contraseCifrada = Hash.cifradoSha(Hash.desencriptarContrasenia(password));
        Usuario usuario = null;
        try {
            usuario = (Usuario) em.createNamedQuery("buscarUsuarioConLoginYPassword")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
            if (!usuario.getPassword().equals(contraseCifrada)) {
                LOGGER.severe("Contraseña incorrecta ");
                throw new AutenticacionFallidaException();
            }
        } catch (Exception e) {
        }
        return usuario;
    }

    /**
     * Método que busca un usuario por su email para enviar el mail de
     * recuperación de contraseña.
     *
     * @param usuario el usuario que se buscará.
     */
    @POST
    @Path("enviarMailRecuperacion")
    @Consumes({MediaType.APPLICATION_XML})
    public void buscarUsuarioParaEnviarMailRecuperarContrasenia(Usuario usuario) {
       
    }

    //ENVIAR EMAIL DE RECUPERACION Falta probarla
    /**
     * Método que busca un usuario por su email para enviar el mail de
     * recuperación de contraseña.
     *
     * @param usuario el usuario que se buscará.
     *
     * @POST
     * @Path("enviarMailRecuperacion")
     * @Consumes({MediaType.APPLICATION_XML}) public void
     * buscarUsuarioParaEnviarMailRecuperarContrasenia(Usuario usuario) { try {
     * LOGGER.info("UsuarioFacadeREST: Buscando usuario por email para enviar
     * mail de recuperación de contraseña"); try {
     * LOGGER.info("UsuarioAbstractFacade: Buscando usuario por email para
     * enviar mail de recuperación de contraseña");
     *
     * Collection<Usuario> usuarioCol =
     * getEntityManager().createNamedQuery("buscarUsuarioPorEmail").setParameter("email",
     * usuario.getEmail()).getResultList();
     *
     * if (!usuarioCol.isEmpty()) { String nuevaContrasenia =
     * EnvioEmail.enviarMailRecuperarContrasenia(usuario); Hash cifradoHash =
     * new Hash(); nuevaContrasenia =
     * cifradoHash.cifrarTextoEnHash(nuevaContrasenia);
     *
     * for (Usuario u : usuarioCol) { u.setPassword(nuevaContrasenia); } } }
     * catch (Exception e) { LOGGER.severe(e.getMessage()); throw new
     * ReadException(e.getMessage()); } } catch (ReadException e) {
     * LOGGER.severe(e.getMessage()); throw new
     * InternalServerErrorException(e.getMessage()); } }
     *
     * //ENVIAR EMAIL DE CAMBIO CONTRASEÑA Falta probarla /** Método que busca
     * un usuario por su email para enviar el mail de cambio de contraseña.
     *
     * @param email el email que se buscará.
     *
     * @POST
     * @Path("enviarMailCambio")
     * @Consumes({MediaType.APPLICATION_XML}) public void
     * buscarEmailParaEnviarMailCambiarContrasenia(String email) { try {
     * LOGGER.info("UsuarioFacadeREST: Buscando email para enviar mail de cambio
     * de contraseña");
     * getEntityManager().createNamedQuery("buscarUsuarioPorEmail").setParameter("email",
     * email).getResultList(); EnvioEmail.enviarMailCambiarContrasenia(email); }
     * catch (Exception e) { LOGGER.severe(e.getMessage()); throw new
     * InternalServerErrorException(e.getMessage()); } }
     *
     * // INICIAR SECION V1Falta probarla /
     *
     * @GET
     * @Path("iniciarSesion/{login},{password}")
     * @Produces({MediaType.APPLICATION_XML}) public Usuario
     * buscarUsuarioPorLoginYContrasenia(@PathParam("login") String login,
     * @PathParam("password") String password) { Usuario usuario; try { //
     * password = descifrado(password); byte[] contracifrada =
     * cifrado(password);
     * LOGGER.info("*******************************************************Contraseña
     * Cifrada: " + new String(contracifrada)); byte[] contraDescifra =
     * descifrado(contracifrada);
     * LOGGER.info("*****************************************************contraseña
     * descifrada: " + password); String contra = hasheado(new
     * String(contraDescifra));
     * LOGGER.info("*****************************************************contraseña
     * Hasheada: " + contra);
     *
     * usuario = (Usuario)
     * em.createNamedQuery("iniciarSesionConLoginYPassword").setParameter("login",
     * login).setParameter("password", contra).getSingleResult();
     *
     * } catch (Exception e) { throw new
     * InternalServerErrorException(e.getMessage()); } return usuario; }
     */
    /**
     * ********************************************
     */
    // INICIAR SECION V1Falta probarla
    @GET
    @Path("BuscarTodosLosUsurios")
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> buscarTodosLosTrabajadores() {
        List<Usuario> usuarios = null;
        try {
            usuarios = (List<Usuario>) em.createNamedQuery("buscarTodoLosUsuarios").getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return usuarios;
    }

    @GET
    @Path("nuevaContrasenia/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public void resetPasswordByLogin(@PathParam("email") String email) {
        Usuario usuarios = null;
        String nuevaContra = "1234";
        try {
            usuarios = buscarUsuarioPorEmail(email);

            if (usuarios.getLogin().isEmpty()) {
                LOGGER.info("No existe el email");
            } else {
                LOGGER.info("*************CAMBIANDO CONTRASEÑA***************+");
                System.out.println(nuevaContra);
                String contra = (nuevaContra);
                contra = hasheado(contra);
                usuarios.setPassword(contra);
                actualizar(usuarios);
                LOGGER.info("************* CONTRASEÑA ACTUALIZADA***************+");
                //  EnvioEmail.enviarMail(usuarios.getEmail(), "Reset de Contraseña", nuevaContra);
            }

        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /*
    @GET
    @Path("cambiarContrasenia/{login},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarContraseniaPorLogin(@PathParam("login") String login, @PathParam("password") String password) {
        Usuario u = null;
        Usuario x = null;
        String asunto = "Contraseña Actualizada";
        String cuerpo = "Contraseña actualizada correctamente.";
        try {
            u = buscarUserPorLogin(login);
            if (u.getLogin().isEmpty()) {

            } else {
                u.setPassword(password);
                actualizar(u);
            }
            EnvioEmail.enviarMail(u.getEmail(), asunto, cuerpo);

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }

    /*
    @GET
    @Path("cambiarContraseniaPorEmail/{correo},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarContraseniaPoreEmail(@PathParam("correo") String email, @PathParam("password") String password) {
        Usuario u = null;
      
        try {
            u = buscarUsuarioPorEmail(email);
            if (u.getLogin().isEmpty()) {

            } else {
                u.setPassword(password);
                actualizar(u);
            }

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

    }
     */
    public void actualizar(Usuario usuario) {
        try {
            if (!em.contains(usuario)) {
                em.merge(usuario);
            }
            em.flush();

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /*
    private byte[] cifrado(String contra) {
        Cripto cripto = new Cripto();
        byte[] contraCifrada = null;
        try {
            contraCifrada = cripto.cifrar(contra);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contraCifrada;

    }

    private byte[] descifrado(byte[] contra) throws Exception {
        Cripto cripto = new Cripto();
        byte[] contraDescifrada = null;
        contraDescifrada = cripto.descifrar(contra);

        return contraDescifrada;
    }
     */
    private String hasheado(String contra) throws Exception {
        Hash hasheado = new Hash();
        String cifrarTextoEnHash = hasheado.cifrarTextoEnHash(contra);

        return cifrarTextoEnHash;
    }

}
