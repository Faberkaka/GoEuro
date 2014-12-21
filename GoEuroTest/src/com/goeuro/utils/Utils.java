package com.goeuro.utils;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.goeuro.bean.TripDetails;

public class Utils {

	/**
	 * This maps json to beans using Jackson lib.
	 * 
	 * @param  jsonString the string containing all json array elements
	 * @return List<TripDetails> the list of objects achieved after the mapping 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static List<TripDetails> convertJsonToBean(String jsonString)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		List<TripDetails> tripResults = objectMapper.readValue(
				jsonString,
				objectMapper.getTypeFactory().constructCollectionType(
						List.class, TripDetails.class));
		return tripResults;
	}
}
