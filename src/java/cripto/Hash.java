package cripto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase de cifrado hash con algoritmo SHA-1, cifra la contraseña y se guarda en
 * la base de datos
 *
 * @author Daniel Brizuela y Jonathan Viñan
 */
public class Hash {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(Hash.class.getName());
    private static PrivateKey privateKey;
    private static Cipher cipher;

    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.rutas");
    private final static String PUBLIC_KEY = RB.getString("PUBLIC_KEY");
    private final static String PRIVATE_KEY = RB.getString("PRIVATE_KEY");

    /**
     * Metodo que mediante el MessageDigest cifra la contraseña del usuario.
     *
     * @param contra La contraseña del usuario.
     * @return contraseña con hash hexadecimal.
     */
    public String cifrarTextoEnHash(String contra) {
        MessageDigest messageDigest;
        try {
            LOGGER.info("GESREserver/Hash: Cifrando clave");
            //Obtener una instancia de MessageDigest que usa SHA
            messageDigest = MessageDigest.getInstance("SHA-1");
            //Convierte la contraseña en un array de bytes 
            byte dataBytes[] = contra.getBytes();
            //Actualiza el MessageDigest con el array de bytes 
            messageDigest.update(dataBytes);
            //Calcula el resumen (función digest)
            byte resumen[] = messageDigest.digest();
            //contaseña hasheada
            contra = byteToHexadecimal(resumen);

            System.out.println("Contraseña Hasheada: " + contra);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe(e.getMessage());
        }
        return contra;
    }

    /**
     * Metodo que convierte un array de bytes en un string hexadecimal.
     *
     * @param bytes Array de bytes
     * @return devuelve un String hexadecimal
     */
    private String byteToHexadecimal(byte[] bytes) {
        LOGGER.info("GESREserver/Hash: Convirtiendo bytes a hexadecimal...");

        String HEX = "";
        for (int i = 0; i < bytes.length; i++) {
            String h = Integer.toHexString(bytes[i] & 0xFF);
            if (h.length() == 1) {
                HEX += "0";
            }
            HEX += h;
        }
        return HEX.toUpperCase();
    }

    /**
     * Transformar cadena hexadecimal en un array de bytes
     *
     * @param s
     * @return
     */
    private static byte[] hexStringToByteArray(String contrasenia) {
        if (contrasenia.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[contrasenia.length() / 2];
        for (int i = 0; i < contrasenia.length(); i += 2) {
            bytes[i / 2] = hexToByte(contrasenia.substring(i, i + 2));
        }
        return bytes;
    }

    /**
     * Hexadecimal a byte
     *
     * @param hexString
     * @return
     */
    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    /**
     * Hexadecimal a caracter
     *
     * @param hexChar
     * @return valor
     */
    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }

    /**
     * Contraseña encriptada con clave pública y convertida a hexadecimal
     *
     * @param contrasenia
     * @return contrasenia desincriptada
     */
    public static String desencriptarContrasenia(String contrasenia) {
        String pass = "";
        try {
            // byte fileKey[] = fileReader("Private.key");
            byte fileKey[] = getPrivateFileKey();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytesDesencriptados = cipher.doFinal(hexStringToByteArray(contrasenia));
            pass = new String(bytesDesencriptados);

        } catch (Exception ex) {
            LOGGER.severe("Error al desencriptar con clave privada");
        }
        return pass;
    }

    /**
     * Cifrado Sha1 para poder almacenar en la bd
     *
     * @param contrasenia
     * @return contrasenia cifrada con SHA1
     */
    public static String cifradoSha(String contrasenia) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(contrasenia.getBytes());
            byte[] db = md.digest();
            hash = DatatypeConverter.printHexBinary(db).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Error al cifrar SHA1 " + e.getMessage());
        }
        return hash;
    }

    /**
     * Obtener clave privada
     *
     * @return
     * @throws IOException
     */
    public static byte[] getPrivateFileKey() throws IOException {

        InputStream keyfis = Hash.class.getClassLoader()
                .getResourceAsStream("./archivos/privateKeyFile.key");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        // read bytes from the input stream and store them in buffer
        while ((len = keyfis.read(buffer)) != -1) {
            // write bytes from the buffer into output stream
            os.write(buffer, 0, len);
        }
        keyfis.close();
        return os.toByteArray();
    }

}
