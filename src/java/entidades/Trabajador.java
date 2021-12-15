/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase que define los atributos y los métodos de la entidad "Trabajador".
 *
 * @author Jonathan Viñan
 */
@Entity(name = "trabajador")
@DiscriminatorValue("TRABAJADOR")
@XmlRootElement
public class Trabajador extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Precio a las horas incurridas en el Trabajador
     */
    @NotNull
    private Double precioHora;

    /**
     * Fecha del contrato del Trabajador
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaContrato;

    /**
     * Relacion 1:N con Piezas
     *
     */
    @OneToMany(mappedBy = "trabajador")
    private Set<Pieza> piezas;

    @OneToMany(mappedBy = "trabajador")
    private Set<Recoge> recoge;

    @XmlTransient
    public Set<Recoge> getRecoge() {
        return recoge;
    }

    public void setRecoge(Set<Recoge> recoge) {
        this.recoge = recoge;
    }

    /**
     * Metodo que obtine el pricio hora del trbajador
     *
     * @return
     */
    public Double getPrecioHora() {
        return precioHora;
    }

    /**
     * Metodo que establece el precio hora del trabajador
     *
     * @param precioHora
     */
    public void setPrecioHora(Double precioHora) {
        this.precioHora = precioHora;
    }

    /**
     * Metodo que obtine el fecha contrato del trbajador
     *
     * @return
     */
    public Date getFechaContrato() {
        return fechaContrato;
    }

    /**
     * Metodo que establece la fecha contrato del trabajador
     *
     * @param fechaContrato
     */
    public void setFechaContrato(Date fechaContrato) {
        this.fechaContrato = fechaContrato;
    }

    /**
     * Método que obtiene la colección de piezas.
     *
     * @return las piezas de la colección
     */
    @XmlTransient
    public Set<Pieza> getPiezas() {
        return piezas;
    }

    /**
     * Método que establece la colección de piezas.
     *
     * @param piezas las piezas que se van a guardar
     */
    public void setPiezas(Set<Pieza> piezas) {
        this.piezas = piezas;
    }

    /**
     * Método que compara el código hash de dos objetos.
     *
     * @return el código hash del objeto.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.precioHora);
        hash = 53 * hash + Objects.hashCode(this.fechaContrato);
        hash = 53 * hash + Objects.hashCode(this.piezas);
        hash = 53 * hash + Objects.hashCode(this.recoge);
        return hash;
    }

    /**
     * Método que compara si un objeto es igual al objeto "Trabajador".
     *
     * @param obj cualquier tipo de objeto.
     * @return un "false" si los objetos no son iguales y un "true" si lo son.
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
        final Trabajador other = (Trabajador) obj;
        if (!Objects.equals(this.precioHora, other.precioHora)) {
            return false;
        }
        if (!Objects.equals(this.fechaContrato, other.fechaContrato)) {
            return false;
        }
        if (!Objects.equals(this.piezas, other.piezas)) {
            return false;
        }
        if (!Objects.equals(this.recoge, other.recoge)) {
            return false;
        }
        return true;
    }

    /**
     * Método que devuelve un String con los atributos del Trabajdor.
     *
     * @return un String con los atributos de la entidad.
     */
    @Override
    public String toString() {
        return "Trabajador{" + "precioHora=" + precioHora + ", fechaContrato=" + fechaContrato + ", piezas=" + piezas + ", recoge=" + recoge + '}';
    }

}
