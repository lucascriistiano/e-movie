package br.ufrn.imd.emovie.server.executor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import br.ufrn.imd.emovie.dao.exception.DaoException;

/**
 * 
 * @author lucas cristiano
 *
 */
@SuppressWarnings("restriction")
public abstract class ServiceExecutorTemplate implements IServiceExecutorTemplate {

	public static final String CREATE_OPERATION = "create";
	public static final String UPDATE_OPERATION = "update";
	public static final String DELETE_OPERATION = "delete";
	
	public abstract String processGetFindOne(Integer id) throws DaoException;
	public abstract String processGetFindAll() throws DaoException;
	public abstract String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams);
	
	public abstract String processPostCreate(Map<String, Object> requestParams);
	public abstract String processPostUpdate(Map<String, Object> requestParams);
	public abstract String processPostDelete(Map<String, Object> requestParams);
	public abstract String processPostOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams);
	
	@Override
	public void execute(HttpExchange httpExchange) {
		this.execute(httpExchange, new ArrayList<>());
	}
	
	@Override
	public void execute(HttpExchange httpExchange, List<String> urlParams) {
		@SuppressWarnings("unchecked")
		Map<String, Object> requestParams = (Map<String, Object>) httpExchange.getAttribute("parameters");
		String requestMethod = httpExchange.getRequestMethod();

		try {
			if(requestMethod.equals("GET")) {
				processGET(httpExchange, urlParams, requestParams);
			} else {  // create or modify an existing item
				processPOST(httpExchange, urlParams, requestParams);
			}
		} catch(Exception e) {
			//TODO LOG
			System.out.println("Error on request handling");
			e.printStackTrace();
		}
	}
	
	private void processGET(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) {
		if(urlParams.size() > 0) {  // has id passed in request
			String strId = urlParams.get(0);
			
			try {
				int id = Integer.parseInt(strId);
				String resultJSON = processGetFindOne(id);
				sendResponseJSON(httpExchange, resultJSON);
			} catch(NumberFormatException e) {
				System.out.println("Invalid ID passed");
				sendEmptyResponse(httpExchange);
			} catch (DaoException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
				sendEmptyResponse(httpExchange);
			}
		} else if(requestParams.size() > 0) {
			String resultJSON = processGetOther(httpExchange, urlParams, requestParams);
			sendResponseJSON(httpExchange, resultJSON);
		} else {  // get all results					
			try {
				String resultJSON = processGetFindAll();
				sendResponseJSON(httpExchange, resultJSON);
			} catch (DaoException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
				sendEmptyResponse(httpExchange);
			}
		}
	}
	
	private void processPOST(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) {
		String operation = (String) requestParams.get("operation");
		
		if(operation != null) {
			String resultJSON;
			
			if(operation.equals(CREATE_OPERATION)) {
				resultJSON = processPostCreate(requestParams);
			} else if(operation.equals(UPDATE_OPERATION)) {
				resultJSON = processPostUpdate(requestParams);
			} else if(operation.equals(DELETE_OPERATION)) {
				resultJSON = processPostDelete(requestParams);
			} else {
				try {
					resultJSON = processPostOther(httpExchange, urlParams, requestParams);
				} catch(Exception e) {
					System.out.println("Unknown operation received. Operation: " + operation);
					sendErrorCode(httpExchange, 404);
					return;
				}
			}
			
			sendResponseJSON(httpExchange, resultJSON);
		} else {
			//TODO Implement Log
			System.out.println("Param 'operation' not received");
			JsonObject responseJson = new JsonObject();
			responseJson.addProperty("success", false);
			responseJson.addProperty("error", "Param 'operation' not received");
			sendResponseJSON(httpExchange, responseJson.toString());
		}
	}
	
	private void sendResponseJSON(HttpExchange httpExchange, String jsonString) {
		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
		responseHeaders.set("Access-Control-Allow-Origin", "*");
		
		try {
			httpExchange.sendResponseHeaders(200, jsonString.getBytes().length);

			OutputStream os = httpExchange.getResponseBody();
			os.write(jsonString.getBytes());
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendEmptyResponse(HttpExchange httpExchange) {
		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
		responseHeaders.set("Access-Control-Allow-Origin", "*");

		try {
			httpExchange.sendResponseHeaders(200, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendOperationResult(HttpExchange httpExchange, boolean success) {
		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
		responseHeaders.set("Access-Control-Allow-Origin", "*");

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("success", success);
		String jsonString = jsonObject.toString();
		
		try {
			httpExchange.sendResponseHeaders(200, jsonString.getBytes().length);

			OutputStream os = httpExchange.getResponseBody();
			os.write(jsonString.getBytes());
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String createErrorJSONResponse(String errorCause) {
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("success", false);
		responseJson.addProperty("error", errorCause);
		return responseJson.toString();
	}
	
	private void sendErrorCode(HttpExchange httpExchange, int errorCode) {
		try {
			httpExchange.sendResponseHeaders(errorCode, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
