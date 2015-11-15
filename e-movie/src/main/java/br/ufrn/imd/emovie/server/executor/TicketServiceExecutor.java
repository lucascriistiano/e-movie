package br.ufrn.imd.emovie.server.executor;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
	public String processPostCreate(Map<String, Object> requestParams) {
		Integer idExhibition = Integer.parseInt((String) requestParams.get("id_exhibition"));
		String chairNum = (String) requestParams.get("chair_num");
		String email = (String) requestParams.get("email");
		String password = (String) requestParams.get("password");
		String strPurchaseLocation = (String) requestParams.get("purchase_location");

		PurchaseLocation purchaseLocation;
		if (strPurchaseLocation.equals("local")) {
			purchaseLocation = PurchaseLocation.LOCAL;
		} else if (strPurchaseLocation.equals("internet")) {
			purchaseLocation = PurchaseLocation.INTERNET;
		} else {
			System.out.println("Invalid purchase location value '" + strPurchaseLocation + "'");
			return createErrorJSONResponse("Invalid purchase location value '" + strPurchaseLocation + "'");
		}

		String strSendMail = (String) requestParams.get("send_mail");
		boolean sendMail = false;
		if (strSendMail != null && strSendMail.equals("true") && purchaseLocation == PurchaseLocation.INTERNET) {
			sendMail = true;
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

				if (sendMail) {
					mailSender.sendTicket(ticket);
				}

				String objectJSON = new Gson().toJson(ticket);
				JsonObject responseJson = new JsonObject();
				responseJson.addProperty("success", true);
				responseJson.addProperty("ticket", objectJSON);
				return responseJson.toString();
			} else {
				System.out.println("Invalid authentication info");
				return createErrorJSONResponse("Invalid authentication info");
			}
		} catch (ServiceException | DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		} catch (MandrillApiError | IOException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Error on sending mail");
			return createErrorJSONResponse("Error on sending mail");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error on saving changes on database");
			return createErrorJSONResponse("Error on saving changes on database");
		}
	}

	@Override
	public String processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		Integer idExhibition = Integer.parseInt((String) requestParams.get("id_exhibition"));
		String chairNum = (String) requestParams.get("chair_num");
		String email = (String) requestParams.get("email");
		String password = (String) requestParams.get("password");
		String strPurchaseLocation = (String) requestParams.get("purchase_location");

		PurchaseLocation purchaseLocation;
		if (strPurchaseLocation.equals("local")) {
			purchaseLocation = PurchaseLocation.LOCAL;
		} else if (strPurchaseLocation.equals("internet")) {
			purchaseLocation = PurchaseLocation.INTERNET;
		} else {
			System.out.println("Invalid purchase location value '" + strPurchaseLocation + "'");
			return createErrorJSONResponse("Invalid purchase location value '" + strPurchaseLocation + "'");
		}

		String strSendMail = (String) requestParams.get("send_mail");
		boolean sendMail = false;
		if (strSendMail != null && strSendMail.equals("true") && purchaseLocation == PurchaseLocation.INTERNET) {
			sendMail = true;
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

				if (sendMail) {
					mailSender.sendTicket(ticket);
				}

				String objectJSON = new Gson().toJson(ticket);
				JsonObject responseJson = new JsonObject();
				responseJson.addProperty("success", true);
				responseJson.addProperty("ticket", objectJSON);
				return responseJson.toString();
			} else {
				System.out.println("Invalid authentication info");
				return createErrorJSONResponse("Invalid authentication info");
			}
		} catch (ServiceException | DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		} catch (MandrillApiError | IOException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Error on sending mail");
			return createErrorJSONResponse("Error on sending mail");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error on saving changes on database");
			return createErrorJSONResponse("Error on saving changes on database");
		}
	}

	@Override
	public String processPostDelete(Map<String, Object> requestParams) {
		String token = (String) requestParams.get("token");
		User user = new User();
		try {
			user.setEmail((String) requestParams.get("email"));
			user.setPassword((String) requestParams.get("password"));
			ticketService.delete(token, user);

			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("token", token);
			return responseJson.toString();
		} catch (ServiceException | DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		}
	}

	@Override
	public String processPostOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		System.out.println("Operation not supported");
		return createErrorJSONResponse("Operation not supported");
	}

}