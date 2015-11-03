/**
 * 
 */
package br.ufrn.imd.emovie.model;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author joao
 *
 */
public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("e-movie");
		
		EntityManager em = factory.createEntityManager();
		
		Movie movie = new Movie();
		movie.setName("Filme teste");
		movie.setStartExibition(new Date());
		movie.setEndExibition(new Date());
		movie.setSessions(null);
		
		em.getTransaction().begin();
		em.merge(movie);
		em.getTransaction().commit();
		
		System.out.println(em.createQuery("FROM Movie").getResultList());

		em.close();
	    factory.close();
	}

}
