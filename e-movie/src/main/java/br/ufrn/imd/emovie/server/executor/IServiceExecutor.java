package br.ufrn.imd.emovie.server.executor;

import com.sun.net.httpserver.HttpExchange;

@SuppressWarnings("restriction")
public interface IServiceExecutor {
	
	public void execute(HttpExchange httpExchange);
}
