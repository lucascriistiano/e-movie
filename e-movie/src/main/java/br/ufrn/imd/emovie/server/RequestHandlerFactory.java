package br.ufrn.imd.emovie.server;

import java.net.Socket;

public class RequestHandlerFactory {

	/**
	 * Creates and starts a request handler
	 * @param clientSocket
	 */
	public void createRequestHandler(Socket clientSocket) {
		new RequestHandler(clientSocket).start();
	}
	
}
