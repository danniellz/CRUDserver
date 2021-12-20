package cripto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Clase de cifrado hash con algoritmo SHA-1, cifra la contraseña y se guarda en
 * la base de datos
 *
 * @author Daniel Brizuela
 */
public class Hash {

    /**
     * LOGGER
     */
    private static final Logger LOG = Logger.getLogger(Hash.class.getName());

    /**
     * Metodo que mediante el MessageDigest cifra la contraseña del usuario.
     *
     * @param contra La contraseña del usuario.
     * @return contraseña con hash hexadecimal.
     */
    public String cifrarTextoEnHash(String contra) {
        MessageDigest messageDigest;
        try {
            LOG.info("GESREserver/Hash: Cifrando clave");
            //Obtener una instancia de MessageDigest que usa SHA
            messageDigest = MessageDigest.getInstance("SHA-512");
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
            LOG.severe(e.getMessage());
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
        LOG.info("GESREserver/Hash: Convirtiendo bytes a hexadecimal...");

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
}
