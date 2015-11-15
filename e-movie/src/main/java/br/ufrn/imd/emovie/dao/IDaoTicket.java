package br.ufrn.imd.emovie.dao;

import java.util.List;

import br.ufrn.imd.emovie.model.Ticket;

public interface IDaoTicket extends IDaoGeneric<Ticket> {
	
	public List<Ticket> listByExhibitionId(Integer idExhibition);
	public List<Ticket> getAllTokens();
	public Ticket getByToken(String token);
	public Ticket findByChairExhibition(Ticket ticket);
	public Integer countTicketsInternet(Integer id);
	public Integer countTicketsAll(Integer id);

}