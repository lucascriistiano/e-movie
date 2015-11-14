/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import br.ufrn.imd.emovie.model.Ticket;

/**
 * @author lucas cristiano
 *
 */
public class DaoTicket extends DaoGeneric<Ticket> implements IDaoTicket {
	
	@SuppressWarnings("unchecked")
	public List<Ticket> getAllTokens() {
		return getEntityManager().createNativeQuery("SELECT distinct token FROM ticket;").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getByToken(String token) {
		List<Ticket> tickets = getEntityManager().createQuery("FROM Ticket WHERE token = '" + token + "'").getResultList();
		return tickets.size() > 0 ? tickets.get(0) : null;
	} 
	
}
