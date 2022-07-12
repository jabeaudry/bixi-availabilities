package com.example.bixi;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		//convert json file to map
		Map<?, ?> map = objectMapper.readValue(new FileInputStream("https://gbfs.velobixi.com/gbfs/en/system_information.json"), Map.class);

		//iterate over map entries and print to console
		for (Map.Entry<?, ?> entry : map.entrySet()) {
		    System.out.println(entry.getKey() + "=" + entry.getValue());
		}

	}

}
