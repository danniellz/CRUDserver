package cripto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.util.logging.Logger;

/**
 * Clase para leer la clave publica
 *
 * @author Daniel Brizuela
 */
public class PublicKeyReader {

    /**
     * LOGGER
     */
    private static final Logger LOG = Logger.getLogger(PublicKeyReader.class.getName());

    /**
     * Lee la clave publica ( igual que leer un fichero) solo que se utiliza la
     * clase X509EncodedKeySpec para descifrar la Key
     *
     * Javadoc X509EncodedKeySpec:
     * https://docs.oracle.com/javase/7/docs/api/java/security/spec/X509EncodedKeySpec.html
     *
     * @param filename en este param especificamos la Ruta en el que esta la Key
     * @return devuelve la Key
     * @throws Exception
     */
    public static PublicKey get(String filename) throws Exception {
        LOG.info("GESREserver/PublicKeyReader: Leyendo Clave Pública...");
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) file.length()];
        dis.readFully(keyBytes);
        dis.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
