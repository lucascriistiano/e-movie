package br.ufrn.imd.emovie.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestHandler extends Thread {

	private Socket clientSocket;

	public RequestHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	@Override
	public void run() {
		try {
			System.err.println("Manipulação de requisição iniciada");
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
	
	        String s;
	        while ((s = in.readLine()) != null) {
	            System.out.println(s);
	            if (s.isEmpty()) {
	                break;
	            }
	        }
	
	        String response = "";
	        response += "<html>";
	        response += "<head>";
	        response += "<title>Exemplo</title>";
	        response += "</head>";
	        response += "<body>";
	        response += "<p>Essa eh uma pagina de exemplo.</p>";
	        response += "</body>";  
	        response += "</html>";
	        
	        out.write("HTTP/1.0 200 OK\r\n");
	        out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
	        out.write("Server: Apache/0.8.4\r\n");
	        out.write("Content-Type: text/html\r\n");
	        out.write("Content-Length: " + response.length() + "\r\n");
	        out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
	        out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
	        out.write("\r\n");
	        out.write(response);
	
//	        // Teste para verificar início da próxima requisição antes da finalização da anterior 
//	        try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
	        
	        System.err.println("Manipulação de requisição finalizada");
	        out.close();
	        in.close();
	        clientSocket.close();
	        System.err.println("Conexão finalizada");
	        
	        
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
