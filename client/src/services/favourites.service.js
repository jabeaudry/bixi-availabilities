
import axios from "axios";
import faveHeader from "./fave-header";
const API_URL = "http://localhost:8080/api/bike/";
class FaveService {

	//get set of favourites
	getFaves(email) {
		return axios.get(API_URL + "users/" + email + "/saves", { headers: faveHeader() })
			.then(console.log("Fetched faves!"));
	}
	//add a favourite
	addFave(email, bikeNumber) {
		return axios
			.post(API_URL + "users/" + email + "/save", { bikeNumber }, { headers: faveHeader() })
	}
	//remove a favourite
	removeFave(email, id) {
		return axios
			.delete(API_URL + email + "/saved/" + id, { headers: faveHeader() })
			.then(console.log("Favorite deleted!"));
	}
}
export default new FaveService();