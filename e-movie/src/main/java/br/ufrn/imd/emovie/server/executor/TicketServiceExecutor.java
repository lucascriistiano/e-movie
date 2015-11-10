package br.ufrn.imd.emovie.server.executor;

import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class TicketServiceExecutor extends ServiceExecutorTemplate {

	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams)
			throws ServiceException, DaoException {
		// TODO Auto-generated method stub
		return null;
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
