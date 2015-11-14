package br.ufrn.imd.emovie.server.executor;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Room;
import br.ufrn.imd.emovie.service.RoomService;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class RoomServiceExecutor extends ServiceExecutorTemplate {
	
	private RoomService roomService;
	
	public RoomServiceExecutor() {
		roomService = RoomService.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Room room = roomService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(room); // returns empty string if room == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Room> rooms = roomService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(rooms); // returns empty string if room == null
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
		Integer rows = Integer.parseInt((String) requestParams.get("rows"));
		
		try {
			Room room = new Room(rows);
			roomService.create(room);
			return true;
		} catch (ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		Integer rows = Integer.parseInt((String) requestParams.get("rows"));
		
		try {
			Room room = new Room(id, rows);
			roomService.update(room);
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
			roomService.delete(id);
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
