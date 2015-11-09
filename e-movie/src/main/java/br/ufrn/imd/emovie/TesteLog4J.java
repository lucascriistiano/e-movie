/**
 * 
 */
package br.ufrn.imd.emovie;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * @author joao
 *
 */
public class TesteLog4J {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(TesteLog4J.class);
		BasicConfigurator.configure();
		logger.debug("Teste Log4j.");
		logger.info("Info message test");
		logger.error("Error message");
	}

}
