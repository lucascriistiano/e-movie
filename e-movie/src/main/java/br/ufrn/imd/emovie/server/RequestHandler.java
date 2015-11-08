package br.ufrn.imd.emovie.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

@SuppressWarnings("restriction")
public class RequestHandler implements HttpHandler {
	
	private static int NUM_REQ = 1;

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		URI requestURI = httpExchange.getRequestURI();
		System.out.println("Processing request #" + NUM_REQ + ": " + requestURI.getPath());
		NUM_REQ++;

		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>)httpExchange.getAttribute("parameters");
		
		System.out.println((String) params.get("id"));
		System.out.println((String) params.get("name"));
		System.out.println((String) params.get("price"));
		
		int id = Integer.parseInt((String) params.get("id"));
		String name = (String) params.get("name");
		double price = Double.parseDouble((String) params.get("price"));
		
		// Generic object test
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("movie", name);
		json.put("price", price); 
		String jsonString = json.toString();

		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
		httpExchange.sendResponseHeaders(200, jsonString.length());

		System.out.println(httpExchange.getRequestMethod());	
		
		System.out.println(responseHeaders.toString());

		OutputStream os = httpExchange.getResponseBody();
		os.write(jsonString.getBytes());
		os.close();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
