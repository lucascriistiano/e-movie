package br.ufrn.imd.emovie.service;

import java.util.List;

import br.ufrn.imd.emovie.dao.DaoSession;
import br.ufrn.imd.emovie.dao.IDaoSession;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Session;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class SessionService {

	private static SessionService sessionService;
	private IDaoSession daoSession;
	
	private SessionService() {
		this.daoSession = new DaoSession();
	}
	
	public static SessionService getInstance() {
		if(sessionService == null) {
			sessionService = new SessionService();
		}
		
		return sessionService;
	}
	
	public Session find(Integer id) throws DaoException {
		return daoSession.getById(id);
	}
	
	public List<Session> listAll() throws DaoException {
		return daoSession.getAll();
	}
	
	public void create(Session session) throws ServiceException, DaoException {
		daoSession.create(session);
	}
	
	public void update(Session session) throws ServiceException, DaoException {
		daoSession.update(session);
	}
	
	public void delete(Session session) throws DaoException {
		daoSession.delete(session);
	}
	
	public void delete(Integer id) throws DaoException {
		daoSession.delete(id);
	}
	
}