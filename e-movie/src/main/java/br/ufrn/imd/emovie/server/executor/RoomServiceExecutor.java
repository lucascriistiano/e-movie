package br.ufrn.imd.emovie.server.executor;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
		String jsonMovie = gson.toJson(room); // returns empty string if room ==
												// null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Room> rooms = roomService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(rooms); // returns empty string if room
												// == null
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
		Integer rows = Integer.parseInt((String) requestParams.get("rows"));

		try {
			Room room = new Room(rows);
			roomService.create(room);
			
			String objectJSON = new Gson().toJson(room);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("room", objectJSON);
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
	public String processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		Integer rows = Integer.parseInt((String) requestParams.get("rows"));

		try {
			Room room = new Room(id, rows);
			roomService.update(room);
			
			String objectJSON = new Gson().toJson(room);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("room", objectJSON);
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
			roomService.delete(id);
			
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
