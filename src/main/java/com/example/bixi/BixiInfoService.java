//package com.example.bixi;
//
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BixiInfoService {
//	
//	 @Autowired
//	JavaSparkContext sc;
//
//	 
//	 public getBikes(List<String> wordList) throws MalformedURLException {
//
//		 String [] columns = new String[] {"capacity","eightd_has_key_dispenser","eightd_station_services","electric_bike_surcharge_waiver","external_id","has_kiosk","is_charging","lat","lon","name","rental_methods","short_name","station_id"};
//			String [] columns2 = new String[] {"eightd_active_station_services", "eightd_has_available_keys", "is_charging", "is_installed", "is_renting", "is_returning", "last_reported", "num_bikes_available", "num_bikes_disabled", "num_docks_available", "num_docks_disabled", "num_ebikes_available","station_id"};
//			URL url = new URL("https://gbfs.velobixi.com/gbfs/en/station_information.json");
//			URL url2 = new URL("https://gbfs.velobixi.com/gbfs/en/station_status.json");
//			JSONObject[] x = getBixiFile(url);
//			
//			
//			
//			
////			List<String> strings = x.stream()
////					   .map(object -> Objects.toString(object, null))
////					   .collect(Collectors.toList());
//			JavaRDD<String> infoInput = sc.parallelize(strings);
//
//	    }
//
//	    
//	  //fetch and read json file
//	    public static JSONObject[] getBixiFile(URL url) {
//
//	        try {
//	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	            conn.setRequestMethod("GET");
//	            conn.connect();
//
//	            //Getting the response code
//	            int responsecode = conn.getResponseCode();
//
//	            if (responsecode != 200) {
//	                throw new RuntimeException("HttpResponseCode: " + responsecode);
//	            } else {
//
//	                String inline = "";
//	                Scanner scanner = new Scanner(url.openStream());
//
//	                //Write all the JSON data into a string using a scanner
//	                while (scanner.hasNext()) {
//	                    inline += scanner.nextLine();
//	                }
//	                
//	                //Close the scanner
//	                scanner.close();
//
//	                //Using the JSON simple library parse the string into a json object
//	                JSONParser parse = new JSONParser();
//	                JSONObject data_obj = (JSONObject) parse.parse(inline);
//	                //Create JSON Object
//	                JSONObject obj = (JSONObject) data_obj.get("data");
//	                //Create JSON Array of stations
//	                JSONArray arrStations = (JSONArray) obj.get("stations");
//	                
//	                //Creating an empty ArrayList of type Object  
//	                ArrayList<Object> listdata = new ArrayList<Object>();  
//	                if (arrStations != null) {   
//	                    
//	                    //Iterating JSON array  
//	                    for (int i=0;i<arrStations.size();i++){   
//	                          
//	                        //Adding each element of JSON array into ArrayList  
//	                        listdata.add(arrStations.get(i));  
//	                    }   
//	                }
//	                JSONObject[] json = new JSONObject[listdata.size()];
//	                listdata.toArray(json);
//	                return json;
//	            }
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        
//	        
//	        return null;
//	    }
//}
