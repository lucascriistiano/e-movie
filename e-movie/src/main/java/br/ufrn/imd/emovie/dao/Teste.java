/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * @author joao
 *
 */
public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logger logger = Logger.getLogger(Teste.class);
		BasicConfigurator.configure();
		logger.debug("Teste Log4j.");
	}

}
