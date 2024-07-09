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
	

	private static final long serialVersionUID = 1L;
	// Autenticación
    final String username = "cincobitutec@gmail.com";
    final String password = "mzrd gmnt ufaw gklz"; // deberia de estar encriptada
	
    
	public boolean enviarMail(String subject, String bodyText,String mail) {
		
		boolean resultado = false;
		
		// Propiedades del servidor de correo
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587"); // Puerto SMTP para STARTTLS
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true"); // Habilitar STARTTLS
		
     // Obtener la sesión de correo
        Session session = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje para enviar
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(mail)
            );
            message.setSubject(subject);
            message.setText(bodyText);

            // Enviar el correo
            Transport.send(message);
            System.out.println("Correo enviado con éxito");
            resultado = true;
            
            return resultado;

        } catch (MessagingException e) {
        	System.out.println("No se envio Correo");
            throw new RuntimeException(e);
        }
		
	}
	
	

}
