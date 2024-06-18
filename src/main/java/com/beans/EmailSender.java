package com.beans;

import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.mail.*;
import javax.mail.internet.*;


@Named(value = "emailSender") // JEE8
@SessionScoped
public class EmailSender implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Autenticación
    final String username = "cincobitutec@gmail.com";
    final String password = "mzrd gmnt ufaw gklz"; // deberia de estar encriptada
	
    
	public void enviarMail(String subject, String bodyText,String mail) {
		
		// Propiedades del servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Puerto SMTP para STARTTLS
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Habilitar STARTTLS
		
     // Obtener la sesión de correo
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear un mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cincobitutec@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject(subject);
            message.setText(bodyText);

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado con éxito");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
		
	}
	
	

}
