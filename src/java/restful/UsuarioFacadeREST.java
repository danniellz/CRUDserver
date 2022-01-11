/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;


import cripto.Cripto;
import cripto.Hash;
import email.EnvioEmail;
import entidades.Usuario;
import java.util.List;
import java.util.Random;
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

    @PersistenceContext(unitName = "GESREServerPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Usuario entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
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
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("login/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario findUserByLogin(@PathParam("login") String login) {
        Usuario usuarios = null;
        try {

            usuarios = (Usuario) em.createNamedQuery("buscarUser")
                    .setParameter("login", login)
                    .getSingleResult();


        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }
        return usuarios;
    }

    @GET
    @Path("nuevaContrasenia/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public void resetPasswordByLogin(@PathParam("login") String login) {
        Usuario usuarios = null;
        try {
            usuarios = findUserByLogin(login);
            if (usuarios.getLogin().isEmpty()) {

            } else {
                //generar nueva contraseña
                Random rand = new Random(); //instance of random class

                int int_random = rand.nextInt(99999999 - 00000000) + 99999999;
                String nuevaContra = String.valueOf(int_random);
                System.out.println(nuevaContra);
                String contra = cifradoHash(nuevaContra);
                
                usuarios.setPassword(contra);
                if (!em.contains(usuarios)) {
                    em.merge(usuarios);
                }
                em.flush();
                EnvioEmail.enviarMail(usuarios.getEmail(), "Reset de Contraseña", nuevaContra);
            }

        } catch (Exception e) {
            throw new InternalServerErrorException(e);
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("iniciarSesion/{login},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario buscarUsuarioPorLoginYContrasenia(@PathParam("login") String login, @PathParam("password") String password) {
        Usuario usuario;
        try {
            password = descifrado(password);
            password = cifradoHash(password);
            usuario = (Usuario) em.createNamedQuery("iniciarSesionConLoginYPassword").setParameter("login", login).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return usuario;
    }

    @GET
    @Path("buscarUsuarioPorEmail/{correo}")
    @Produces({MediaType.APPLICATION_XML})
    public Usuario buscarEmail(@PathParam("correo") String email) {
        Usuario u;
        try {
            u = (Usuario) em.createNamedQuery("buscarUsuarioPorEmail").setParameter("correo", email).getSingleResult();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        return u;
    }

    @GET
    @Path("cambiarContrasenia/{login},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarContraseniaPorLogin(@PathParam("login") String login, @PathParam("password") String password) {
        Usuario u = null;
        Usuario x = null;
        String asunto = "Contraseña Actualizada";
        String cuerpo = "Contraseña actualizada correctamente.";
        try {
            u = findUserByLogin(login);
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

    @GET
    @Path("cambiarContraseniaPorEmail/{correo},{password}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarContraseniaPoreEmail(@PathParam("correo") String email, @PathParam("password") String password) {
        Usuario u = null;
        Usuario x = null;
        try {
            u = buscarEmail(email);
            if (u.getLogin().isEmpty()) {

            } else {
                u.setPassword(password);
                actualizar(u);
            }

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

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
    
    private String cifradoHash(String contra){
        Hash hash = new Hash();
        return hash.cifrarTextoEnHash(contra);
        
    }
    
    private String descifrado(String contra) throws Exception{
        Cripto cripto = new Cripto();
        return cripto.descifrar(contra);
    } 

}
