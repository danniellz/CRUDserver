/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class CifradoSimetrico {

    /**
     * Atributo estático y constante que guarda los loggers de la clase.
     */
    private static final Logger LOGGER = Logger.getLogger("seguridad.CifradoSimetrico");

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
    private final static String CLAVE = ResourceBundle.getBundle("\\archivos.private").getString("CLAVE");

    /**
     * Variable que guarda el salt.
     */
    private static byte[] salt = "esta es la salt!".getBytes();

    /**
     * Metodo que cifra las credenciales del correo mediante una clave y un salt
     * y guarda el resultado en un fichero.
     *
     * @param email El email que se quiere cifrar.
     * @param contraseña La contraseña que se quiere cifrar.
     */
    public void cifrarTextoConClavePrivada(String email, String contraseña) {
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            LOGGER.info("CifradoSimetrico: Cifrando con clave privada");

            // Crea un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Crea un Cipher con el algoritmo que se va a usar y lo inicia
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // Mensaje cifrado
            byte[] encodedEmail = cipher.doFinal(email.getBytes());
            byte[] encodedContraseña = cipher.doFinal(contraseña.getBytes());
            // Vector de inicializacion por modo CBC
            byte[] iv = cipher.getIV();

            // Guarda el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combinedEmail = concatenarArrays(iv, encodedEmail);
            byte[] combinedContraseña = concatenarArrays(iv, encodedContraseña);

            // Escribe los textos cifrados en distintos archivos.
            fileWriter(EMAIL_PATH, combinedEmail);
            fileWriter(CONTRASENA_PATH, combinedContraseña);
        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Metodo que obtiene y descifra el email mediante una clave privada.
     *
     * @return El texto descifrado.
     */
    public String descifrarEmailConClavePrivada() {
        String emailDescifrado = null;

        byte[] fileContent = readFile(EMAIL_PATH);

        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            LOGGER.info("CifradoSimetrico: Descifrando email con clave privada");

            // Crea un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Crea un Cipher con el algoritmos que se va a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            emailDescifrado = new String(decodedMessage);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.severe(e.getMessage());
        }
        return emailDescifrado;
    }

    /**
     * Metodo que obtiene y descifra la contraseña mediante una clave privada.
     *
     * @return El texto descifrado.
     */
    public String descifrarContraseñaConClavePrivada() {
        String contraseñaDescifrada = null;

        byte[] fileContent = readFile(CONTRASENA_PATH);

        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            LOGGER.info("CifradoSimetrico: Descifrando contraseña con clave privada");

            // Crea un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(CLAVE.toCharArray(), salt, 65536, 128);
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Crea un Cipher con el algoritmos que se va a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            contraseñaDescifrada = new String(decodedMessage);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOGGER.severe(e.getMessage());
        }
        return contraseñaDescifrada;
    }

    /**
     * Metodo qie concatena dos arrays.
     *
     * @param array1 Primer array.
     * @param array2 Segundo array.
     * @return La concatenacion de los dos arrays
     */
    private byte[] concatenarArrays(byte[] array1, byte[] array2) {
        LOGGER.info("CifradoSimetrico: Concatenando dos arrays");

        byte[] concatenacion = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, concatenacion, 0, array1.length);
        System.arraycopy(array2, 0, concatenacion, array1.length, array2.length);
        return concatenacion;
    }

    /**
     * Metodo que escribe en un fichero.
     *
     * @param path Path del fichero.
     * @param text Texto a escibir.
     */
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            LOGGER.info("CifradoSimetrico: Escribiendo archivo");

            fos.write(text);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Metodo que devuelve el contenido de un fichero.
     *
     * @param filePath Path del fichero.
     * @return El texto del fichero.
     */
    public byte[] readFile(String filePath) {
        byte[] keyBytes = null;
        try {
            LOGGER.info("CifradoSimetrico: Leyendo archivo");

            InputStream inputStream = Hash.class.getResourceAsStream(filePath);///////////FUNCIONA con el path en un archivo de propiedades y coger el properties file
            //InputStream inputStream = CifradoAsimetrico.class.getResourceAsStream("/archivos/ComicSansAsimetricPrivate.key");/////Funciona con fichero en carpeta archivos
            //InputStream inputStream = CifradoAsimetrico.class.getResourceAsStream("ComicSansAsimetricPrivate.key");///////////////// FUNCIONA con el fichero en la carpeta seguridad
            keyBytes = new byte[inputStream.available()];
            inputStream.read(keyBytes);
        } catch (IOException ex) {
            LOGGER.severe(ex.getMessage());
        }
        return keyBytes;
    }
}
