package com.example.bixi.model;
//https://www.yawintutor.com/how-to-connect-multiple-databases-using-spring-boot/#:~:text=To%20connect%20multiple%20databases%2C%20each,java%20packages%20need%20be%20created.
//from: https://gbfs.velobixi.com/gbfs/en/station_information.json
public class BikeStation {

	private String name;
	private int stationId;
	private int lat;
	private int lon;
	private int bikesAvailable;
	private int eBikesAvailable;
	private int docksAvailable;
	private int brokenBikes;
	private int brokenStation;
	
	
	public BikeStation(String name, int stationId, int lat, int lon, int bikesAvailable, int eBikesAvailable,
			int docksAvailable, int brokenBikes, int brokenStation) {
		this.name = name;
		this.stationId = stationId;
		this.lat = lat;
		this.lon = lon;
		this.bikesAvailable = bikesAvailable;
		this.eBikesAvailable = eBikesAvailable;
		this.docksAvailable = docksAvailable;
		this.brokenBikes = brokenBikes;
		this.brokenStation = brokenStation;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStationId() {
		return stationId;
	}
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}



	public int getBikesAvailable() {
		return bikesAvailable;
	}



	public void setBikesAvailable(int bikesAvailable) {
		this.bikesAvailable = bikesAvailable;
	}



	public int geteBikesAvailable() {
		return eBikesAvailable;
	}



	public void seteBikesAvailable(int eBikesAvailable) {
		this.eBikesAvailable = eBikesAvailable;
	}



	public int getDocksAvailable() {
		return docksAvailable;
	}



	public void setDocksAvailable(int docksAvailable) {
		this.docksAvailable = docksAvailable;
	}



	public int getBrokenBikes() {
		return brokenBikes;
	}



	public void setBrokenBikes(int brokenBikes) {
		this.brokenBikes = brokenBikes;
	}



	public int getBrokenStation() {
		return brokenStation;
	}



	public void setBrokenStation(int brokenStation) {
		this.brokenStation = brokenStation;
	}
	
	
}
