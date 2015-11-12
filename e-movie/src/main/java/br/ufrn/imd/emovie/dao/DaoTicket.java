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
	
}
