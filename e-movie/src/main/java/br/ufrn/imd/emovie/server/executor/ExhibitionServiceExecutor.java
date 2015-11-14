package br.ufrn.imd.emovie.server.executor;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Exhibition;
import br.ufrn.imd.emovie.model.Movie;
import br.ufrn.imd.emovie.model.Room;
import br.ufrn.imd.emovie.model.Session;
import br.ufrn.imd.emovie.service.ExhibitionService;
import br.ufrn.imd.emovie.service.MovieService;
import br.ufrn.imd.emovie.service.RoomService;
import br.ufrn.imd.emovie.service.SessionService;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class ExhibitionServiceExecutor extends ServiceExecutorTemplate {
	
	private ExhibitionService exhibitionService;
	
	private MovieService movieService;
	private SessionService sessionService;
	private RoomService roomService;
	
	public ExhibitionServiceExecutor() {
		exhibitionService = ExhibitionService.getInstance();
		
		movieService = MovieService.getInstance();
		sessionService = SessionService.getInstance();
		roomService = RoomService.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Exhibition exhibition = exhibitionService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(exhibition); // returns empty string if exhibition == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Exhibition> exhibitions = exhibitionService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(exhibitions); // returns empty string if exhibition == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean processPostCreate(Map<String, Object> requestParams) {
		Integer idMovie = Integer.parseInt((String) requestParams.get("id_movie"));
		Integer idSession = Integer.parseInt((String) requestParams.get("id_session"));
		Integer idRoom = Integer.parseInt((String) requestParams.get("id_room"));
		
		try {
			Movie movie = movieService.find(idMovie);
			Session session = sessionService.find(idSession);
			Room room = roomService.find(idRoom);
			
			Exhibition exhibition = new Exhibition(movie, session, room);
			exhibitionService.create(exhibition);
			return true;
		} catch (ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		Integer id = Integer.parseInt((String) requestParams.get("id"));
		Integer idMovie = Integer.parseInt((String) requestParams.get("id_movie"));
		Integer idSession = Integer.parseInt((String) requestParams.get("id_session"));
		Integer idRoom = Integer.parseInt((String) requestParams.get("id_room"));
		
		try {
			Movie movie = movieService.find(idMovie);
			Session session = sessionService.find(idSession);
			Room room = roomService.find(idRoom);
			
			Exhibition exhibition = new Exhibition(id, movie, session, room);
			exhibitionService.update(exhibition);
			return true;
		} catch (ServiceException | DaoException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean processPostDelete(Map<String, Object> requestParams) {
		Integer id = Integer.parseInt((String) requestParams.get("id"));
		
		try {
			exhibitionService.delete(id);
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