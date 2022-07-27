import React, { Component, useEffect } from "react";
import L from 'leaflet';
import UserService from "../services/user.service";
import BixiService from "../services/bixi.service";
import { connect } from "react-redux";
import { MapContainer, TileLayer, Marker, Popup, Tooltip } from 'react-leaflet'
import { Button, ButtonGroup } from 'reactstrap';
import 'leaflet/dist/leaflet.css';
import "../style/home.css";
import FaveService from "../services/favourites.service";
import favouritesService from "../services/favourites.service";

class Home extends Component {
	constructor(props) {
		super(props);
		this.state = {
			content: "",
			myPosition: "",
			positionEnabled: false,
			stationsClose: '',
			activePage: "home",
			listFaves: [],
			noFavesSet: true,
			setFavesDuringFirstRun: true
		};
		this.onRadioBtnClick = this.onRadioBtnClick.bind(this);
	};

	//state for top button
	onRadioBtnClick(rSelected, email) {
		this.setState({ rSelected });
		this.setFaves(email);
	}



	redIcon = new L.Icon({
		iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
		shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
		iconSize: [25, 41],
		iconAnchor: [12, 41],
		popupAnchor: [1, -34],
		shadowSize: [41, 41]
	});

	greyIcon = new L.Icon({
		iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-grey.png',
		shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
		iconSize: [25, 41],
		iconAnchor: [12, 41],
		popupAnchor: [1, -34],
		shadowSize: [41, 41]
	});


	//checks if position is enabled
	shouldGetPosition = () => {
		if ("geolocation" in navigator) {
			console.log("Available");
			this.setPosition();
			//set map icon to red
			this.setState({ icon: this.redIcon });
			this.setState({ positionEnabled: true });
			return true;
		} else {
			console.log("Not Available");
			return false;
		}
	};

	//gets user position
	setPosition = function () {
		navigator.geolocation.getCurrentPosition((position) => {
			let testing = true;
			let longitude, latitude;
			if (testing) {
				longitude = -73.572519;
				latitude = 45.506621;
			}
			else {
				latitude = position.coords.latitude;
				longitude = position.coords.longitude;
			}
			this.setState({ myPosition: [latitude, longitude] });
		});
	};

	//set favourites
	async setFaves(email) {
		//fetch favourites
		let res = await favouritesService.getFaves(email);
		let listOfFaves = res.data;
		console.log(listOfFaves);
		//fetch bikes
		let bixiArray = await BixiService.createBikes();
		this.setState({ bikes: bixiArray });
		//check if there is a list of faves to render
		if (listOfFaves.length === 0) {    //no faves set
			this.setState({ noFavesSet: true });
		}
		else {   //faves set
			this.setState({ noFavesSet: false });
			this.setState({ listFaves: [] }); //resets list
			//creates array of bikes that are favorites
			for (const fave of listOfFaves) {
				let currentBike = (this.state.bikes).find(elem => elem['id'] == fave['bikeNumber']);
				this.setState({ listFaves: [...this.state.listFaves, currentBike] });
			}
			//console.log(this.state.listFaves);
		}

	}

	//clicking a button
	async faveButtonClicked(email, id) {
		let response = await FaveService.getFaves(email);
		let currentFaves = await response.data;
		if (currentFaves.length === 0 || !Array.isArray(currentFaves)) {
			FaveService.addFave(email, id);
			return;
		}
		else {
			this.addOrRemoveFave(currentFaves, email, id);
		}
		this.setFaves(email);
	}

	//get fave button colour
	isFaveCloseBy(id) {
		if (this.state.listFaves.length > 0) {
			if (this.state.listFaves.some(f => f['id'] == id)) {
				//console.log(this.state.listFaves);
				return "danger";
			}
			else {
				//console.log(this.state.listFaves);
				return "secondary";
			}
		}
		else {
			return 'secondary';
		}
	}
	//add or remove a favourte after button click
	addOrRemoveFave(faves, email, id) {
		if (faves.some(e => e.bikeNumber == id)) {
			console.log("Remove fave")
			FaveService.removeFave(email, id);
		}
		else if (!faves.some(e => e.bikeNumber == id)) {
			console.log("add fave")
			FaveService.addFave(email, id)
		}
	}

	//used to calculate rad
	rad = function (x) {
		return x * Math.PI / 180;
	};

	//get distance between two coordintes
	getDistance = function (p1, p2) {
		var R = 6378137; // Earth’s mean radius in meter
		var dLat = this.rad(p2[0] - p1[0]);
		var dLong = this.rad(p2[1] - p1[1]);
		var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
			Math.cos(this.rad(p1[0])) * Math.cos(this.rad(p2[0])) *
			Math.sin(dLong / 2) * Math.sin(dLong / 2);
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		var d = R * c;
		return d; // returns the distance in meter
	};

	componentDidMount = async () => {
		this.shouldGetPosition();   //check if user enabled position sharing
		//id, street, lat, lon, totalAvail, totalAvailEBike, totalDocks
		let bixiArray = await BixiService.createBikes();
		this.setState({ bikes: bixiArray });
		if (this.shouldGetPosition()) {
			for (const bike of this.state.bikes) {
				if (this.getDistance(this.state.myPosition, [bike['lat'], bike['lon']]) < 400) {
					this.setState({ stationsClose: [...this.state.stationsClose, bike] });
				}
			}
			console.log(this.state.stationsClose);
		}
		UserService.getPublicContent().then(
			response => {
				this.setState({
					content: response.data
				});
			},
			error => {
				this.setState({
					content:
						(error.response && error.response.data) ||
						error.message ||
						error.toString()
				});
			}
		);
	}
	render() {
		const { user: currentUser } = this.props;  //user info
		if (this.state.positionEnabled === true) {
			if (!this.state.bikes || this.state.stationsClose.length <= 0) {
				return null;
			}
			return (
				<div className="container">

					<header className="jumbotron">
						<h3>{this.state.content}</h3>
					</header>

					{/*top buttons*/}
					<ButtonGroup className="control-buttons">
						<Button checked color="secondary" onClick={() => this.onRadioBtnClick("home", currentUser.email)} active={this.state.rSelected === "home"}>My Location</Button>
						<Button color="secondary" onClick={() => this.onRadioBtnClick("fav", currentUser.email)} active={this.state.rSelected === "fav"}>My Favorites</Button>
					</ButtonGroup>
					{/* <div id="map" style={{ height: 180 }}></div> */}
					{this.state.rSelected !== "fav"
						?
						<MapContainer center={[this.state.myPosition[0], [this.state.myPosition[1]]]} zoom={17} scrollWheelZoom={false} id="mapContainer">
							<TileLayer
								attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
								url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
							/>
							{/* user position*/}

							<Marker position={[this.state.myPosition[0], [this.state.myPosition[1]]]} icon={this.redIcon}>
								<Popup closeButton={false}>
									You are here!
								</Popup>
							</Marker>
							{/* bike station positions*/}
							{
								this.state.stationsClose.map(bikes => {
									return (
										<Marker position={[bikes['lat'], bikes['lon']]} icon={this.greyIcon}>
											<Tooltip interactive={true} permanent="true">
												Bikes: {bikes['totalAvail'] + bikes['totalAvailEBike']} <br /> Docks: {bikes['totalDocks']}<br />
												<Button onClick={() => this.faveButtonClicked(currentUser.email, bikes['id'])} outline color={this.isFaveCloseBy(bikes['id'], currentUser.email)} size="sm" className="favouriteButton">Favorite</Button>
											</Tooltip>
										</Marker>
									)
								})
							}
						</MapContainer>
						:
						<MapContainer center={[this.state.myPosition[0], [this.state.myPosition[1]]]} zoom={10} scrollWheelZoom={false} id="mapContainer">
							<TileLayer
								attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
								url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
							/>
							{/* user position*/}
							{this.state.noFavesSet
								?
								<Marker position={[this.state.myPosition[0], [this.state.myPosition[1]]]} icon={this.redIcon}>
									<Popup closeButton={false}>
										No favourites to show!
									</Popup>
								</Marker>
								:
								this.state.listFaves.map(faves => {
									return (
										<Marker position={[faves["lat"], faves["lon"]]} icon={this.redIcon}>
											<Tooltip interactive={true} permanent="true">
												Bikes: {faves['totalAvail'] + faves['totalAvailEBike']} <br /> Docks: {faves['totalDocks']}<br />
												<Button onClick={() => this.faveButtonClicked(currentUser.email, faves['id'])} outline color="secondary" size="sm" className="favouriteButton">Favorite</Button>
											</Tooltip>
										</Marker>
									)
								})
							}
						</MapContainer>
					}
				</div>
			);
		}
	}
}
function mapStateToProps(state) {
	const { user } = state.auth;
	return {
		user,
	};
}
export default connect(mapStateToProps)(Home);