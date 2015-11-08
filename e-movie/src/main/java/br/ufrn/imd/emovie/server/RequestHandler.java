package br.ufrn.imd.emovie.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.ufrn.imd.emovie.server.executor.MovieServiceExecutor;
import br.ufrn.imd.emovie.server.executor.RoomServiceExecutor;
import br.ufrn.imd.emovie.server.executor.IServiceExecutor;
import br.ufrn.imd.emovie.server.executor.SessionServiceExecutor;
import br.ufrn.imd.emovie.server.executor.TicketServiceExecutor;
import br.ufrn.imd.emovie.server.executor.UserServiceExecutor;

@SuppressWarnings("restriction")
public class RequestHandler implements HttpHandler {

	private static int REQUEST_NUMBER = 1;
	private Map<String, IServiceExecutor> serviceExecutors;

	public RequestHandler() {
		this.serviceExecutors = new HashMap<>();
		this.serviceExecutors.put("rooms", new RoomServiceExecutor());
		this.serviceExecutors.put("movies", new MovieServiceExecutor());
		this.serviceExecutors.put("sessions", new SessionServiceExecutor());
		this.serviceExecutors.put("users", new UserServiceExecutor());
		this.serviceExecutors.put("tickets", new TicketServiceExecutor());
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {		
		URI requestURI = httpExchange.getRequestURI();
		String contextPath = httpExchange.getHttpContext().getPath();
		
		String path = requestURI.getPath();
		path = path.replaceFirst(contextPath, "");

		System.out.println("Processing request #" + REQUEST_NUMBER + ": " + httpExchange.getRequestMethod() + " " + path);
		REQUEST_NUMBER++;
		
		String[] splittedPath = path.split("/");
		List<String> filteredSplittedPath = new ArrayList<>();
		for (int i = 0; i < splittedPath.length; i++) {
			String item = splittedPath[i];
			if(!item.equals("")) {
				filteredSplittedPath.add(item);
			}
		}
		
		boolean validOperation = false;
		if (filteredSplittedPath.size() > 0) {
			String rootCommand = filteredSplittedPath.get(0);
			IServiceExecutor serviceExecutor = this.serviceExecutors.get(rootCommand);

			if (serviceExecutor != null) {
				validOperation = true;		
				Thread thread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						serviceExecutor.execute(httpExchange);
					}
				});
				thread.start();
			}
		}

		if (!validOperation) {
			String response = "404 (Not Found)\n";
			httpExchange.sendResponseHeaders(404, response.length());

			OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
