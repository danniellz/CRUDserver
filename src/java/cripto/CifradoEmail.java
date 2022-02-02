/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.spec.KeySpec;
import java.util.Properties;
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
    
    private static final ResourceBundle RBC = ResourceBundle.getBundle("archivos.symmetricalPrivateKey");
    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.emailCript");

    private static String clave = RBC.getString("KEY");
    
    /**
     * Cifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     * 
     * @param contra
     * @return Mensaje cifrado
     */
    public static void cifrarTexto(String email, String contra) {
        String ret = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        //String clave = RBC.getString("KEY");
        try {
            OutputStream output = new FileOutputStream("src/java/archivos/emailCript.properties");
            
            Properties prop = new Properties();
            
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedEmail = cipher.doFinal(email.getBytes());
            byte[] encodedPass = cipher.doFinal(contra.getBytes());

            //fileWriter(".\\src\\java\\archivos\\PassCifrada.dat", combined);

            
            prop.setProperty("EMAIL", byteArrayToHexString(encodedEmail));
            prop.setProperty("PASS", byteArrayToHexString(encodedPass));

            prop.store(output, null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     * 
     * @param clave La clave del usuario
     * @return 
     */
    public static String[] descifrarTexto() {
        String[] ret = null;
        
        // Fichero leído
        //byte[] fileContent = fileReader(".\\src\\java\\archivos\\PassCifrada.dat");
        
        byte[] emailContent = hexStringToByteArray(RB.getString("EMAIL"));
        byte[] passContent = hexStringToByteArray(RB.getString("PASS"));
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
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decodedEmail = cipher.doFinal(emailContent);
            byte[] decodedPass = cipher.doFinal(passContent);
            ret = new String[]{new String(decodedEmail),new String(decodedPass)};
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
     * Retorna el contenido de un fichero
     * 
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static void main(String[] args) {
        String email = "gesre.empresa@gmail.com";
        String contra = "abcd*1234";
        //CifradoEmail.cifrarTexto(email, contra);
        String[] credentials = CifradoEmail.descifrarTexto();
        System.out.println("Correo: " + credentials[0]);
        System.out.println("Contra: " + credentials[1]);
    }
}