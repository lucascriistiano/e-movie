package br.ufrn.imd.emovie.service;

import java.util.List;

import br.ufrn.imd.emovie.dao.DaoExhibition;
import br.ufrn.imd.emovie.dao.IDaoExhibition;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Exhibition;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class ExhibitionService {

	private static ExhibitionService exhibitionService;
	private IDaoExhibition daoExhibition;
	
	private ExhibitionService() {
		this.daoExhibition = new DaoExhibition();
	}
	
	public static ExhibitionService getInstance() {
		if(exhibitionService == null) {
			exhibitionService = new ExhibitionService();
		}
		
		return exhibitionService;
	}
	
	public Exhibition find(Integer id) throws DaoException {
		return daoExhibition.getById(id);
	}
	
	public List<Exhibition> listAll() throws DaoException {
		return daoExhibition.getAll();
	}
	
	public void create(Exhibition exhibition) throws ServiceException, DaoException {
		daoExhibition.create(exhibition);
	}
	
	public void update(Exhibition exhibition) throws ServiceException, DaoException {
		daoExhibition.update(exhibition);
	}
	
	public void delete(Exhibition exhibition) throws DaoException {
		daoExhibition.delete(exhibition);
	}
	
	public void delete(Integer id) throws DaoException {
		daoExhibition.delete(id);
	}
	
}