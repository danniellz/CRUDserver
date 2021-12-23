package cripto;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Clase para leer la clave publica
 *
 * @author Daniel Brizuela, Aritz Arrieta
 */
public class PublicKeyReader {

    /**
     * LOGGER
     */
    private static final Logger LOG = Logger.getLogger(PublicKeyReader.class.getName());
    private static final String rutaAbsoluta = new File("").getAbsolutePath();
    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.rutas");

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
    public static PublicKey get() throws Exception {
        LOG.info("GESREserver/PublicKeyReader: Leyendo Clave Pública...");
        System.out.println(rutaAbsoluta);
        File file = new File(RB.getString("PUBLIC_KEY"));
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
