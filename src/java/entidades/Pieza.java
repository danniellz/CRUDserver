package entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entidad Pieza para los distintos tipos de incidencia
 *
 * @author Daniel Brizuela
 */
@Entity
public class Pieza implements Serializable {

    /**
     * Atributos
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificacion de la Pieza
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Nombre de la Pieza
     */
    private String name;
    /**
     * Descripcion de la Pieza
     */
    private String descripcion;
    /**
     * Stock disponible de la Pieza
     */
    private Integer stock;

    /**
     * Obtener el id
     *
     * @return devuleve el id de Pieza
     */
    public Long getId() {
        return id;
    }

    /**
     * Establecer el id
     *
     * @param id establece el id de Pieza
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtener el nombre
     *
     * @return devuelve el nombre de la Pieza
     */
    public String getName() {
        return name;
    }

    /**
     * Establecer el nombre
     *
     * @param name establece el nombre de la Pieza
     */
    public void setName(String name) {
        this.name = name;
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
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.descripcion);
        hash = 73 * hash + Objects.hashCode(this.stock);
        return hash;
    }

    /**
     * Metodo equals para comparar si dos objetos son iguales
     *
     * @param obj el otro objeto a comparar
     * @return devuelve true si los Id son iguales
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
        if (!Objects.equals(this.id, other.id)) {
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
        return "Pieza{" + "id=" + id + ", name=" + name + ", descripcion=" + descripcion + ", stock=" + stock + '}';
    }
}
