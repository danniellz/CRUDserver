/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Aritz Arrieta
 */
@NamedQueries({
   // @NamedQuery(name="findIncidenciaDeUnTrabajador",query="SELECT i FROM Incidencia i JOIN Recoge r JOIN Usuario u WHERE u.idUsuario=:idUsuario"),
    
    @NamedQuery(name="findIncidenciaDeUnUsuario", query="SELECT i FROM Incidencia i WHERE cliente_idUsuario LIKE:idUsuario")//,
                                                        //SELECT incidencia.* FROM Incidencia  WHERE cliente_idUsuario = 2
    //@NamedQuery(name="findIncidenciaDeUnUsuarioAcaba",query="SELECT distinct  i FROM  usuario u NATURAL JOIN  incidencia i WHERE u.idUsuario=:idUsuario  AND i.estado='CERRADO'")
})
@Entity
@Table(name = "incidencia", schema = "gesredb")
@XmlRootElement
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

    @OneToMany(mappedBy = "incidencia")
    private Set<Recoge> recoge;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Pieza pieza;
    /**
     * enumeracion del Tipo de las Incidencias
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoIncidencia tipoIncidencia;

    /**
     * enumeracion del Estado de las Incidencias
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoIncidencia estado;

   @XmlTransient//Si la quitas la anotacion  aparencen toda su informcaion que esta relacionada con el cliente
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * recive el numero de estrellas
     *
     * @return estrellas
     */
    public Integer getEstrellas() {
        return estrellas;
    }

    /**
     * recive el numero de Horas
     *
     * @return horas
     */
    public Integer getHoras() {
        return horas;
    }

    /**
     * recive el precio de la Incidencia
     *
     * @return precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * recive de la enumeracion el Tipo de Incidencia
     *
     * @return tipoInc
     */
    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    /**
     * recive de la enumeracion el Estado de Incidencia
     *
     * @return estado
     */
    public EstadoIncidencia getEstado() {
        return estado;
    }

    /**
     * Recive el Id de Incidencia
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRecoge(Set<Recoge> recoge) {
        this.recoge = recoge;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    //setters
    /**
     * Introduce el numero de estrellas
     *
     * @param estrellas
     */
    public void setEstrellas(Integer estrellas) {
        this.estrellas = estrellas;
    }

    /**
     * Introduce el numero de horas
     *
     * @param horas
     */
    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    /**
     * Introduce el precio de la Incidencia
     *
     * @param precio
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Introduce el tipo de Incidencia
     *
     * @param tipoIncidencia
     */
    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    /**
     * Introduce el Estado de la Incidencia
     *
     * @param estado
     */
    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    /**
     * Introduce el Id de la Incidencia
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.estrellas);
        hash = 83 * hash + Objects.hashCode(this.horas);
        hash = 83 * hash + Objects.hashCode(this.precio);
        hash = 83 * hash + Objects.hashCode(this.recoge);
        hash = 83 * hash + Objects.hashCode(this.cliente);
        hash = 83 * hash + Objects.hashCode(this.pieza);
        hash = 83 * hash + Objects.hashCode(this.tipoIncidencia);
        hash = 83 * hash + Objects.hashCode(this.estado);
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
        if (!Objects.equals(this.estrellas, other.estrellas)) {
            return false;
        }
        if (!Objects.equals(this.horas, other.horas)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
            return false;
        }
        if (!Objects.equals(this.recoge, other.recoge)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.pieza, other.pieza)) {
            return false;
        }
        if (this.tipoIncidencia != other.tipoIncidencia) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Incidencia{" + "id=" + id + ", estrellas=" + estrellas + ", horas=" + horas + ", precio=" + precio + ", recoge=" + recoge + ", cliente=" + cliente + ", pieza=" + pieza + ", tipoIncidencia=" + tipoIncidencia + ", estado=" + estado + '}';
    }

}
