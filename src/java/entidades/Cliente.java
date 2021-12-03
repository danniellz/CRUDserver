/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Entity;
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
    
    /**
     * Fecha de regitro del cliente
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp fechaRegistro;
    
    /**
     * Metodo getter de la fecha de registro
     * @return feecha de registro
     */
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * Metodo setter de la fecha de registro
     * @param fechaRegistro Fecha de registro
     */
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.fechaRegistro);
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
        if (!Objects.equals(this.fechaRegistro, other.fechaRegistro)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "fechaRegistro=" + fechaRegistro + '}';
    }
    
}
