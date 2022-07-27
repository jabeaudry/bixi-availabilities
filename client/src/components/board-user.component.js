import React, { Component } from "react";
import UserService from "../services/user.service";
import BixiService from "../services/bixi.service";


export default class BoardUser extends Component {

	constructor(props) {
		super(props);
		this.state = {
			content: ""
		};
		BixiService.createBikes().then(response => this.setState({ bikes: response })).catch();
	}
	render() {

		if (!this.state.bikes) {
			return null;
		}
		return (
			<div className="container">
				<div className="container">
					{console.log(this.state.bikes)}
					{this.state.bikes.map(({ id, street, lat, lon, totalAvail, totalAvailEBike, totalDocks }) => {
						// return <Station id={id} street={street} lat={lat} lon={lon} totalAvail={totalAvail} totalAvailEBike={totalAvailEBike} totalDocks={totalDocks} />
					})
					}
				</div>
			</div>
		);

	}
}