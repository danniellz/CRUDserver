/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author JonY
 */
public class AutenticacionFallidaException extends Exception {

    /**
     * Excepcion de contraseña incorrecta
     */
    public AutenticacionFallidaException() {
        super("Contraseña incorrecta");
    }
}
