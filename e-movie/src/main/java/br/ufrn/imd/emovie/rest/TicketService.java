package br.ufrn.imd.emovie.rest;

//@Path("/ticket")
public class TicketService {

//	public static int REQUEST_NUM = 1;
//	
//	@Path("buy")
//	@GET
//	@Produces("application/json")
//	public Response buy() throws JSONException {
//		System.out.println("Processing request " + REQUEST_NUM);
//		 REQUEST_NUM++;
//		
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("movie: ", "Goosebumps");
//		jsonObject.put("price", 20.0);
//		
//		String result = "Operation: Buy ticket  \n\n Ticket: \n\n" + jsonObject;
//		return Response.status(200).entity(result).build();
//	}
//	
//	@Path("teste")
//	@GET
//	public void test() {
//		System.out.println("Processing request");
//		
//		Thread thread = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				System.out.println("");
//				
//				String result = "Operation: Buy ticket  \n\n Ticket: \n\n" + jsonObject;
//				return Response.status(200).entity(result).build();
//			}
//		});
//		thread.start();
//		
//		
//	}
//
//	@Path("{f}")
//	@GET
//	@Produces("application/json")
//	public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
//
//		JSONObject jsonObject = new JSONObject();
//		float celsius;
//		celsius = (f - 32) * 5 / 9;
//		jsonObject.put("F Value", f);
//		jsonObject.put("C Value", celsius);
//
//		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
//		return Response.status(200).entity(result).build();
//	}
}