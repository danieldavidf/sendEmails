import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

    String email = "your_email_account@gmail.com";
    String password = "your_password";

    public void send(String subject, String text, String email_to, String filePath, String fileName) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        session.setDebug(true);

        try {

        	// Construct email structure
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            Address[] toUser = InternetAddress.parse(email_to);
            message.setRecipients(Message.RecipientType.TO, toUser);

            message.setSubject(subject);
            //message.setText(text);

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent( text, "text/html; charset=utf-8" );

            DataSource source = new FileDataSource(filePath);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDisposition(Part.ATTACHMENT);
            mimeBodyPart.setDataHandler(new DataHandler(source));
            mimeBodyPart.setFileName(fileName);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);

            System.out.println("E-mail sent successfully -> " + email_to);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    

}