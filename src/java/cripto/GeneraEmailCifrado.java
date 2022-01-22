/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

/**
 *
 * @author Mikel, Jonathan
 */
public class GeneraEmailCifrado {

    /**
     * Metodo principal que se usa para ejecutar el metodo
     * "cifrarTextoConClavePrivada" de la clase "CifradoSimetrico" y los metodos
     * que los descifran.
     *
     * @param args
     */
    public static void main(String[] args) {

        CifradoEmail cifradoEmail = new CifradoEmail();
        //cifradoEmail.cifrarTextoConClavePrivada("gesre.empresa@gmail.com", "gesre");

        // Descifra las credenciales de la cuenta de correo String
        String textoDescifrado = cifradoEmail.descifrarEmailConClavePrivada();
        System.out.println("Descifrado Email! -> " + textoDescifrado);
        System.out.println("-----------");
        String textoDescifradoContra = cifradoEmail.descifrarContraseñaConClavePrivada();
        System.out.println("Descifrado contraseña! -> " + cifradoEmail.descifrarContraseñaConClavePrivada());
        System.out.println("-----------");
    }
}
