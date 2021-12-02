/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aritz Arrieta
 */
@Entity
@Table(name="incidencia",schema="gesredb")

public class Incidencia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   /**
    * integer identificador de la Indidencia
    */
    private Integer id;
    
  /**
   * integer estrellas para medir valoraciones
   */
   
    private Integer estrellas;
    /**
     * integer Horas para saber cuanto ha tardado el trabajador
     */
   
    private Integer horas;
    
   /**
    * Precio Final de la Incidencia
    */
    private Double precio;
    
    //private Cliente cliente;
  
   /**
    * enumeracion del Tipo de las Incidencias
    */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoIncidencia tipoInc;
    
   /**
    * enumeracion del Estado de las Incidencias
    */
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoIncidencia estado;

    
    
    /**
     * 
     * @return 
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * recive el numero de estrellas 
     * @return estrellas
     */
    public Integer getEstrellas() {
        return estrellas;
    }

    /**
     * recive el numero de Horas
     * @return horas
     */
    public Integer getHoras() {
        return horas;
    }

    /**
     * recive el precio de la Incidencia
     * @return precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     *  recive de la enumeracion el Tipo de Incidencia
     * @return tipoInc
     */
    public TipoIncidencia getTipoInc() {
        return tipoInc;
    }

    /**
     * recive de la enumeracion el Estado de Incidencia
     * @return estado
     */ 
    public EstadoIncidencia getEstado() {
        return estado;
    }
    
    /**
     * Recive el Id de Incidencia
     * @return
     */
    public int getId() {
        return id;
    }

    
    //setters

    /**
     * Introduce el numero de estrellas
     * @param estrellas
     */
    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * Introduce el numero de horas
     * @param horas
     */
    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    /**
     * Introduce el precio de la Incidencia
     * @param precio
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /** 
     * Introduce el tipo de Incidencia
     * @param tipoInc
     */
    public void setTipoInc(TipoIncidencia tipoInc) {
        this.tipoInc = tipoInc;
    }

    /** 
     * Introduce el Estado de la Incidencia
     * @param estado
     */
    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }
    
    /**
     * Introduce el Id de la Incidencia
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
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
        final Incidencia other = (Incidencia) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

 

    @Override
    public String toString() {
        return "entidades.Incidencia[ id=" + id + " ]";
    }
    
}
