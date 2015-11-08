/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.Date;

import br.ufrn.imd.emovie.model.Movie;
import br.ufrn.imd.emovie.model.Room;
import br.ufrn.imd.emovie.model.Session;

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
		
		Movie movie = new Movie("Ghost Busters", new Date(), new Date());
		dao.create(movie);
		System.out.println(dao.getAll(Movie.class));
		
		Room room1 = new Room(100);
		Room room2 = new Room(150);
		Room room3 = new Room(200);
		Room room4 = new Room(50);
		
		Session session1 = new Session(0,1);
		Session session2 = new Session(0,2);
		Session session3 = new Session(3,1);
		Session session4 = new Session(3,2);
		
	}

}
