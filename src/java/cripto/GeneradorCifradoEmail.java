/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;



/**
 * Clase que se usa para cifrar las credenciales del email y comprobar que se
 * cifran y descifran bien.
 *
 * @author Jonathan
 */
public class GeneradorCifradoEmail {

    /**
     * Metodo principal que se usa para ejecutar el metodo
     * "cifrarTextoConClavePrivada" de la clase "CifradoSimetrico" y los metodos
     * que los descifran.
     *
     * @param args Array de strings
     */
    public static void main(String[] args) {
        // Cifra las credenciales de la cuenta de correo
        CifradoSimetrico cifradoSimetrico = new CifradoSimetrico();
        //cifradoSimetrico.cifrarTextoConClavePrivada("info.ComicSans@gmail.com", "abcd*1234");

        // Descifra las credenciales de la cuenta de correo
        String textoDescifrado = cifradoSimetrico.descifrarEmailConClavePrivada();
        System.out.println("EMAIL Descifrado: " + textoDescifrado);
        System.out.println("-----------");
        textoDescifrado = cifradoSimetrico.descifrarContraseñaConClavePrivada();
        System.out.println("Contraseña Descifrada: " + textoDescifrado);

        //Mandar mail
        /*Mail mail = new Mail();
        mail.enviarMail("naranguren3@gmail.com");*/
    }
}
