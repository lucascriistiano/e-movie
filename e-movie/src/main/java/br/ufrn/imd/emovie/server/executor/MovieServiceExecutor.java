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
import br.ufrn.imd.emovie.model.Movie;
import br.ufrn.imd.emovie.service.MovieService;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class MovieServiceExecutor extends ServiceExecutorTemplate {

	private MovieService movieService;

	public MovieServiceExecutor() {
		movieService = MovieService.getInstance();
	}

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Movie movie = movieService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(movie); // returns empty string if movie
												// == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Movie> movies = movieService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(movies); // returns empty string if movie
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
		String name = (String) requestParams.get("name");
		String synopsis = (String) requestParams.get("synopsis");
		String advertisement = (String) requestParams.get("advertisement");
		String image = (String) requestParams.get("image");
		String strStartExhibition = (String) requestParams.get("start_exhibition");
		String strEndExhibition = (String) requestParams.get("end_exhibition");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startExhibition, endExhibition;
		try {
			startExhibition = formatter.parse(strStartExhibition);
			endExhibition = formatter.parse(strEndExhibition);

			Movie movie = new Movie(name, synopsis, advertisement, image, startExhibition, endExhibition);
			movieService.create(movie);
			
			String objectJSON = new Gson().toJson(movie);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("movie", objectJSON);
			return responseJson.toString();
		} catch (ParseException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Invalid date format");
			return createErrorJSONResponse("Invalid date format");
		} catch (ServiceException | DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		}
	}

	@Override
	public String processPostUpdate(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		String name = (String) requestParams.get("name");
		String synopsis = (String) requestParams.get("synopsis");
		String advertisement = (String) requestParams.get("advertisement");
		String image = (String) requestParams.get("image");
		String strStartExhibition = (String) requestParams.get("start_exhibition");
		String strEndExhibition = (String) requestParams.get("end_exhibition");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startExhibition, endExhibition;
		try {
			startExhibition = formatter.parse(strStartExhibition);
			endExhibition = formatter.parse(strEndExhibition);

			Movie movie = new Movie(id, name, synopsis, advertisement, image, startExhibition, endExhibition);
			movieService.update(movie);

			String objectJSON = new Gson().toJson(movie);
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("movie", objectJSON);
			return responseJson.toString();
		} catch (ParseException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println("Invalid date format");
			return createErrorJSONResponse("Invalid date format");
		} catch (ServiceException | DaoException e) {
			// TODO Implement log
			e.printStackTrace();
			System.out.println(e.getMessage());
			return createErrorJSONResponse(e.getMessage());
		}
	}

	@Override
	public String processPostDelete(Map<String, Object> requestParams) {
		int id = Integer.parseInt((String) requestParams.get("id"));
		try {
			movieService.delete(id);

			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", true);
			responseJson.addProperty("id", id);
			return responseJson.toString();
		} catch (DaoException e) {
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
