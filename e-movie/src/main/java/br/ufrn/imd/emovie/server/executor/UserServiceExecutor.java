package br.ufrn.imd.emovie.server.executor;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.User;
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
	
	private UserService userService;
	private MailSender mailSender;
	
	public UserServiceExecutor() {
		userService = UserService.getInstance();
		mailSender = new MailSender();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		User user = userService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(user); // returns empty string if user == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<User> users = userService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(users); // returns empty string if user == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams)
			throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean processPostCreate(Map<String, Object> requestParams) {
		String name = (String) requestParams.get("name");
		String email = (String) requestParams.get("email");
		String password = userService.generatePassword();		
		boolean admin = false; // creates a non admin user by default
		Date createdAt = new Date();
		
		try {
			User user = new User(name, password, email, admin, createdAt);
			userService.create(user);
			mailSender.sendRegisterConfirmation(user);
			return true;
		} catch (ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (MandrillApiError | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to send the mail");
			return true; // operation well well succeeded but email not sent
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		String name = (String) requestParams.get("name");
		String password = (String) requestParams.get("password");
		String email = (String) requestParams.get("email");
		boolean admin = Boolean.parseBoolean((String) requestParams.get("admin"));
		Date createdAt = new Date();
		
		try {
			User user = new User(id, name, password, email, admin, createdAt);
			userService.update(user);
			return true;
		} catch (ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostDelete(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		try {
			userService.delete(id);
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
		// TODO Auto-generated method stub
		return false;
	}
}