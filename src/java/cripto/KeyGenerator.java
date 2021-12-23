package cripto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que genera las key publica y privada
 *
 * @author Daniel Brizuela, Aritz Arrieta
 */
public class KeyGenerator {

    /**
     * LOGGER
     */
    private static final Logger LOG = Logger.getLogger(KeyGenerator.class.getName());
    private static final String rutaAbsoluta = new File("").getAbsolutePath();
    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.rutas");

    public static void main(String[] args) {
        LOG.info("GESREserver/KeyGenerator: Generando claves...");
        try {
            //KeyPairGenerator puede generar las 2 claves para cifrar a la vez.
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            //Se guardan las claves generadas
            KeyPair keyPair = keyPairGenerator.genKeyPair();

            /**
             * Key Publica
             *
             * Se generan los archivos que van a tener las key para el cifrado
             * el order al generar las claves no importa (key publica y privada o
             * viceversa)
             */
            //Ruta de guardado de la clave
            LOG.info("GESREserver/KeyGenerator: Guardando PublicKey...");
            String publicKeyFilename = rutaAbsoluta+RB.getString("PUBLIC_KEY");
            //Contenido que va a ir dentro del archivo
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            FileOutputStream fos = new FileOutputStream(publicKeyFilename);
            fos.write(publicKeyBytes);
            fos.close();

            //Key privada
            //Ruta de guardado de la clave
            LOG.info("GESREserver/KeyGenerator: Guardando PrivateKey...");
            String privateKeyFilename = rutaAbsoluta+RB.getString("PRIVATE_KEY");
            //Contenido que va a ir dentro del archivo
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            fos = new FileOutputStream(privateKeyFilename);
            fos.write(privateKeyBytes);
            fos.close();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
