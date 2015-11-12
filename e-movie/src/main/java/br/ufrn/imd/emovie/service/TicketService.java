package br.ufrn.imd.emovie.service;

import java.util.List;
import java.util.Random;

import br.ufrn.imd.emovie.dao.DaoTicket;
import br.ufrn.imd.emovie.dao.IDaoTicket;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Ticket;

public class TicketService {
	
	private IDaoTicket daoTicket;
	
	public TicketService() {
		daoTicket = new DaoTicket();
	}

	public void create(Ticket ticket) throws DaoException {
		List<Ticket> allTokens = daoTicket.getAllTokens();
		String token;
		
		/* Gera o token até achar um que não exista */
		do {
			token = generateToken().toUpperCase();
		} while (allTokens.contains(token));
		
		ticket.setToken(token);
		
		daoTicket.create(ticket);
	}
	
	public String generateToken() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i < 7; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
	
}
