/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author Jonathan Viñan
 */
public class TrabajadorNoExisteException extends Exception{
    
      /**
     * Constructor vacío.
     */
    public TrabajadorNoExisteException() {
        super("El trabajador no exsite en la base de datos");
    } 
}
