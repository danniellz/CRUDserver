/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aritz Arrieta
 */
@Entity
@Table(name="recoge",schema="gesredb")

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
    private Timestamp  fechaRecogida;

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
    public Timestamp getFechaRecogida() {
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
    public void setFechaRecogida(Timestamp fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    

    @Override
    public String toString() {
        return "Recoge{" + "idRecoge=" + idRecoge + ", trabajador=" + trabajador + ", incidencia=" + incidencia + ", horasEstimadas=" + horasEstimadas + ", fechaRecogida=" + fechaRecogida + '}';
    }
    
    
}
