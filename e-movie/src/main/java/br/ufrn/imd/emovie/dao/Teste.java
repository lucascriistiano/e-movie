/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.Date;

import br.ufrn.imd.emovie.model.Movie;

/**
 * @author joao
 *
 */
public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DaoMovie dao = new UserDao();
		
		Movie movie = new Movie();
		movie.setName("Filme teste2");
		movie.setStartExibition(new Date());
		movie.setEndExibition(new Date());
		movie.setSessions(null);
		
		dao.create(movie);
		
		System.out.println(dao.getAll(Movie.class));
	}

}
