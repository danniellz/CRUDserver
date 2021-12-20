package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase de la entidad Cliente
 *
 * @author Mikel Matilla
 */

@NamedQueries({
    @NamedQuery(name = "findAllClienteWithIncidencia", query = "SELECT c FROM cliente c, Incidencia i WHERE c.idUsuario=i.cliente"),
        //SQL: select distinct usuario.* from usuario,incidencia where usuario.idUsuario = incidencia.cliente_idUsuario
    @NamedQuery(name = "findClienteByFullName", query = "SELECT c FROM cliente c WHERE c.fullName LIKE:fullName")
        //SQL: SELECT usuario.* FROM usuario WHERE usuario.fullName="" AND privilege="CLIENTE"
}
)

@Entity(name = "cliente")
@DiscriminatorValue("CLIENTE")
@XmlRootElement
public class Cliente extends Usuario implements Serializable {

    //Atributos
    /**
     * Relacion de incidencias
     */
    @OneToMany(mappedBy = "cliente",fetch= FetchType.EAGER)
    private Set<Incidencia> incidencias;

    /**
     * Fecha de registro del cliente
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    //Getters & Setters
    /**
     * Metodo getter de incidencias
     *
     * @return Incidencias
     */
    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    /**
     * Metodo setter de incidencias
     *
     * @param incidencias Incidencias
     */
   //@XmlTransient
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    /**
     * Metodo getter de la fecha de registro
     *
     * @return fecha de registro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo setter de la fecha de registro
     *
     * @param fechaRegistro Fecha de registro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

   /* @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.incidencias);
        hash = 59 * hash + Objects.hashCode(this.fechaRegistro);
        return hash;
    }*/

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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.incidencias, other.incidencias)) {
            return false;
        }
        return Objects.equals(this.fechaRegistro, other.fechaRegistro);
    }

    @Override
    public String toString() {
        return "Cliente{" + "incidencias=" + incidencias + ", fechaRegistro=" + fechaRegistro + '}';
    }

}
