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
@Table(name="gesredb",schema="gesredb")

public class Incidencia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
  
   
    private Integer estrellas;
    
   
    private Integer horas;
    
   
    private Double precio;
    
    //private Cliente cliente;
  
   
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoIncidencia tipoInc;
    
   
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoIncidencia estado;

    
    
    //getters
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public Integer getHoras() {
        return horas;
    }

    public Double getPrecio() {
        return precio;
    }

    public TipoIncidencia getTipoInc() {
        return tipoInc;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }
    

    public int getId() {
        return id;
    }

    
    //setters
    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setTipoInc(TipoIncidencia tipoInc) {
        this.tipoInc = tipoInc;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }
    
    
    
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

   /* @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Incidencia)) {
            return false;
        }
        Incidencia other = (Incidencia) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }*/

    @Override
    public String toString() {
        return "entidades.Incidencia[ id=" + id + " ]";
    }
    
}
