package br.ufrn.imd.emovie.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.ufrn.imd.emovie.Application;
import br.ufrn.imd.emovie.dao.DaoTicket;
import br.ufrn.imd.emovie.dao.IDaoTicket;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.PurchaseLocation;
import br.ufrn.imd.emovie.model.Ticket;
import br.ufrn.imd.emovie.model.User;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class TicketService {
	
	public static final long SIXHOURS = 21600000;
	public static final double ONLINE_SALES_PERCENT = 0.4;
	
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
		
		Application.write_sem.acquire();
		try {
			validateTicket(ticket);
			
			daoTicket.create(ticket);
		} finally {
			Application.write_sem.release();
		}
	}

	public void update(Ticket ticket) throws ServiceException, DaoException {
		daoTicket.update(ticket);
	}

	public void delete(Ticket ticket) throws DaoException {
		daoTicket.delete(ticket);
	}

	public void delete(Integer id) throws DaoException {
		daoTicket.delete(id);
	}
	
	public void delete(String token, User user) throws DaoException {
		Ticket ticket = daoTicket.getByToken(token);
		
		if(ticket != null && user.compareLogin(ticket.getUser())) {
			if(compareDates(ticket)) {
				daoTicket.delete(ticket.getId());
			} else {
				throw new DaoException("Passada hora mínima para realizar o cancelamento do Ticket");
			}
		} else {
			throw new DaoException("Um erro ocorreu, verifique as informações digitadas");
		}
	}

	/**
	 * Compare two dates to see de hours difference between then.
	 * @param ticket
	 * @return
	 */
	private boolean compareDates(Ticket ticket) {
		Integer dayWeekTicket = ticket.getExhibition().getSession().getDayWeek();
		Date hourTicket = ticket.getExhibition().getSession().getHour();
		Calendar calendarTicket = Calendar.getInstance();
		calendarTicket.setTime(hourTicket);
		
		Calendar today = Calendar.getInstance();
		Integer todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK) - 1;
		
		calendarTicket.set(Calendar.MONTH, today.get(Calendar.MONTH));
		calendarTicket.set(Calendar.YEAR, today.get(Calendar.YEAR));
		calendarTicket.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
		
		Integer differenceDay = Math.abs(dayWeekTicket - todayDayOfWeek);
		calendarTicket.add(Calendar.DATE, differenceDay);
		
		long diferenceHours = calendarTicket.getTimeInMillis() - today.getTimeInMillis();
		
		return (diferenceHours >= SIXHOURS ? true : false);
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
		Ticket foundChair = daoTicket.findByChairExhibition(ticket);	
		
		Integer occupedChairsByInternet = daoTicket.countTicketsInternet(ticket.getExhibition().getId());
		Integer totalOccupedChairs = daoTicket.countTicketsAll(ticket.getExhibition().getId());
		
		Map<String, Integer> exhibitionChairState = chairStateService.getExhibitionChairState(ticket.getExhibition());
		int totalRoomChairs = exhibitionChairState.size();
		
		if(foundChair != null) {
			throw new ServiceException("Já existe um ticket comprado para essa mesma cadeira e sessão");
		}
		
		if((ticket.getPurchaseLocation() == PurchaseLocation.INTERNET) && (occupedChairsByInternet >= ((int) Math.round(totalRoomChairs * ONLINE_SALES_PERCENT)))) {
			throw new ServiceException("Limite de compras pela internet atingido.");
		}
		
		if(totalOccupedChairs >= totalRoomChairs) {
			throw new ServiceException("Sala lotada.");
		}
		
	}
}
