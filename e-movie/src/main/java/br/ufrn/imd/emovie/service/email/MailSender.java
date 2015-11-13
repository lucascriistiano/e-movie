package br.ufrn.imd.emovie.service.email;

import java.io.IOException;
import java.util.ArrayList;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;

import br.ufrn.imd.emovie.model.User;

public class MailSender {

	private MandrillApi mandrillApi;
	private static String LOGIN_PAGE_URL = "http://localhost:8888/login.html";
	private static String SENDER_EMAIL = "lucacristiano27@gmail.com";
	private static String SENDER_NAME = "E-Movie";
	private static String LOGO_LINK = "http://postimg.org/image/jkxj9kp1f/";
	
	public MailSender() {
		mandrillApi = new MandrillApi("y4Vy2Sx3dGl7I8nzR_CRBg");
	}
	
	public void sendRegisterConfirmation(User user) throws MandrillApiError, IOException {
		MandrillMessage message = new MandrillMessage();
		message.setSubject("Confirmação de Cadastro");
		
		String htmlMessage = "";
		htmlMessage += "<img src=\"" + LOGO_LINK + "\" alt=\"E-Movie\" /><br />";
		htmlMessage += "<h1>Olá, " + user.getFirstName() + "!</h1><br />";
		
		htmlMessage += "O seu cadastro no <em>E-Movie</em> foi realizado com sucesso! Seguem abaixo as suas informações de acesso:<br /><br />";
		htmlMessage += "<strong>Nome:</strong> " + user.getName() + "<br />";
		htmlMessage += "<strong>E-mail:</strong> " + user.getEmail() + "<br />";
		htmlMessage += "<strong>Senha:</strong> " + user.getPassword() + "<br /><br />";
		
		htmlMessage += "Você pode acessar o sistema agora clicando <a href=\"" + LOGIN_PAGE_URL + "\">aqui</a>.<br />";
		htmlMessage += "Esperamos que aproveite tudo que o <em>E-Movie</em> tem a lhe oferecer.<br /><br />";
		
		htmlMessage += "<strong>Equipe E-Movie (" + SENDER_EMAIL + ")</strong><br />";
		
		message.setHtml(htmlMessage);
		message.setAutoText(true);
		message.setFromEmail(SENDER_EMAIL);
		message.setFromName(SENDER_NAME);

		// add recipients
		ArrayList<Recipient> recipients = new ArrayList<Recipient>();
		Recipient recipient = new Recipient();
		recipient.setEmail(user.getEmail());
		recipient.setName(user.getName());
		recipients.add(recipient);
		
		message.setTo(recipients);
		message.setPreserveRecipients(true);
		
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("e-movie");
		tags.add("cadastro");
		message.setTags(tags);
		
		mandrillApi.messages().send(message, false);		
	}
}