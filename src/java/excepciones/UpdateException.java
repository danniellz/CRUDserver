package excepciones;

/**
 * Clase Exception si ocurre un error al Actualizar datos
 *
 * @author Daniel Brizuela
 */
public class UpdateException extends Exception {

    /**
     * Contructor vacio
     */
    public UpdateException() {
    }

    /**
     * Contructor con el mensaje de error
     *
     * @param msg mensaje de error
     */
    public UpdateException(String msg) {
        super(msg);
    }
}
