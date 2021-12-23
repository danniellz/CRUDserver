package cripto;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ResourceBundle;
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
    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.rutas");
    private final static String PUBLIC_KEY = RB.getString("PUBLIC_KEY");
    private final static String PRIVATE_KEY = RB.getString("PRIVATE_KEY");

    /**
     * Metodo para cifrar la contraseña
     *
     * @param contra contraseña
     * @throws Exception
     */
    public String cifrar(String contra) throws Exception {
        LOG.info("GESREserver/Cripto: Cifrando contraseña...");
        //Modo de cifrado, Crea una instancia del objeto Cipher como un cifrado RSA con el modo de operación ECB y el esquema de relleno
        cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        //Keys a utilizar
        RSAPublicKey pubKey = (RSAPublicKey) PublicKeyReader.get();
        RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get();
        //Iniciar Cipher en modo Encriptacion con clave publica
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        //Cifrar
        cipherText = cipher.doFinal(contra.getBytes());
        // Mostrar Cifrado
        System.out.println("Contraseña Cifrada: " + new String(cipherText));
        return byteToHexadecimal(cipherText);

    }

    /**
     * Metodo para descifrar la contraseña
     *
     * @param contraHex contraseña con hash
     * @throws Exception
     * @return 
     */
    public String descifrar(String contraHex) throws Exception {
        LOG.info("GESREserver/Cripto: Descifrando contraseña...");
        cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get();

        // Iniciar descifrado con la clave privada 
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] contraDescifrada = cipher.doFinal(HexadecimalToByte(contraHex));
        System.out.println("Contraseña Descifrada: " + new String(contraDescifrada));
        return new String(contraDescifrada);
    }
    
    /**
     * Metodo que convierte un array de bytes en un string hexadecimal.
     *
     * @param bytes Array de bytes
     * @return devuelve un String hexadecimal
     */
    private String byteToHexadecimal(byte[] bytes) {
        LOG.info("CifradoAsimetrico: Convirtiendo bytes a hexadecimal");

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
     * Metodo que convierte un string con valor hexadecimal a un array de bytes.
     *
     * @param hexadecimal string hexadecimal
     * @return devuelve un array de bytes
     */
    private byte[] HexadecimalToByte(String hexadecimal) {
        LOG.info("CifradoAsimetrico: Convirtiendo hexadecimal a bytes");
        int length = hexadecimal.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexadecimal.charAt(i), 16) << 4)
                    + Character.digit(hexadecimal.charAt(i + 1), 16));
        }
        return data;
    }
}
