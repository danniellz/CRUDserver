/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Mikel Matilla
 */
@Entity
@Table(name="cliente", schema="gesredb")
public class Cliente extends User implements Serializable {
    
    //Atributos
    @NotNull
    private Timestamp fechaRegistro;
    private Set<Incidencia> incidencias;

    //Getters
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    //Setters
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

}
