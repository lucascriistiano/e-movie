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
import br.ufrn.imd.emovie.service.exception.ServiceException;

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
	public abstract String processGetOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) throws ServiceException, DaoException;
	
	public abstract boolean processPostCreate(Map<String, Object> requestParams);
	public abstract boolean processPostUpdate(Map<String, Object> requestParams);
	public abstract boolean processPostDelete(Map<String, Object> requestParams);
	public abstract boolean processPostOther(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams);
	
	@Override
	public void execute(HttpExchange httpExchange) {
		this.execute(httpExchange, new ArrayList<>());
	}
	
	@Override
	public void execute(HttpExchange httpExchange, List<String> urlParams) {

		@SuppressWarnings("unchecked")
		Map<String, Object> requestParams = (Map<String, Object>) httpExchange.getAttribute("parameters");
		String requestMethod = httpExchange.getRequestMethod();

		if(requestMethod.equals("GET")) {
			if(urlParams.size() > 0) {  // has id passed in request
				String strId = urlParams.get(0);
				
				try {
					int id = Integer.parseInt(strId);
					String resultJSON = processGetFindOne(id);
					sendResponseJSON(httpExchange, resultJSON);
				} catch(NumberFormatException e) {
					//TODO Implement return error code				
					throw new IllegalArgumentException("Invalid id passed");
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {  // get all results					
				try {
					String resultJSON = processGetFindAll();
					sendResponseJSON(httpExchange, resultJSON);
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {  // create or modify an existing movie
			String operation = (String) requestParams.get("operation");
			
			if(operation != null) {
				boolean result;
				
				if(operation.equals(CREATE_OPERATION)) {
					result = processPostCreate(requestParams);
				} else if(operation.equals(UPDATE_OPERATION)) {
					result = processPostUpdate(requestParams);
				} else if(operation.equals(DELETE_OPERATION)) {
					result = processPostDelete(requestParams);
				} else {
					try {
						result = processPostOther(httpExchange, urlParams, requestParams);
					} catch(Exception e) {
						System.out.println("Unknown operation passed");
						sendErrorCode(httpExchange, 404);
						return;
					}
				}
				
				sendOperationResult(httpExchange, result);
			} else {
				System.out.println("Operation param not passed");
				sendOperationResult(httpExchange, false);
			}
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
	
	private void sendErrorCode(HttpExchange httpExchange, int errorCode) {
		try {
			httpExchange.sendResponseHeaders(errorCode, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
