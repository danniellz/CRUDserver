package entidades;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad Pieza para los distintos tipos de incidencia
 *
 * @author Daniel Brizuela
 */
@NamedQueries({
    @NamedQuery(name = "findAllPiezaInStockByTrabajadorId", query = "SELECT p FROM Pieza p WHERE p.stock>0 and trabajador_idUsuario LIKE :idUsuario")
    ,
    @NamedQuery(name = "findAllPiezaByName", query = "SELECT p FROM Pieza p WHERE p.nombre LIKE :nombre")
    ,
    @NamedQuery(name = "findAllPiezaByTrabajadorId", query = "SELECT p FROM Pieza p WHERE trabajador_idUsuario LIKE :idUsuario")}
)
@Entity
@Table(name = "pieza", schema = "gesredb")
@XmlRootElement
public class Pieza implements Serializable {

    /**
     * ATRIBUTOS
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificacion de la Pieza
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * Nombre de la Pieza
     */
    private String nombre;

    /**
     * Descripcion de la Pieza
     */
    private String descripcion;
    /**
     * Stock disponible de la Pieza
     */
    private Integer stock;
    /**
     * Relacion ManyToOne con trabajador
     */
    @ManyToOne(fetch=FetchType.EAGER)
    private Trabajador trabajador;
    /**
     * Relacion OneToMany con incidencias
     */
    @OneToMany(mappedBy = "pieza", fetch=FetchType.EAGER)
    private Set<Incidencia> incidencias;

    //GETTERS y SETTERS
    /**
     * Obtener el trabajador
     *
     * @return devuelve el trabajador
     */
    @XmlTransient//Si se quita la anotacion aparence toda la informcaion relacionada con el trabajador
    public Trabajador getTrabajador() {
        return trabajador;
    }

    /**
     * Establecer el Trabajador
     *
     * @param trabajador establece el trabajador
     */
    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    /**
     * Obtener las incidencias en las que se usa la Pieza
     *
     * @return devuelve las incidencias
     */
    @XmlTransient
    public Set<Incidencia> getIncidencias() {
        return incidencias;
    }

    /**
     * Establecer las incidencias en las que se usa la Pieza
     *
     * @param incidencias establece las incidencias
     */
    public void setIncidencias(Set<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    /**
     * Obtener el id
     *
     * @return devuleve el id de Pieza
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establecer el id
     *
     * @param id establece el id de Pieza
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtener el nombre
     *
     * @return devuelve el nombre de la Pieza
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establecer el nombre
     *
     * @param nombre establece el nombre de la pieza
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener la descripcion
     *
     * @return devuelve la descripcion de la Pieza
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establecer la descripcion
     *
     * @param descripcion establece la descripcion de la Pieza
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtener el stock
     *
     * @return devuelve el stock disponible de la Pieza
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * Establecer el Stock
     *
     * @param stock establece el stock de la Pieza
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * Metodo hashCode
     *
     * @return deuvelve un entero
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
       
        return hash;
    }

    /**
     * Metodo equals para comparar si dos objetos son iguales
     *
     * @param obj el otro objeto a comparar
     * @return devuelve un booleano si son o no iguales
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
        final Pieza other = (Pieza) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.stock, other.stock)) {
            return false;
        }
        if (!Objects.equals(this.trabajador, other.trabajador)) {
            return false;
        }
        if (!Objects.equals(this.incidencias, other.incidencias)) {
            return false;
        }
        return true;
    }

    /**
     * Metodo para obtener la informacion de Pieza
     *
     * @return devuelve una representacion en forma de texto de Pieza
     */
    @Override
    public String toString() {
        return "Pieza{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", stock=" + stock + ", trabajador=" + trabajador + ", incidencias=" + incidencias + '}';
    }
    

}
