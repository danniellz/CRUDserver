package excepciones;

/**
 * Clase Exception si ocurre un error al crear
 *
 * @author Daniel Brizuela
 */
public class CreateException extends Exception {

    /**
     * Contructor vacio
     */
    public CreateException() {
    }

    /**
     * Contructor con el mensaje de error
     *
     * @param msg mensaje de error
     */
    public CreateException(String msg) {
        super(msg);
    }
}
