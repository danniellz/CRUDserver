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
import java.security.spec.KeySpec;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
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
public class CifradoEmail{

    // Fíjate que el String es de exactamente 16 bytes
    private static byte[] salt = "esta es la salt!".getBytes(); 

    /**
     * Cifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     * 
     * @param clave   La clave del usuario
     * @param mensaje El mensaje a cifrar
     * @return Mensaje cifrado
     */
    public String cifrarTexto(String clave, String mensaje) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {

            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes()); // Mensaje cifrado !!!
        

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            byte[] combined = encodedMessage;

            fileWriter("C:\\Mikel\\PassCifrada.dat", combined);

            ret = new String(encodedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     * 
     * @param clave La clave del usuario
     */
    private String descifrarTexto(String clave) {
        String ret = null;

        // Fichero leído
        byte[] fileContent = fileReader("C:\\Mikel\\PassCifrada.dat");
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16)); // La IV est� aqu�
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedMessage = cipher.doFinal(fileContent);
            ret = new String(decodedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Retorna una concatenaci�n de ambos arrays
     * 
     * @param array1
     * @param array2
     * @return Concatenaci�n de ambos arrays
     */
    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
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
     * Retorna el contenido de un fichero
     * 
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

//private static final ResourceBundle RBP = ResourceBundle.getBundle("ejemploSimetrico.Contra");
private static final ResourceBundle RBC = ResourceBundle.getBundle("archivos.symmetricalPrivateKey");

    public static void main(String[] args) {
        String clave = "abcd*1234";
        String contra = RBC.getString("KEY");
        CifradoEmail cifradoEmail = new CifradoEmail();
        String mensajeCifrado = cifradoEmail.cifrarTexto(contra, clave);
        System.out.println("Cifrado! -> " + mensajeCifrado);
        System.out.println("-----------");
        System.out.println("Descifrado! -> " + cifradoEmail.descifrarTexto(contra));
        System.out.println("-----------");
    }
}