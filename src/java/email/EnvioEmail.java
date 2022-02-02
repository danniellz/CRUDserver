
package email;


import cripto.CifradoEmail;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase para enviar un mail
 *
 * @author Daniel Brizuela, Mikel Matilla
 */

public class EnvioEmail {
    
    private static final ResourceBundle RB = ResourceBundle.getBundle("archivos.email");
    
    private static final String[] credentials = CifradoEmail.descifrarTexto();
    
    /**
     * Metodo para enviar email con el reset y cambio de contraseña
     * 
     * @param receptor Direccion de correo que recive el correo
     * @param asunto Asunto del correo
     * @param cuerpo Cuerpo del mennsaje 
     * @throws AddressException Excepcion de la direccion de correo
     * @throws Messaging Exception Excepcion en el mensaje
     */
    public static void enviarMail (String receptor, String asunto, String cuerpo) throws AddressException, MessagingException {

        //Propiedades del Mail
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", RB.getString("HOST"));
        properties.put("mail.smtp.port", RB.getString("PORT"));
        properties.put("mail.smtp.ssl.trust", RB.getString("HOST"));
        properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.imap.partialfetch", false);

        //Autenticacion
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(credentials[0], credentials[1]);
            }
        });
        
        //Preparar mensaje
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(credentials[0]));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receptor));

        msg.setSubject(asunto);

        
        //Cuerpo del mensaje
        Multipart multipart = new MimeMultipart();
        
        //Mensaje (tambien puede ser un archivo)
        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        mimeBodyPart.setContent(cuerpo, "text/html");

        multipart.addBodyPart(mimeBodyPart);
        
        //Agregar las partes al MIME message
        msg.setContent(multipart);
        
        //Enviar el mensaje
        Transport.send(msg);
        
    }

}
