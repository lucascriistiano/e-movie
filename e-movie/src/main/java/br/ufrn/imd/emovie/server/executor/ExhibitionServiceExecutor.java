package br.ufrn.imd.emovie.server.executor;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Exhibition;
import br.ufrn.imd.emovie.service.ExhibitionService;
import br.ufrn.imd.emovie.service.exception.ServiceException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class ExhibitionServiceExecutor extends ServiceExecutorTemplate {
	
	private ExhibitionService exhibitionService;
	
	public ExhibitionServiceExecutor() {
		exhibitionService = ExhibitionService.getInstance();
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processPostDelete(Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processPostOther(HttpExchange httpExchange, List<String> urlParams,
			Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return false;
	}
}