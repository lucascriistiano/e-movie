package br.ufrn.imd.emovie.server.executor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
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
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams)
			throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean processPostCreate(Map<String, Object> requestParams) {
		String dayWeek = (String) requestParams.get("day_week");
		String strHour = (String) requestParams.get("hour");
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		Date hour;
		try {
			hour = formatter.parse(strHour);
			
			Session session = new Session(dayWeek, hour);
			sessionService.create(session);
			return true;
		} catch (ParseException | ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		String dayWeek = (String) requestParams.get("day_week");
		String strHour = (String) requestParams.get("hour");
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date hour;
		try {
			hour = formatter.parse(strHour);
			
			Session session = new Session(id, dayWeek, hour);
			sessionService.update(session);
			return true;
		} catch (ParseException | ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostDelete(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		try {
			sessionService.delete(id);
			return true;
		} catch (DaoException e ) {
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