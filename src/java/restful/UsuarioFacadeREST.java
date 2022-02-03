/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cripto.Cripto;
import cripto.Hash;
import static cripto.Hash.desencriptarContrasenia;
import email.EnvioEmail;
import entidades.Usuario;
import excepciones.*;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
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

        //entity.setPassword(cifrarContrasena(descifrarContrasena(entity.getPassword())));
      ///  entity.setPassword(descifrarContrasena(entity.getPassword()));
        entity.setPassword(Hash.cifradoSha(entity.getPassword()));
        try {
            LOGGER.info("UsuarioFacadeREST: Creando usuario");
            super.create(entity);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PUT
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(Usuario entity) {
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

    //FUNCIONA
    @GET
    @Path("Login/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> buscarUserPorLogin(@PathParam("login") String login) {
        List<Usuario> usuarios = null;
        try {
            usuarios = em.createNamedQuery("buscarUserPorLogin")
                    .setParameter("login", login)
                    .getResultList();

        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return usuarios;
    }

    //FUNCIONA
    @GET
    @Path("Email/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> buscarUsuarioPorEmail(@PathParam("email") String email) throws ReadException {
        List<Usuario> usuario = null;
        try {
            usuario = (List<Usuario>) em.createNamedQuery("buscarUsuarioPorEmail").setParameter("email", email).getResultList();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return usuario;
    }

    //FUNCIONA
    @GET
    @Path("Login y Password/{login},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> buscarUsuarioPorLoginYContrasenia(@PathParam("login") String login, @PathParam("password") String password) throws Exception {
        LOGGER.info("*********DESIFRANDO CONTRASEÑA*****");

        String contraseCifrada = Hash.cifradoSha(password);

        List< Usuario> usuario = null;
        try {

            LOGGER.severe("Contraseña cifrada  " + contraseCifrada);
            usuario = em.createNamedQuery("buscarUsuarioConLoginYPassword")
                    .setParameter("login", login)
                    .setParameter("password", contraseCifrada)
                    .getResultList();
            for (Usuario usuario1 : usuario) {
                if (!usuario1.getPassword().equals(contraseCifrada)) {
                    LOGGER.severe("Contraseña incorrecta ");
                    throw new AutenticacionFallidaException();
                }
            }
        } catch (Exception e) {
        }
        return usuario;
    }

    /**
     * FALTA PROBAR SI ENVIA EMAIL CORRECTAMENTE Método que busca un usuario por
     * su email para enviar el mail de recuperación de contraseña.
     *
     * @param usuario el usuario que se buscará.
     */
    @GET
    @Path("Enviar Mail Recuperacion/{email}")
    @Consumes({MediaType.APPLICATION_XML})
    public void buscarUsuarioParaEnviarMailRecuperarContrasenia(Usuario usuario) throws ReadException {
        try {
            LOGGER.info("UsuarioFacadeREST: Buscando usuario por email para enviar mail de recuperación de contraseña");
            Collection<Usuario> usuarioEmails = getEntityManager().createNamedQuery("buscarUsuarioPorEmail").setParameter("email", usuario.getEmail()).getResultList();
            if (!usuarioEmails.isEmpty()) {
                String newPassword = generarContrasenia();
                EnvioEmail.enviarMail(usuario.getEmail(), "Recuperar Contraseña", newPassword);
                Hash cifradoHash = new Hash();
                newPassword = cifradoHash.cifrarTextoEnHash(newPassword);
                for (Usuario user : usuarioEmails) {
                    user.setPassword(newPassword);
                }
            }
        } catch (Exception e) {
            throw new ReadException(e.getMessage());
        }
    }

    /**
     * FALTA PROBAR SI ENVIA EMAIL CORRECTAMENTE
     *
     * Método que busca un usuario por su email para enviar el mail de cambio de
     * contraseña.
     *
     * @param email el email que se buscará.
     */
    @POST
    @Path("enviarMailCambio")
    @Consumes({MediaType.APPLICATION_XML})
    public void buscarEmailParaEnviarMailCambiarContrasenia(Usuario usuario) throws ReadException {
        Collection<Usuario> usuarioEmails = getEntityManager().createNamedQuery("buscarUsuarioPorEmail").setParameter("email", usuario.getEmail()).getResultList();

        try {
            LOGGER.info("UsuarioFacadeREST: Buscando email para enviar mail de cambio de contraseña");
            

            String newPassword = generarContrasenia();
            EnvioEmail.enviarMail(usuario.getEmail(), "Cambio de Contraseña actualizada", usuario.getPassword());
            Hash cifradoHash = new Hash();
            newPassword = cifradoHash.cifrarTextoEnHash(newPassword);

            for (Usuario user : usuarioEmails) {
                user.setPassword(newPassword);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
    }

    @GET
    @Path("Todos los usuarios")
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> buscarTodosLosUsuarios() {
        List<Usuario> usuarios = null;
        try {
            usuarios = (List<Usuario>) em.createNamedQuery("buscarTodoLosUsuarios").getResultList();
        } catch (Exception ex) {
            System.out.println("error");
        }
        return usuarios;
    }

    @GET
    @Path("Nueva Contrasenia/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public void resetPasswordByLogin(@PathParam("email") String email) {
        try {
            Usuario usuario = (Usuario) em.createNamedQuery("buscarUsuarioPorEmail").setParameter("email", email).getSingleResult();
            if (usuario!=null) {
                //generar nueva contraseña
                Random rand = new Random(); //instance of random class

              
                
               // int int_random = rand.nextInt(99999999 - 00000000) + 99999999;
                String nuevaContra = generarContrasenia();
                String contra = Hash.cifradoSha(nuevaContra);

                usuario.setPassword(contra);
                EnvioEmail.enviarMail(usuario.getEmail(), "Reset de Contraseña", nuevaContra);
                if (!em.contains(usuario)) {
                    em.merge(usuario);
                }
                em.flush();
            }

        } catch (MessagingException e) {
            throw new InternalServerErrorException(e);
        }
    }

    @GET
    @Path("EmailV2/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario buscarUsuarioPorEmailV2(@PathParam("email") String email) throws ReadException {
        Usuario usuario = null;
        try {
            usuario = (Usuario) em.createNamedQuery("buscarUsuarioPorEmail").setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ReadException(e.getMessage());
        }
        return usuario;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

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
        Hash descifrarAsimetrico = new Hash();
        return descifrarAsimetrico.desencriptarContrasenia(contrasena);
    }

    public String generarContrasenia() {
        int leftLimit = 48; //Número '0'
        int rightLimit = 122; //Letra 'Z'
        int tamanioMax = 10;
        Random random = new Random();

        String nuevaContrasenia = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) //(0-9) || (A-Z) && (a-z)
                .limit(tamanioMax)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return nuevaContrasenia;
    }

}
