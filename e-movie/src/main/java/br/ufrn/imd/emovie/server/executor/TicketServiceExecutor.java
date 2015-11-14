package br.ufrn.imd.emovie.server.executor;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;
import br.ufrn.imd.emovie.model.Ticket;
import br.ufrn.imd.emovie.service.TicketService;

/**
*
* @author lucas cristiano
*
*/
@SuppressWarnings("restriction")
public class TicketServiceExecutor extends ServiceExecutorTemplate {

	private TicketService ticketService;
	private static final String RETRIEVE_TOKEN = "retrieveToken"; 
	
	public TicketServiceExecutor() {
	        ticketService = TicketService.getInstance();
	}
	
	@Override
	public String processGetFindOne(Integer id) throws DaoException {
		Ticket ticket = ticketService.find(id);
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(ticket); // returns empty string if ticket == null
		return jsonMovie;
	}

	@Override
	public String processGetFindAll() throws DaoException {
		List<Ticket> tickets = ticketService.listAll();
		Gson gson = new Gson();
		String jsonMovie = gson.toJson(tickets); // returns empty string if movie == null
		return jsonMovie;
	}

	@Override
	public String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) {
		//TODO
		String operation = (String) requestParams.get("operation");
		if(operation.equals(RETRIEVE_TOKEN)) {
			String token = (String) requestParams.get("token");
			Ticket ticket = ticketService.getByToken(token);
			
			Gson gson = new Gson();
			String jsonTicket = gson.toJson(ticket);
			return jsonTicket;
		}
		
		return "";
	}

	@Override
	public boolean processPostCreate(Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return false;
		
//		String strDate = (String) requestParams.get("date");
//		Float price = Float.parseFloat((String) requestParams.get("price"));
//		Integer idExhibition = Integer.parseInt((String) requestParams.get("id_exhibition"));
//		Integer idUser = Integer.parseInt((String) requestParams.get("id_user"));
//		Date createdAt = new Date(); // current time
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date date;
//		try {
//		        date = formatter.parse(strDate);
//		
//		
//		
//		
//		        Movie movie = new Ticket();
//		        movieService.create(movie);
//		        return true;
//		} catch (ParseException | ServiceException | DaoException e ) {
//		        // TODO Auto-generated catch block
//		        e.printStackTrace();
//		        return false;
//		}
	}

	@Override
	public boolean processPostUpdate(Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return false;
		
//		int id = Integer.parseInt((String) requestParams.get("id"));
//		String name = (String) requestParams.get("name");
//		String strStartExhibition = (String) requestParams.get("start_exhibition");
//		String strEndExhibition = (String) requestParams.get("end_exhibition");
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Date startExhibition, endExhibition;
//		try {
//		        startExhibition = formatter.parse(strStartExhibition);
//		        endExhibition = formatter.parse(strEndExhibition);
//		
//		        Movie movie = new Movie(id, name, startExhibition, endExhibition);
//		        movieService.update(movie);
//		        return true;
//		} catch (ParseException | ServiceException | DaoException e ) {
//		        // TODO Auto-generated catch block
//		        e.printStackTrace();
//		        return false;
//		}
	}

	@Override
	public boolean processPostDelete(Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return false;
		
//		int id = Integer.parseInt((String) requestParams.get("id"));
//		try {
//		        movieService.delete(id);
//		        return true;
//		} catch (DaoException e ) {
//		        // TODO Auto-generated catch block
//		        e.printStackTrace();
//		        return false;
//		}
	}

	@Override
	public boolean processPostOther(HttpExchange httpExchange, List<String> urlParams,Map<String, Object> requestParams) {
		return false;
	}

}
