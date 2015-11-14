package br.ufrn.imd.emovie.dao;

import java.util.List;

import br.ufrn.imd.emovie.model.Ticket;

public interface IDaoTicket extends IDaoGeneric<Ticket> {
	
	public List<Ticket> getAllTokens();

	public Ticket getByToken(String token);

}