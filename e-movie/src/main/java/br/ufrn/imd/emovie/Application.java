package br.ufrn.imd.emovie;

import java.net.ConnectException;
import java.net.InetSocketAddress;

import javax.persistence.PersistenceException;

import org.hibernate.exception.JDBCConnectionException;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

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
        
        try {
	        HttpContext context = server.createContext("/emovie", new RequestHandler());
	        context.getFilters().add(new ParameterFilter());
	        server.setExecutor(null); // creates a default executor
	        server.start();
	    	
	        System.out.println("[LOG] Started server on port " + PORT);
        } catch(PersistenceException e) {
        	System.out.println("[LOG] Não foi possível conectar com a base de dados. Verifique se o servidor de dados se encontra ativo.");
        }
    }

}