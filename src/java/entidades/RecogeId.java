/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Aritz Arrieta
 */
@Embeddable
public class RecogeId implements Serializable {

    private Integer trabajador_idUsuario;
    private Integer incidenciaId;

    public Integer getTrabajador_idUsuario() {
        return trabajador_idUsuario;
    }

    public void setTrabajador_idUsuario(Integer trabajador_idUsuario) {
        this.trabajador_idUsuario = trabajador_idUsuario;
    }

    public Integer getIncidenciaId() {
        return incidenciaId;
    }

    public void setIncidenciaId(Integer incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.trabajador_idUsuario);
        hash = 59 * hash + Objects.hashCode(this.incidenciaId);
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
        final RecogeId other = (RecogeId) obj;
        if (!Objects.equals(this.trabajador_idUsuario, other.trabajador_idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.incidenciaId, other.incidenciaId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RecogeId{" + "trabajador_idUsuario=" + trabajador_idUsuario + ", incidenciaId=" + incidenciaId + '}';
    }

}
