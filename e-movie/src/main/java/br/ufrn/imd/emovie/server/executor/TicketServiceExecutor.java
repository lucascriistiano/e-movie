package br.ufrn.imd.emovie.server.executor;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Exhibition;
import br.ufrn.imd.emovie.model.PurchaseLocation;
import br.ufrn.imd.emovie.model.Ticket;
import br.ufrn.imd.emovie.model.User;
import br.ufrn.imd.emovie.service.ExhibitionService;
import br.ufrn.imd.emovie.service.TicketService;
import br.ufrn.imd.emovie.service.UserService;
import br.ufrn.imd.emovie.service.email.MailSender;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 *
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class TicketServiceExecutor extends ServiceExecutorTemplate {

	private static final String RETRIEVE_TOKEN = "retrieveToken";

	private TicketService ticketService;
	private UserService userService;
	private ExhibitionService exhibitionService;
	private MailSender mailSender;

	public TicketServiceExecutor() {
		ticketService = TicketService.getInstance();
		userService = UserService.getInstance();
		exhibitionService = ExhibitionService.getInstance();
		mailSender = MailSender.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Ticket ticket = ticketService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(ticket); // returns empty string if
												// ticket == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Ticket> tickets = ticketService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(tickets); // returns empty string if
													// movie == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		// TODO
		String operation = (String) requestParams.get("operation");
		if (operation.equals(RETRIEVE_TOKEN)) {
			String token = (String) requestParams.get("token");
			Ticket ticket = ticketService.getByToken(token);

			Gson gson = new Gson();
			String jsonTicket = gson.toJson(ticket);
			return jsonTicket;
		}

		return "";
	}

	@Override
	public boolean processPostCreate(Map<String, Object> requestParams) {
		Integer idExhibition = Integer.parseInt((String) requestParams.get("id_exhibition"));
		String chairNum = (String) requestParams.get("chair_num");
		String email = (String) requestParams.get("email");
		String password = (String) requestParams.get("password");
		String strPurchaseLocation = (String) requestParams.get("purchase_location");
		
		PurchaseLocation purchaseLocation;
		if(strPurchaseLocation.equals("local")) {
			purchaseLocation = PurchaseLocation.LOCAL;
		} else if(strPurchaseLocation.equals("internet")) {
			purchaseLocation = PurchaseLocation.INTERNET;
		} else {
			throw new IllegalArgumentException("Invalid purchase location value '" + strPurchaseLocation + "'");
		}
		
		try {
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);

			User foundUser = userService.checkLogin(user);
			if (foundUser != null) {
				Exhibition exhibition = exhibitionService.find(idExhibition);

				Ticket ticket = new Ticket(exhibition, chairNum, foundUser, purchaseLocation, new Date());
				ticketService.create(ticket);
				
				if(purchaseLocation == PurchaseLocation.INTERNET) {
					sendEmail(ticket);
				}
				return true;
			} else {
				return false;
			}
		} catch (ServiceException | DaoException | MandrillApiError | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private void sendEmail(Ticket ticket) throws DaoException, MandrillApiError, IOException {
		ticket.setUser(userService.find(ticket.getUser().getId()));
		ticket.setExhibition(exhibitionService.find(ticket.getExhibition().getId()));
		mailSender.sendTicket(ticket);
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		Integer idExhibition = Integer.parseInt((String) requestParams.get("id_exhibition"));
		String chairNum = (String) requestParams.get("chair_num");
		String email = (String) requestParams.get("email");
		String password = (String) requestParams.get("password");
		String strPurchaseLocation = (String) requestParams.get("purchase_location");
		
		PurchaseLocation purchaseLocation;
		if(strPurchaseLocation.equals("local")) {
			purchaseLocation = PurchaseLocation.LOCAL;
		} else if(strPurchaseLocation.equals("internet")) {
			purchaseLocation = PurchaseLocation.INTERNET;
		} else {
			throw new IllegalArgumentException("Invalid purchase location value '" + strPurchaseLocation + "'");
		}

		try {
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);

			User foundUser = userService.checkLogin(user);
			if (foundUser != null) {
				Exhibition exhibition = exhibitionService.find(idExhibition);

				Ticket foundTicket = ticketService.find(id);
				
				Ticket ticket = new Ticket(id, exhibition, chairNum, foundUser, purchaseLocation, new Date());
				ticket.setToken(foundTicket.getToken());
				
				ticketService.update(ticket);
				
				if(purchaseLocation == PurchaseLocation.INTERNET) {
					sendEmail(ticket);
				}
				return true;
			} else {
				return false;
			}
		} catch (ServiceException | DaoException | MandrillApiError | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostDelete(Map<String, Object> requestParams) {
		String token = (String) requestParams.get("token");
		User user = new User();
		try {
			user.setEmail((String) requestParams.get("email"));
			user.setPassword((String) requestParams.get("password"));
			ticketService.delete(token, user);
			return true;
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		return false;
	}

}