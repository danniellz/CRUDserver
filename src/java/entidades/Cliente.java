package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Clase de la entidad Cliente
 * @author Mikel Matilla
 */
@Entity
@Table(name="cliente", schema="gesredb")
public class Cliente extends Usuario implements Serializable {

    
    //Atributos


    
    /**
     * Relacion de incidencias
     */
    @OneToMany (cascade=ALL, mappedBy="cliente")
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
     * @return Incidencias
     */
    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    /**
     * Metodo setter de incidencias
     * @param incidencias Incidencias
     */
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
    
    /**
     * Metodo getter de la fecha de registro
     * @return fecha de registro
     */
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo setter de la fecha de registro
     * @param fechaRegistro Fecha de registro
     */
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.incidencias);
        hash = 59 * hash + Objects.hashCode(this.fechaRegistro);
        return hash;
    }

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
