package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import static java.util.logging.Level.ALL;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * // * Clase que define los atributos y los métodos de la entidad "Usuario".
 *
 * @author Jonathan Viañan y Aritz Arrieta
 */
@NamedQueries({
    @NamedQuery(name = "buscarUser", query = "SELECT u FROM Usuario u WHERE u.login LIKE:login")
    ,
    @NamedQuery(name = "todoLosUsuarios", query = "SELECT u FROM Usuario u")
    ,
    @NamedQuery(name = "iniciarSesionConLoginYPassword", query = "SELECT u FROM Usuario u WHERE u.login LIKE:login and u.password LIKE:password")
    ,
    @NamedQuery(name = "buscarUsuarioPorEmail", query = "SELECT u FROM Usuario u WHERE u.email LIKE :correo")

})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "privilege") //Columna que va a diferenciar a los distintos tipos de usuario.
@DiscriminatorValue("ADMIN")
@Table(name = "usuario", schema = "gesredb")
@XmlRootElement
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Id del usuario. Es la clave primaria de la tabla "usuario".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUsuario;
    /**
     * Login del usuario.
     */
    @NotNull
    private String login;

    /**
     * Email del usuario.
     */
    @NotNull
    private String email;
    /**
     * Nombre completo del usuario.
     */
    @NotNull
    private String fullName;
    /**
     * Estado del usuario, que puede ser ENABLED o DISABLED.
     */
    @NotNull
    @Enumerated(EnumType.STRING) //ORDINAL crea una columna de tipo int y STRING crea una columna de tipo varchar.
    private UserStatus status;
    /**
     * Privilegio del usuario, que puede ser ADMIN o USER.
     */
    @NotNull
    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserPrivilege privilege;

    /**
     * Contraseña del usuario.
     */
    @NotNull
    private String password;
    /**
     * Fecha de la última vez en la que se ha modificado la contraseña del
     * usuario.
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;

    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private Set<SignIn> signIn;

    public Set<SignIn> getSignIn() {
        return signIn;
    }

    public void setSignIn(Set<SignIn> signIn) {
        this.signIn = signIn;
    }
    /**
     * Método que obtiene el id del usuario.
     *
     * @return el id que se va a mostrar.
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Método que establece el id del usuario.
     *
     * @param idUsuario el id que se va a guardar.
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Método que obtiene el login del usuario.
     *
     * @return el login que se va a mostrar.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Método que establece el login del usuario.
     *
     * @param login el login que se va a guardar.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Método que obtiene el email del usuario.
     *
     * @return el email que se va a mostrar.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que establece el email del usuario.
     *
     * @param email el email que se va a guardar.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método que obtiene el nombre completo del usuario.
     *
     * @return el nombre completo que se va a mostrar.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Método que establece el nombre completo del usuario.
     *
     * @param fullName el nombre completo que se va a guardar.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Método que obtiene el estado del usuario.
     *
     * @return el estado que se va a mostrar.
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Método que establece el estado del usuario.
     *
     * @param status el estado que se va a guardar.
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Método que obtiene el privilegio del usuario.
     *
     * @return el privilegio que se va a mostrar.
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * Método que establece el privilegio del usuario.
     *
     * @param privilege el privilegio que se va a guardar.
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     * Método que obtiene la contraseña del usuario.
     *
     * @return la contraseña que se va a mostrar.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que establece la contraseña del usuario.
     *
     * @param password la contraseña que se va a guardar.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que obtiene el último cambio de contraseña del usuario.
     *
     * @return la fecha del último cambio de contraseña que se va a mostrar.
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Método que establece el último cambio de contraseña del usuario.
     *
     * @param lastPasswordChange la fecha del último cambio de contraseña que se
     * va a guardar.
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * Hashcode
     *
     * @return devuelve el hashcode
     */
    @Override   
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idUsuario);
        hash = 67 * hash + Objects.hashCode(this.login);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.fullName);
        hash = 67 * hash + Objects.hashCode(this.status);
        hash = 67 * hash + Objects.hashCode(this.privilege);
        hash = 67 * hash + Objects.hashCode(this.password);
        hash = 67 * hash + Objects.hashCode(this.lastPasswordChange);
        hash = 67 * hash + Objects.hashCode(this.signIn);
        return hash;
    }

    /**
     * Metodo para comprobar si dos clases son iguales
     *
     * @param obj objeto a comparar
     * @return devuelve un booleano si son o no iguales
     */
    @Override      
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.privilege != other.privilege) {
            return false;
        }
        if (!Objects.equals(this.lastPasswordChange, other.lastPasswordChange)) {
            return false;
        }
        if (!Objects.equals(this.signIn, other.signIn)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo para obtener la informacion de Usuario
     *
     * @return devuelve una representacion en forma de texto de Usuario
     */
    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", login=" + login + ", email=" + email + ", fullName=" + fullName + ", status=" + status + ", privilege=" + privilege + ", password=" + password + ", lastPasswordChange=" + lastPasswordChange + '}';
    }

}
