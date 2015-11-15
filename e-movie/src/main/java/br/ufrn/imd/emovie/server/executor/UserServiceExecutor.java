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
import br.ufrn.imd.emovie.model.User;
import br.ufrn.imd.emovie.model.UserType;
import br.ufrn.imd.emovie.service.UserService;
import br.ufrn.imd.emovie.service.email.MailSender;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class UserServiceExecutor extends ServiceExecutorTemplate {

	private static final String LOGIN = "login";

	private UserService userService;
	private MailSender mailSender;

	public UserServiceExecutor() {
		userService = UserService.getInstance();
		mailSender = MailSender.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		User user = userService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(user); // returns empty string if user ==
												// null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<User> users = userService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(users); // returns empty string if user
												// == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		String operation = (String) requestParams.get("operation");
		if (operation.equals(LOGIN)) {
			try {
				User usuario = new User();
				usuario.setEmail((String) requestParams.get("email"));
				usuario.setPassword((String) requestParams.get("senha"));

				User user = userService.checkLogin(usuario);

				Gson gson = new Gson();
				String jsonTicket = gson.toJson(user);
				return jsonTicket;
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return "";
	}

	@Override
	public String processPostCreate(Map<String, Object> requestParams) {
		String name = (String) requestParams.get("name");
		String email = (String) requestParams.get("email");
		String password = userService.generatePassword();
		Date createdAt = new Date();

		try {
			User user = new User(name, password, email, UserType.USER, createdAt);
			userService.create(user);
			
			mailSender.sendRegisterConfirmation(user);
			
			String objectJSON = new Gson().toJson(user);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("user", objectJSON);
			return responseJson.toString();
		} catch (ServiceException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Invalid object to process");
			return createErrorJSONResponse("Invalid object to process");
		} catch (DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Error on saving changes on database");
			return createErrorJSONResponse("Error on saving changes on database");
		}
		catch (MandrillApiError | IOException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Error on sending mail");
			return createErrorJSONResponse("Error on sending mail");
		}
	}

	@Override
	public String processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		String name = (String) requestParams.get("name");
		String password = (String) requestParams.get("password");
		String email = (String) requestParams.get("email");
		Date createdAt = new Date();

		try {
			User user = new User(id, name, password, email, UserType.USER, createdAt);
			userService.update(user);
			
			String objectJSON = new Gson().toJson(user);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("user", objectJSON);
			return responseJson.toString();
		} catch (ServiceException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Invalid object to process");
			return createErrorJSONResponse("Invalid object to process");
		} catch (DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Error on saving changes on database");
			return createErrorJSONResponse("Error on saving changes on database");
		}
	}

	@Override
	public String processPostDelete(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		try {
			userService.delete(id);
			
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("id", id);
			return responseJson.toString();
		} catch (DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Error on saving changes on database");
			return createErrorJSONResponse("Error on saving changes on database");
		}
	}

	@Override
	public String processPostOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		System.out.println("Operation not supported");
		return createErrorJSONResponse("Operation not supported");
	}
}