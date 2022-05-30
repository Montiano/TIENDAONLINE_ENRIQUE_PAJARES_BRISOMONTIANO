package com.tienda.online.utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMail {
	
	//static String destinatario = "kike_montiano@hotmail.com";
	
	/**
	 * Método para enviar un email al usuario
	 * @param destinatario El destinatario
	 * @param texto El texto a enviar
	 */
	public static void enviarMail(String destinatario, String texto) {
		
	try {
		// Propiedades de la conexion
		Properties prop = new Properties();
		// Nombre del servidor de salida
		prop.setProperty("mail.smtp.host", "smtp.gmail.com");
		// Habilitamos TLS
		prop.setProperty("mail.smtp.starttls.enable", "true");
		// Indicamos el puerto
		prop.setProperty("mail.smtp.port", "587");
		// Indicamos el usuario
		prop.setProperty("mail.smtp.user", "kikemontiano17@gmail.com");
		// Indicamos que requiere autenticación
		prop.setProperty("mail.smtp.auth", "true");

		// Creamos un objeto sesion
		Session sesion = Session.getDefaultInstance(prop);
		//TODO
		sesion.setDebug(true);
		// Creamos un objeto mensaje a traves de la sesion
		MimeMessage mensaje = new MimeMessage(sesion);
		
		// Indicamos la cuenta desde la que se va a enviar
		mensaje.setFrom(new InternetAddress("kikemontiano17@gmail.com"));

		// Añadimos el recipiente al mensaje al que va a ir dirigido el mensaje
		mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));

		// Creamos el asunto del mensaje
		mensaje.setSubject("Confirmación de tu pedido de la Tienda Online de Enrique Pajares");

		// Creamos el cuerpo del mensaje
		mensaje.setText(texto);

//		mensaje.setText(
//				"Esto es una prueba <br> con <b>JavaMail</b>",
//				"ISO-8859-1",
//				"html");
		
		// Utilizamos un objeto transport para hacer el envio indicando el protocolo
		Transport t = sesion.getTransport("smtp");
		// Hacemos la conexion
		t.connect("kikemontiano17@gmail.com", "jbfwfojxzhjzrxth");
		// Enviamos el mensaje
		t.sendMessage(mensaje, mensaje.getAllRecipients());

		// Cerramos la conexion
		t.close();

	} catch (AddressException ex) {
		Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, ex);
	} catch (MessagingException ex) {
		Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, ex);
	}

}
}
