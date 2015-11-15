/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.ufrn.imd.emovie.model.PurchaseLocation;
import br.ufrn.imd.emovie.model.Ticket;

/**
 * @author lucas cristiano
 *
 */
public class DaoTicket extends DaoGeneric<Ticket> implements IDaoTicket {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> listByExhibitionId(Integer idExhibition) {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tickets = entityManager.createQuery("FROM Ticket WHERE id_exhibition = " + idExhibition).getResultList();
		entityManager.close();
		return tickets;
	} 
	
	@SuppressWarnings("unchecked")
	public List<Ticket> getAllTokens() {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tokens = entityManager.createNativeQuery("SELECT distinct token FROM ticket;").getResultList();
		entityManager.close();
		return tokens;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getByToken(String token) {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tickets = entityManager.createQuery("FROM Ticket WHERE token = '" + token + "'").getResultList();
		entityManager.close();
		return tickets.size() > 0 ? tickets.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Ticket findByChairExhibition(Ticket ticket) {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tickets = entityManager.createQuery("FROM Ticket WHERE chairNumber = '" + ticket.getChairNumber() + "' AND exhibition.id = " + ticket.getExhibition().getId()).getResultList();
		entityManager.close();
		
		return tickets.size() > 0 ? tickets.get(0) : null;
	}

	@Override
	public Integer countTicketsInternet(Integer id_exhibition) {
		EntityManager entityManager = getEntityManager();
		Long quantity = (Long) entityManager.createQuery("SELECT count(t) FROM Ticket t WHERE exhibition.id = " + id_exhibition + " AND purchaseLocation = " + PurchaseLocation.INTERNET.ordinal()).getSingleResult();
		entityManager.close();
		
		return quantity.intValue();
	}

	@Override
	public Integer countTicketsAll(Integer id_exhibition) {
		EntityManager entityManager = getEntityManager();
		Long quantity = (Long) entityManager.createQuery("SELECT count(t) FROM Ticket t WHERE exhibition.id = " + id_exhibition).getSingleResult();
		entityManager.close();
		
		return quantity.intValue();
	}
	
}