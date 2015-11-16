package br.ufrn.imd.emovie.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ufrn.imd.emovie.Server;
import br.ufrn.imd.emovie.dao.DaoTicket;
import br.ufrn.imd.emovie.dao.IDaoTicket;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.PurchaseLocation;
import br.ufrn.imd.emovie.model.Session;
import br.ufrn.imd.emovie.model.Ticket;
import br.ufrn.imd.emovie.model.User;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class TicketService {
	
	public static final double ONLINE_SALES_PERCENT = 0.4;
	
	public static final long ONE_HOUR = 3600000; // millis
	public static final long TICKET_BUY_TIME_LIMIT = ONE_HOUR;
	public static final long TICKET_CHANGE_TIME_LIMIT = ONE_HOUR * 4;
	public static final long TICKET_CANCEL_TIME_LIMIT = ONE_HOUR * 6;
	
	private ChairStateService chairStateService;
	
	private static TicketService ticketService;
	private IDaoTicket daoTicket;

	private TicketService() {
		this.chairStateService = ChairStateService.getInstance();
		this.daoTicket = new DaoTicket();
	}

	public static TicketService getInstance() {
		if (ticketService == null) {
			ticketService = new TicketService();
		}

		return ticketService;
	}

	public Ticket find(Integer id) throws DaoException {
		return daoTicket.getById(id);
	}
	
	public Ticket getByToken(String token) {
		return daoTicket.getByToken(token);
	}

	public List<Ticket> listAll() throws DaoException {
		return daoTicket.getAll();
	}

	public List<Ticket> listByExhibitionId(Integer idExhibition) {
		return daoTicket.listByExhibitionId(idExhibition);
	}

	public void create(Ticket ticket) throws ServiceException, DaoException, InterruptedException {
		String token = generateToken(ticket.getUser().getId());
		ticket.setToken(token);
		
		Server.writeSemaphore.acquire();
		try {
			if(compareDates(ticket, TICKET_BUY_TIME_LIMIT)) {
				validateTicket(ticket);
				daoTicket.create(ticket);
			} else {
				throw new ServiceException("Passada a hora limite para realizar a compra de Tickets para a exibição");
			}
		} finally {
			Server.writeSemaphore.release();
		}
	}

	public void update(Ticket ticket) throws ServiceException, DaoException, InterruptedException {
		Server.writeSemaphore.acquire();
		try {
			if(compareDates(ticket, TICKET_CHANGE_TIME_LIMIT)) {
				validateTicket(ticket);
				daoTicket.update(ticket);
			} else {
				throw new ServiceException("Passada hora limite para realizar a troca da sessão dessa exibição");
			}
		} finally {
			Server.writeSemaphore.release();
		}
	}

	public void delete(Ticket ticket) throws DaoException {
		daoTicket.delete(ticket);
	}

	public void delete(Integer id) throws DaoException {
		daoTicket.delete(id);
	}
	
	public void delete(String token, User user) throws ServiceException, DaoException {
		Ticket ticket = daoTicket.getByToken(token);
		
		if(ticket != null && user.compareLogin(ticket.getUser())) {
			if(compareDates(ticket, TICKET_CANCEL_TIME_LIMIT)) {
				daoTicket.delete(ticket.getId());
			} else {
				throw new ServiceException("Passada hora limite para realizar o cancelamento do Ticket");
			}
		} else {
			throw new ServiceException("Um erro ocorreu, verifique as informações digitadas");
		}
	}

	/**
	 * Compare two dates to see the hours difference between then.
	 * @param ticket
	 * @return
	 */
	private boolean compareDates(Ticket ticket, long timeLimit) {
		Session session = ticket.getExhibition().getSession();
		Integer sessionDayWeek = session.getDayWeek();
		Date sessionHour = session.getHour();
		
		Calendar calendarTicket = Calendar.getInstance();
		calendarTicket.setTime(sessionHour);
		
		Calendar today = Calendar.getInstance();
		Integer todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK) - 1;
		
		calendarTicket.set(Calendar.MONTH, today.get(Calendar.MONTH));
		calendarTicket.set(Calendar.YEAR, today.get(Calendar.YEAR));
		calendarTicket.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
		
		Integer differenceDay = Math.abs(sessionDayWeek - todayDayOfWeek);
		calendarTicket.add(Calendar.DATE, differenceDay);
		
		long diferenceHours = calendarTicket.getTimeInMillis() - today.getTimeInMillis();
		
		return (diferenceHours >= timeLimit ? true : false);
	}

	/**
	 * Generates random token based on current date/time and user id
	 * 
	 * @param userId Id of user that is buying the ticket
	 * @return The generated token
	 */
	private String generateToken(Integer userId) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 4; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}

		DateFormat format = new SimpleDateFormat("ddMMyyyyHHmmssSS");
		sb.append(format.format(new Date()));

		sb.append(String.format("%05d", userId));

		return sb.toString().toUpperCase();
	}

	/**
	 * Verifica o cumprimento das regras de negócio
	 * @param ticket
	 * @throws ServiceException
	 */
	private void validateTicket(Ticket ticket) throws ServiceException {
		Ticket foundTicket = daoTicket.findByChairAndExhibition(ticket);	
		if(foundTicket != null) {
			throw new ServiceException("Já existe um ticket comprado para essa mesma cadeira e sessão");
		}
		
		Map<String, Integer> exhibitionChairState = chairStateService.getExhibitionChairState(ticket.getExhibition());
		int totalRoomChairs = exhibitionChairState.size();
		
		String selectedChair = ticket.getChairNumber();
		if(!exhibitionChairState.containsKey(selectedChair)) {
			throw new ServiceException("A cadeira selecionada não é existente na sala dessa exibição.");
		}
		
		Integer occupedChairsByInternet = daoTicket.countTicketsInternet(ticket.getExhibition().getId());
		Integer totalOccupedChairs = daoTicket.countTicketsAll(ticket.getExhibition().getId());
		
		if((ticket.getPurchaseLocation() == PurchaseLocation.INTERNET) && (occupedChairsByInternet >= ((int) Math.round(totalRoomChairs * ONLINE_SALES_PERCENT)))) {
			throw new ServiceException("Limite de compras pela internet atingido.");
		}
		
		if(totalOccupedChairs >= totalRoomChairs) {
			throw new ServiceException("Sala lotada.");
		}
		
	}
}
