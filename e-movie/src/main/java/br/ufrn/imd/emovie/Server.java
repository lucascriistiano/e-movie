package br.ufrn.imd.emovie;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

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
public class Server {

	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
	
	private static final int PORT = 8000;
	public static final Semaphore writeSemaphore = new Semaphore(1, true);
	
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        try {
	        HttpContext context = server.createContext("/emovie", new RequestHandler());
	        context.getFilters().add(new ParameterFilter());
	        server.setExecutor(null); // creates a default executor
	        server.start();
	    	
	        LOGGER.info("Servidor iniciado na porta " + PORT);
        } catch(PersistenceException e) {
        	LOGGER.error("Não foi possível conectar com a base de dados. Verifique se o servidor de dados se encontra ativo.", e);
        }
    }

}