/**
 * GO EURO - JAVA DEVELOPER TEST
 * 
 * @author Marco Cerro
 * 
 */

package com.goeuro.application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import au.com.bytecode.opencsv.CSVWriter;

import com.goeuro.bean.TripDetails;
import com.goeuro.exception.ServiceException;
import com.goeuro.utils.Utils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class GoEuroApp { 
	
	private static final String FILE_NAME = "goeuro.csv";
	private static final String FILE_PATH = ".";
	private static final String BASE_REST_SERVICE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
	private static final String[] COLUMNS_NAME = {"_ID", "NAME", "TYPE", "LATITUDE", "LONGITUDE"};
	private static final Logger logger = Logger.getLogger(GoEuroApp.class);
	
	public static void main(String[] args) {

		if(args.length != 1){
			logger.error("The program accepts exactly one input argument");
		} else {	
			logger.info("***** Starting Go Euro Test *****");
			String jsonResponse;
			try {
				jsonResponse = callGoEuroRestService(args[0]);
				List<TripDetails> tripResults = Utils.convertJsonToBean(jsonResponse);
				generateCsvForTripDetails(tripResults, FILE_PATH + "//" + FILE_NAME);
			} catch (ServiceException serviceException) {
				logger.error("CallGoEuroRestService: Error calling webservice. " + serviceException.getMessage());
			} catch (JsonParseException jsonParseException) {
				logger.error("ConvertJsonToBean: exception parsing the json");
			} catch (JsonMappingException jsonMappingException) {
				logger.error("ConvertJsonToBean: exception mapping the json into bean");
			} catch (IOException ioexception) {
				logger.error("ConvertJsonToBean: json mapper I/O exception");
			}
		}
	}
	
	/**
	 * This method call the rest service at the following address:
	 * http://api.goeuro.com/api/v2/position/suggest/en/
	 * returning a string of all json results
	 * 
	 * @param queryParam: the input argument passed when the program is started
	 *        (e.g java -jar GoEuroTest.jar "STRING" --> queryParam = STRING)
	 * 
	 * @return A string represents all the elements of the json array 
	 *         returned by the service 
	 *         
	 * @throws ServiceException this exception happens when for some
	 *         reasons the service response is different from 200 (OK)
	 */
	public static String callGoEuroRestService(String queryParam) throws ServiceException{
		
		Client client = Client.create();
		
		logger.info("***** Calling the webservice from this URL : " + BASE_REST_SERVICE_URL + " *****");
		logger.info("***** Query Parameter: '" + queryParam + "' *****");
		
		WebResource resource = client.resource(BASE_REST_SERVICE_URL + queryParam);
		ClientResponse response = resource.accept("application/json")
										  .get(ClientResponse.class);

		if (response.getStatus() == 200) {
			String jsonResponse = response.getEntity(String.class);
			
			logger.info("***** Json results found: " + jsonResponse + " *****");	
			
			return jsonResponse;
		} else {
			throw new ServiceException("Response status : " + response.getStatus());
		}
	}
	
	/**
	 * This method generates a CSV file fulfilling it with
	 * the properties of objects passed in input (tripDetails) to the function
	 * 
	 * @param tripDetails a list of objects mapping the json found
	 * @param fileName the file name (including the path)
	 */
	public static void generateCsvForTripDetails(List<TripDetails> tripDetails, String fileName){
		String [] emptyRowCells = new String[COLUMNS_NAME.length];
		
		CSVWriter writer = null;

		try {
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"),';');
			writer.writeNext(COLUMNS_NAME);
			writer.writeNext(emptyRowCells);
			
			for(TripDetails trdet : tripDetails){	
				String [] recordTripDetails = new String[COLUMNS_NAME.length];
				
				if(trdet.get_id() != null){
					recordTripDetails[0] = String.valueOf(trdet.get_id());
				}
				
				recordTripDetails[1] = trdet.getName();
				recordTripDetails[2] = trdet.getType();
				
				if(trdet.getGeo_position() != null){
					if(trdet.getGeo_position().getLatitude() != null) {
						recordTripDetails[3] = String.valueOf(trdet.getGeo_position().getLatitude());
					}
					if(trdet.getGeo_position().getLongitude() != null){
						recordTripDetails[4] = String.valueOf(trdet.getGeo_position().getLongitude());		
					}
				} 
				
				writer.writeNext(recordTripDetails);
			}	
			logger.info("***** CSV file " + FILE_NAME + " successfully generated into the jar execution directory *****");
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			logger.error("UnsupportedEncodingException: it is not possible to close the file");
		} catch (FileNotFoundException fileNotFoundException) {
			logger.error("FileNotFoundException: it is not possible to close the file");
		} finally {
			try {
				writer.close();
			} catch (IOException ioexception) {
				logger.error("IOException: it is not possible to close the file");
			}
		}
	}
}
