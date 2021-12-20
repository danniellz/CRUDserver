package excepciones;

/**
 * Clase Exception si ocurre un error al Borrar
 *
 * @author Daniel Brizuela
 */
public class DeleteException extends Exception {

    /**
     * Contructor vacio
     */
    public DeleteException() {
    }

    /**
     * Contructor con el mensaje de error
     *
     * @param msg mensaje de error
     */
    public DeleteException(String msg) {
        super(msg);
    }
}
