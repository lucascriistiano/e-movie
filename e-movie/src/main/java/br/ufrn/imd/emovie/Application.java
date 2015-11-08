package br.ufrn.imd.emovie;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

import br.ufrn.imd.emovie.server.ParameterFilter;
import br.ufrn.imd.emovie.server.RequestHandler;

@SuppressWarnings("restriction")
public class Application {

    public static void main(String[] args) throws Exception {
        int port = 8000;
        
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        HttpContext context = server.createContext("/e-movie", new RequestHandler());
        context.getFilters().add(new ParameterFilter());
        server.setExecutor(null); // creates a default executor
        server.start();
    	
        System.out.println("Started server on port " + port);
    }

}