package br.ufrn.imd.emovie.server;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class Application {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/e-movie", new RequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    	
        System.out.println("Started server on port " + port);
    }

}