package cripto;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 * Clase de Cifrado
 *
 * @author Daniel Brizuela, Aritz Arrieta
 */
public class Cripto {

    Cipher cipher;
    byte[] cipherText;
    /**
     * LOGGER
     */
    private static final Logger LOG = Logger.getLogger(Cripto.class.getName());

    /**
     * Metodo para cifrar la contraseña
     *
     * @param contra contraseña
     * @throws Exception
     */
    public void cifrar(String contra) throws Exception {
        LOG.info("GESREserver/Cripto: Cifrando contraseña...");
        //Modo de cifrado, Crea una instancia del objeto Cipher como un cifrado RSA con el modo de operación ECB y el esquema de relleno
        cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        //Keys a utilizar
        RSAPublicKey pubKey = (RSAPublicKey) PublicKeyReader.get("./publicKeyFile.key");
        RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get("./privateKeyFile.key");
        //Iniciar Cipher en modo Encriptacion con clave publica
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        //Cifrar
        cipherText = cipher.doFinal(contra.getBytes());
        // Mostrar Cifrado
        System.out.println("Contraseña Cifrada: " + new String(cipherText));

    }

    /**
     * Metodo para descifrar la contraseña
     *
     * @param contra contraseña
     * @throws Exception
     */
    public void descifrar(String contra) throws Exception {
        LOG.info("GESREserver/Cripto: Descifrando contraseña...");
        cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get("./privateKeyFile.key");

        // Iniciar descifrado con la clave pirvada 
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] contrasena = cipher.doFinal(cipherText);
        System.out.println("Contraseña Descifrada: " + new String(contrasena));
    }
}
