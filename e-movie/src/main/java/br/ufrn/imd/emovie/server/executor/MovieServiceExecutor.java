package br.ufrn.imd.emovie.server.executor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public class MovieServiceExecutor implements IServiceExecutor {

	@Override
	public void execute(HttpExchange httpExchange) {
		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) httpExchange.getAttribute("parameters");

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
		
		try {
			httpExchange.sendResponseHeaders(200, jsonString.length());
			
			OutputStream os = httpExchange.getResponseBody();
			os.write(jsonString.getBytes());
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
