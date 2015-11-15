package br.ufrn.imd.emovie.service.email;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;

import br.ufrn.imd.emovie.model.Exhibition;
import br.ufrn.imd.emovie.model.Ticket;
import br.ufrn.imd.emovie.model.User;

public class MailSender {

	private static String LOGIN_PAGE_URL = "http://localhost:8888/login.php";
	private static String SENDER_EMAIL = "lucacristiano27@gmail.com";
	private static String SENDER_NAME = "E-Movie";
	private static String LOGO_LINK = "http://i.imgur.com/bjijalG.png";
	
	private static MailSender mailSender;
	
	private MandrillApi mandrillApi;
	
	private MailSender() {
		mandrillApi = new MandrillApi("y4Vy2Sx3dGl7I8nzR_CRBg");
	}
	
	public static MailSender getInstance() {
		if(mailSender == null) {
			mailSender = new MailSender();
		}
		
		return mailSender;
	}
	
	public void sendTicket(Ticket ticket) throws MandrillApiError, IOException {
		MandrillMessage message = new MandrillMessage();
		message.setSubject("Ticket Gerado");
		
		String htmlMessage = "";
		htmlMessage += "<img src=\"" + LOGO_LINK + "\" alt=\"E-Movie\" /><br />";
		htmlMessage += "<h1>Olá, " + ticket.getUser().getName() + "!</h1><br />";
		
		Exhibition exhibition = ticket.getExhibition();
		String ticketDayWeek = getDayWeek(exhibition.getSession().getDayWeek());
		DateFormat format = new SimpleDateFormat("HH:mm");
		Date ticketHour = exhibition.getSession().getHour();
		String formattedTicketHour = format.format(ticketHour); 
		
		htmlMessage += "Aqui está seu ticket gerado em <em>E-Movie</em>:<br /><br />";
		htmlMessage += "<strong>Ticket:</strong> " + ticket.getToken() + "<br />";
		htmlMessage += "<strong>Filme:</strong> " + exhibition.getMovie().getName() + "<br />";
		htmlMessage += "<strong>Sala:</strong> " + exhibition.getRoom().getId() + "<br /><br />";
		htmlMessage += "<strong>Cadeira:</strong> " + ticket.getChairNumber() + "<br /><br />";
		htmlMessage += "<strong>Horário:</strong> " + ticketDayWeek + ", " + formattedTicketHour + "<br /><br />";
		
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
		recipient.setEmail(ticket.getUser().getEmail());
		recipient.setName(ticket.getUser().getName());
		recipients.add(recipient);
		
		message.setTo(recipients);
		message.setPreserveRecipients(true);
		
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("e-movie");
		tags.add("ticket");
		message.setTags(tags);
		
		mandrillApi.messages().send(message, false);
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
	
	private String getDayWeek(Integer dayWeek) {
		String[] daysWeek = new String[] {"Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};
		return daysWeek[dayWeek];
	}
}