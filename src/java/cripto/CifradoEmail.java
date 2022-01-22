/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <b>Criptografía Simétrica (Clave Secreta)</b> <br/>
 * <br/>
 *
 * Esta clase permite cifrar un texto mediante una <b>clave secreta</b> y lo
 * guarda en un fichero. La única forma de descifrar el texto es mediante dicha
 * clave, que tanto el <u>emisor</u> como el <u>receptor</u> la deben conocer.
 *
 * En este caso vamos a utilizar:
 * <ul>
 * <li>El algoritmo AES</li>
 * <li>El modo CBC: Existen dos, el ECB que es sencillo, y el CBC que necesita
 * un vector de inicialización(IV)</li>
 * <li>El padding PKCS5Padding (128): Si el mensaje no es múltiplo de la
 * longitud del algoritmo se necesita un relleno.</li>
 * </ul>
 * AES solo admite <b>tamaños de clave</b> de 16, 24 o 32 bytes. Se debe
 * proporcionar exactamente ese tamaño de clave o usar una
 * <b>"salt"(Semilla)</b>. En criptografía, un salt es un dato aleatorio que se
 * usa como una entrada adicional de cifrado. En este caso, vamos a utilizar
 * salt para crear una clave de exactamente 16 bytes. <br/>
 * <br/>
 * Generalmente un salt se genera aleatoriamente cuando creas la clave, así que
 * <u>necesitas guardar</u> la clave y su salt para poder cifrar y descifrar.
 */
public class CifradoEmail {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("seguridad.Cryto");

    /**
     * Variable que guarda el salt. Fíjate que el String es de exactamente 16
     * bytes
     */
    private static byte[] salt = "esta es la salt!".getBytes();

    /**
     * Atributo que guarda la ruta del email cifrado del archivo de propiedades.
     */
    private final static String EMAIL_PATH = ResourceBundle.getBundle("archivos.rutas").getString("EMAIL_EMAIL");
    /**
     * Atributo que guarda la ruta de la contraseña cifrada privada del archivo
     * de propiedades.
     */
    private final static String CONTRASENA_PATH = ResourceBundle.getBundle("archivos.rutas").getString("EMAIL_CONTRASENA");

    /**
     * Atributo que coge la clave privada del archivo de propiedades.
     */
    private final static String CLAVE = ResourceBundle.getBundle("archivos.private").getString("KEY");

    public String cifrarTexto(String contra) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        //String clave = RBC.getString("KEY");
        try {

            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = cipher.doFinal(contra.getBytes()); // Mensaje cifrado !!!

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = encodedMessage;

            fileWriter(".\\src\\java\\archivos\\PassCifrada.dat", combined);

            ret = new String(encodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y
     * lo retorna
     *
     * @param clave La clave del usuario
     * @return
     */
    public static String descifrarTexto() {
        String ret = null;

        // Fichero leído
        byte[] fileContent = fileReader(".\\src\\java\\archivos\\PassCifrada.dat");
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedMessage = cipher.doFinal(fileContent);
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Escribe un fichero
     *
     * @param path Path del fichero
     * @param text Texto a escibir
     */
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna el contenido de un fichero /**
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private static byte[] fileReader(String filePath) {
        byte[] keyBytes = null;
        File file = new File(filePath);
        try {
            keyBytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyBytes;
    }

    /**
     *
     * @param email
     * @param contraseña
     * @throws InvalidKeySpecException
     */
    public void cifrarTextoConClavePrivada(String email, String contraseña) {

        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        //String clave = RBC.getString("KEY");
        try {
            LOGGER.info("CifradoSimetrico: Cifrando con clave privada");
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] encodedEmail = cipher.doFinal(email.getBytes()); // Mensaje cifrado !!!
            byte[] encodedContraseña = cipher.doFinal(contraseña.getBytes());

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combinedEmail = encodedEmail;
            byte[] combinedContraseña = encodedContraseña;
            // Escribe los textos cifrados en distintos archivos.
            fileWriter(EMAIL_PATH, combinedEmail);
            fileWriter(CONTRASENA_PATH, combinedContraseña);

        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Cifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     *
     * @param contra
     * @return Mensaje cifrado
     */
    public String cifrarTextoEmailContraseña(String contra) {
        LOGGER.info("CREANDO CIFRADOD EMAIL_CONTRASENA");
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        //String clave = RBC.getString("KEY");
        try {

            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = cipher.doFinal(contra.getBytes()); // Mensaje cifrado !!!

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = encodedMessage;

            // fileWriter(".\\src\\java\\archivos\\PassCifrada.dat", combined);
            fileWriter(CONTRASENA_PATH, combined);
            ret = new String(encodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String descifrarEmailConClavePrivada() {
        String ret = null;

        // Fichero leído
        //   byte[] fileContent = fileReader(".\\src\\java\\archivos\\PassCifrada.dat");
        byte[] fileContent = fileReader(EMAIL_PATH);
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedMessage = cipher.doFinal(fileContent);
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String descifrarContraseñaConClavePrivada() {
        String ret = null;

        // Fichero leído
        byte[] fileContent = fileReader(CONTRASENA_PATH);
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedMessage = cipher.doFinal(fileContent);
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
