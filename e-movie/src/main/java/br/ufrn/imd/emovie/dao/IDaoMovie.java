package br.ufrn.imd.emovie.dao;

import java.util.List;

public interface IDaoMovie {
	
	void create(Object objeto);
	void update(Object objeto);
	void delete(Object objeto);
	List<Object> getAll(Class<?> entity);
	Object getById(Integer id, Class<?> entity);

}
