class BixiService {

	async getBixiInfo() {
		let url = 'https://gbfs.velobixi.com/gbfs/en/station_information.json';
		try {
			let res = await fetch(url);
			let data = await res.json();
			//console.log(data);
			return data;
		} catch (error) {
			console.log(error);
		}
	};

	async getBixiStatus() {
		let url = 'https://gbfs.velobixi.com/gbfs/en/station_status.json';
		try {
			let res = await fetch(url);
			let data = await res.json();
			//console.log(data);
			return data;
		} catch (error) {
			console.log(error);
		}
	};


	async createBikes() {
		let json1 = await this.getBixiInfo();
		let json11 = await json1['data']['stations'];
		let json2 = await this.getBixiStatus();
		let json22 = await json2['data']['stations'];
		let bikes = [];
		for (let i = 0; i < Object.keys(json22).length; i++) {
			bikes.push({
				id: json11[i]["station_id"],
				street: json11[i]["name"],
				lat: json11[i]["lat"],
				lon: json11[i]["lon"],
				totalAvail: json22[i]["num_bikes_available"],
				totalAvailEBike: json22[i]["num_ebikes_available"],
				totalDocks: json22[i]["num_docks_available"],
			});
		}
		console.log(bikes);
		return bikes;
	};
}
export default new BixiService();