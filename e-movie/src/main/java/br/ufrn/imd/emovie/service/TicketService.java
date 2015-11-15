package br.ufrn.imd.emovie.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.emovie.dao.DaoTicket;
import br.ufrn.imd.emovie.dao.IDaoTicket;
import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Ticket;
import br.ufrn.imd.emovie.model.User;
import br.ufrn.imd.emovie.service.exception.ServiceException;

public class TicketService {
	
	public final static long SIXHOURS = 21600000;

	private static TicketService ticketService;
	private IDaoTicket daoTicket;

	private TicketService() {
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

	public void create(Ticket ticket) throws ServiceException, DaoException {
		String token = generateToken(ticket.getUser().getId());
		ticket.setToken(token);

		daoTicket.create(ticket);
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

}
