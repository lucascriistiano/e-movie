/**
 * 
 */
package br.ufrn.imd.emovie.model;

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
		EntityManagerFactory factory = Persistence.
		          createEntityManagerFactory("e-movie");
		
		EntityManager em = factory.createEntityManager();
		
		Nivel nivel = new Nivel();
		nivel.setName("Funcionário");
		em.getTransaction().begin();
		em.merge(nivel);
		em.getTransaction().commit();
		
		System.out.println(em.createQuery("FROM Nivel").getResultList());

		em.close();
	    factory.close();
	}

}
