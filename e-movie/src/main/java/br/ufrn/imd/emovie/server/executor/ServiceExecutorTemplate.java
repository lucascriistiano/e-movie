package br.ufrn.imd.emovie.server.executor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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

	private static final Logger LOGGER = Logger.getLogger(ServiceExecutorTemplate.class.getName());
	
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
			LOGGER.error("Error on request handling: " + e.getMessage(), e);
			sendEmptyResponse(httpExchange);
		}
	}
	
	private void processGET(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) {
		try {
			if(urlParams.size() > 0) {  // has id passed in request
				String strId = urlParams.get(0);
				int id = Integer.parseInt(strId);
				String resultJSON = processGetFindOne(id);
				sendResponseJSON(httpExchange, resultJSON);
			} else if(requestParams.size() > 0) {
				String resultJSON = processGetOther(httpExchange, urlParams, requestParams);
				sendResponseJSON(httpExchange, resultJSON);
			} else {  // get all results					
				String resultJSON = processGetFindAll();
				sendResponseJSON(httpExchange, resultJSON);
			}
		} catch(NumberFormatException e) {
			LOGGER.warn("Invalid ID format passed", e);
			sendEmptyResponse(httpExchange);
		} catch (DaoException e) {
			LOGGER.error(e.getMessage(), e);
			sendEmptyResponse(httpExchange);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			sendEmptyResponse(httpExchange);
		}
	}
	
	private void processPOST(HttpExchange httpExchange, List<String> urlParams, Map<String, Object> requestParams) {
		try {
			String operation = (String) requestParams.get("operation");
			
			String resultJSON;
			if(operation != null) {			
				if(operation.equals(CREATE_OPERATION)) {
					resultJSON = processPostCreate(requestParams);
				} else if(operation.equals(UPDATE_OPERATION)) {
					resultJSON = processPostUpdate(requestParams);
				} else if(operation.equals(DELETE_OPERATION)) {
					resultJSON = processPostDelete(requestParams);
				} else {
					resultJSON = processPostOther(httpExchange, urlParams, requestParams);
				}
			} else {
				LOGGER.warn("Param 'operation' not received");
				resultJSON = createErrorJSONResponse("Param 'operation' not received");
			}
			
			sendResponseJSON(httpExchange, resultJSON);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			String resultJSON = createErrorJSONResponse(e.getMessage());
			sendResponseJSON(httpExchange, resultJSON);
		}
	}
	
	public String createErrorJSONResponse(String errorCause) {
		JsonObject responseJson = new JsonObject();
		responseJson.addProperty("success", false);
		responseJson.addProperty("error", errorCause);
		return responseJson.toString();
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
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	private void sendEmptyResponse(HttpExchange httpExchange) {
		Headers responseHeaders = httpExchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "application/json");
		responseHeaders.set("Access-Control-Allow-Origin", "*");

		try {
			httpExchange.sendResponseHeaders(200, 0);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
