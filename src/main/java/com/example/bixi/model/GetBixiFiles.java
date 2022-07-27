//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import org.apache.spark.api.java.JavaRDD;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

//package com.example.bixi.model;
//
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Scanner;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//public class GetBixiFiles {
//	  public ArrayList<Object> getBixiFile(URL url) {
//
//	        try {
//
//	            URL url = new URL("https://gbfs.velobixi.com/gbfs/en/station_information.json");
//
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
//	                return listdata;
//	                
//	            }
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        
//	        
//	        return null;
//	    }
//	  }
//	   
//
//  
//  
//
//
//
//
//

//	
//	
//	
//	    //fetch and read json file
//	    public ArrayList<Object> getBixiFile(URL url) {
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
//	                return listdata;
//	                
//	            }
//
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        
//	        
//	        return null;
//	    }
//	    
//	    
//	    //get columns
//	    public String[] getBixiInfoColumns() {
//	    	return (new String[] {"eightd_active_station_services", "eightd_has_available_keys", "is_charging", "is_installed", "is_renting", "is_returning", "last_reported", "num_bikes_available", "num_bikes_disabled", "num_docks_available", "num_docks_disabled", "num_ebikes_available","station_id"});
//	    }
//	    
//	    //get column
//	    public String[] getBixiStatusColumns() {
//	    	return (new String[] {"capacity","eightd_has_key_dispenser","eightd_station_services","electric_bike_surcharge_waiver","external_id","has_kiosk","is_charging","lat","lon","name","rental_methods","short_name","station_id"});
//	    }
//	    
//	    public static JavaRDD<Object> createDF() throws MalformedURLException {
//	    	 
//	    	 
//	    		URL url = new URL("https://gbfs.velobixi.com/gbfs/en/station_information.json");
//				URL url2 = new URL("https://gbfs.velobixi.com/gbfs/en/station_status.json");
//				JavaRDD<Object> infoInput = javaSparkContext().parallelize(getBixiFile(url));
//				
//				
//				return infoInput;
//	    	 
//	    }
//	    public static void main(String[] args) throws MalformedURLException {
//	    	createDF();
//	    	System.out.print("hello");
//	    }
//	    }
//	    

