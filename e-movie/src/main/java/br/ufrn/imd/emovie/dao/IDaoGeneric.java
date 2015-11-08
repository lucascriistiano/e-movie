package br.ufrn.imd.emovie.dao;

import java.util.List;

import br.ufrn.imd.emovie.dao.exception.DaoException;

public interface IDaoGeneric<T> {
	
	public void create(T entity) throws DaoException;
	public void update(T entity) throws DaoException;
	public void delete(Integer id) throws DaoException;
	public void delete(T entity) throws DaoException;
	public List<T> getAll() throws DaoException;
	public T getById(Integer id) throws DaoException;

}
