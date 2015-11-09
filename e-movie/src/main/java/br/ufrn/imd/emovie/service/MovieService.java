package br.ufrn.imd.emovie.service;

import java.util.List;

import br.ufrn.imd.emovie.dao.IDaoMovie;
import br.ufrn.imd.emovie.dao.DaoMovie;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Movie;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class MovieService {

	private static MovieService movieService;
	private IDaoMovie daoMovie;
	
	private MovieService() {
		this.daoMovie = new DaoMovie();
	}
	
	public static MovieService getInstance() {
		if(movieService == null) {
			movieService = new MovieService();
		}
		
		return movieService;
	}
	
	public Movie find(Integer id) throws DaoException {
		return daoMovie.getById(id);
	}
	
	public List<Movie> listAll() throws DaoException {
		return daoMovie.getAll();
	}
	
	public void create(Movie movie) throws ServiceException, DaoException {
		daoMovie.create(movie);
	}
	
	public void update(Movie movie) throws ServiceException, DaoException {
		daoMovie.update(movie);
	}
	
	public void delete(Movie movie) throws DaoException {
		daoMovie.delete(movie);
	}
	
	public void delete(Integer id) throws DaoException {
		daoMovie.delete(id);
	}
	
}