package cripto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 * Clase que genera las key publica y privada
 *
 * @author Daniel Brizuela, Aritz Arrieta
 */
public class KeyGenerator {

    /**
     * LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(KeyGenerator.class.getName());
    private static final String rutaAbsoluta = new File("").getAbsolutePath();
    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.rutas");

    private static Cipher cipher;

    public void generarClavePrivadaYPublica() {
        //KeyPairGenerator puede generar las 2 claves para cifrar a la vez.
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024); // Inicializamos el tamaño a 1024 bits
            //Se guardan las claves generadas F
            KeyPair keypair = generator.generateKeyPair();
            PublicKey publicKey = keypair.getPublic(); // Clave Pública
            PrivateKey privateKey = keypair.getPrivate(); // Clave Privada

            // Se generan los archivos que van a tener las key 
            //para el cifradoel order al generar las claves no importa (key publica y privada o * viceversa)
            // Publica
            LOGGER.info("Generando clave publica");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream fileOutputStream = new FileOutputStream(rutaAbsoluta + RB.getString("PUBLIC_KEY"));
            fileOutputStream.write(x509EncodedKeySpec.getEncoded());
            fileOutputStream.close();

            // Privada
            LOGGER.info("Generando clave privada");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            fileOutputStream = new FileOutputStream(rutaAbsoluta + RB.getString("PRIVATE_KEY"));
            fileOutputStream.write(pKCS8EncodedKeySpec.getEncoded());
            fileOutputStream.close();
            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        LOGGER.info("Iniciando: Generando claves");
        KeyGenerator generarClaves = new KeyGenerator();
        generarClaves.generarClavePrivadaYPublica();
        LOGGER.info("Las claves se han generado correctamente");
    }
}
