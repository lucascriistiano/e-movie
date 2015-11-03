package br.ufrn.imd.emovie.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/ticket")
public class TicketService {

	public static int REQUEST_NUM = 1;
	
	@Path("buy")
	@GET
	@Produces("application/json")
	public Response buy() throws JSONException {
		System.out.println("Processing request " + REQUEST_NUM);
		 REQUEST_NUM++;
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("movie: ", "Goosebumps");
		jsonObject.put("price", 20.0);
		
		String result = "Operation: Buy ticket  \n\n Ticket: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	}
}