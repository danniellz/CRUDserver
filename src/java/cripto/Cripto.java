/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cripto;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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
    private final static String PRIVATE_KEY_PATH = ResourceBundle.getBundle("/archivos.rutas").getString("PRIVATE_KEY");


    public  String descifrarConClavePrivada(String contraseñaHexadecimal) {
        byte[] decodedMessage = null;
        try {
            LOG.info("CifradoAsimetrico: Descifrando con clave privada");

            byte fileKey[] = getFileKey(PRIVATE_KEY_PATH);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decodedMessage = cipher.doFinal(HexadecimalToByte(contraseñaHexadecimal));

        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            LOG.severe(e.getMessage());
        }
        return new String(decodedMessage);
    }

    public byte[] getFileKey(String keyFilePath) {
        byte[] keyBytes = null;
        try {
            LOG.info("CifradoAsimetrico: Leyendo archivo");

            InputStream inputStream = Cripto.class.getResourceAsStream(keyFilePath);
            keyBytes = new byte[inputStream.available()];
            inputStream.read(keyBytes);
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
        return keyBytes;
    }

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
