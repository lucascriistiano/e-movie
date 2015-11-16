package br.ufrn.imd.emovie.server.executor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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

	private static final Logger LOGGER = Logger.getLogger(SessionServiceExecutor.class.getName());
	
	private SessionService sessionService;

	public SessionServiceExecutor() {
		sessionService = SessionService.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Session session = sessionService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(session); // returns empty string if session == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Session> sessions = sessionService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(sessions); // returns empty string if session == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
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
			LOGGER.error("Invalid time format", e);
			return createErrorJSONResponse("Invalid time format");
		} catch (ServiceException e) {
			LOGGER.warn(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
			return createErrorJSONResponse(e.getMessage());
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
			LOGGER.error("Invalid time format", e);
			return createErrorJSONResponse("Invalid time format");
		} catch (ServiceException e) {
			LOGGER.warn(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
			return createErrorJSONResponse(e.getMessage());
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
			LOGGER.error(e.getMessage(), e);
			return createErrorJSONResponse(e.getMessage());
		}
	}

	@Override
	public String processPostOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		LOGGER.warn("Operation not supported");
		return createErrorJSONResponse("Operation not supported");
	}
}