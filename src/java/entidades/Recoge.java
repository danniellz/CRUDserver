/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aritz Arrieta
 */
@Entity
@Table(name="recoge",schema="gesredb")

@XmlRootElement
public class Recoge implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @EmbeddedId
    private RecogeId idRecoge;
    @MapsId("idUsuario")
    @ManyToOne
    private Trabajador trabajador;
    @MapsId("incidenciaId")
    @ManyToOne
    private Incidencia incidencia;
    
    @NotNull
    private Integer horasEstimadas;
    @Temporal(TemporalType.TIMESTAMP)
    private Date  fechaRecogida;

    // getters

    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     *  Obtiene de Id de Recoge
     * @return Integer
     */
    public RecogeId getIdRecoge() {
        return idRecoge;
    }

    /**
     * Recoge la entidad Trabajador
     * @return trabajador
     */
    public Trabajador getTrabajador() {
        return trabajador;
    }

    /**
     * Recoge la entidad Incidencia
     * @return incidencia
     */
    public Incidencia getIncidencia() {
        return incidencia;
    }

    /**
     * Recoge las Horas estimadas
     * @return Integer
     */
    public Integer getHorasEstimadas() {
        return horasEstimadas;
    }

    /**
     * Recoge la fecha de Recogida
     * @return Timestamp
     */
    public Date getFechaRecogida() {
        return fechaRecogida;
    }
    

  //setters

    /** 
     * Establece el Id de Recoger
     * @param idRecoge
     */

    public void setIdRecoge(RecogeId idRecoge) {
        this.idRecoge = idRecoge;
    }

    /**
     * Establece un Trabajdor
     * @param trabajador
     */
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * Establece una Incidencia
     * @param incidencia
     */
    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

    /**
     * Establece las horas estimadas
     * @param horasEstimadas
     */
    public void setHorasEstimadas(Integer horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    /**
     * Establece la Fecha Estimada
     * @param fechaRecogida
     */
    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idRecoge);
        hash = 29 * hash + Objects.hashCode(this.trabajador);
        hash = 29 * hash + Objects.hashCode(this.incidencia);
        hash = 29 * hash + Objects.hashCode(this.horasEstimadas);
        hash = 29 * hash + Objects.hashCode(this.fechaRecogida);
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
        final Recoge other = (Recoge) obj;
        if (!Objects.equals(this.idRecoge, other.idRecoge)) {
            return false;
        }
        if (!Objects.equals(this.trabajador, other.trabajador)) {
            return false;
        }
        if (!Objects.equals(this.incidencia, other.incidencia)) {
            return false;
        }
        if (!Objects.equals(this.horasEstimadas, other.horasEstimadas)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecogida, other.fechaRecogida)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Recoge{" + "idRecoge=" + idRecoge + ", trabajador=" + trabajador + ", incidencia=" + incidencia + ", horasEstimadas=" + horasEstimadas + ", fechaRecogida=" + fechaRecogida + '}';
    }
    
    
}
