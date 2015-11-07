package br.ufrn.imd.emovie.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

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

		// Generic object test
		JSONObject json = new JSONObject();
		json.put("id", 1);
		json.put("movie", "Goosebumper");
		json.put("price", 20.0);
		String jsonString = json.toString();

		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
		httpExchange.sendResponseHeaders(200, jsonString.length());

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
