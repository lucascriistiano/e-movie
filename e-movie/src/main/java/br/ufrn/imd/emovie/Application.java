package br.ufrn.imd.emovie;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpContext;

import br.ufrn.imd.emovie.server.ParameterFilter;
import br.ufrn.imd.emovie.server.RequestHandler;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public class Application {

	private static final int PORT = 8000;
	
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        HttpContext context = server.createContext("/emovie", new RequestHandler());
        context.getFilters().add(new ParameterFilter());
        server.setExecutor(null); // creates a default executor
        server.start();
    	
        System.out.println("[LOG] Started server on port " + PORT);
    }

}