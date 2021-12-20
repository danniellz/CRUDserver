package excepciones;

/**
 * Clase Exception si ocurre un error al Consultar datos
 *
 * @author Daniel Brizuela
 */
public class ReadException extends Exception {

    /**
     * Contructor vacio
     */
    public ReadException() {
    }

    /**
     * Contructor con el mensaje de error
     *
     * @param msg mensaje de error
     */
    public ReadException(String msg) {
        super(msg);
    }
}
