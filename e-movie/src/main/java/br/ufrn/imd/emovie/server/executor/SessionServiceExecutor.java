package br.ufrn.imd.emovie.server.executor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Session;
import br.ufrn.imd.emovie.service.SessionService;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class SessionServiceExecutor extends ServiceExecutorTemplate {

	private SessionService sessionService;

	public SessionServiceExecutor() {
		sessionService = SessionService.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Session session = sessionService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(session); // returns empty string if
													// session == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Session> sessions = sessionService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(sessions); // returns empty string if
													// session == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String processPostCreate(Map<String, Object> requestParams) {
		Integer dayWeek = Integer.parseInt((String) requestParams.get("day_week"));
		String strHour = (String) requestParams.get("hour");

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date hour;
		try {
			hour = formatter.parse(strHour);

			Session session = new Session(dayWeek, hour);
			sessionService.create(session);
			
			String objectJSON = new Gson().toJson(session);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("session", objectJSON);
			return responseJson.toString();
		} catch (ParseException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Invalid time format");
			return createErrorJSONResponse("Invalid time format");
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
	public String processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		Integer dayWeek = Integer.parseInt((String) requestParams.get("day_week"));
		String strHour = (String) requestParams.get("hour");

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date hour;
		try {
			hour = formatter.parse(strHour);

			Session session = new Session(id, dayWeek, hour);
			sessionService.update(session);
			
			String objectJSON = new Gson().toJson(session);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("session", objectJSON);
			return responseJson.toString();
		} catch (ParseException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Invalid time format");
			return createErrorJSONResponse("Invalid time format");
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
			sessionService.delete(id);
			
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