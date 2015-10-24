package br.ufrn.imd.emovie.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

	private int port;
	private ServerSocket serverSocket;
	private RequestHandlerFactory requestHandlerFactory;
	
	public WebServer(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(this.port);
		
		this.requestHandlerFactory = new RequestHandlerFactory();
	}
    
	public void start() throws IOException {
	    System.err.println("Servidor iniciado na porta: " + this.port);
	
	    while (true) {
	        Socket clientSocket = serverSocket.accept();
	        System.err.println("Novo cliente conectado");
	        
	        this.requestHandlerFactory.createRequestHandler(clientSocket);
	    }
	}
	
}
