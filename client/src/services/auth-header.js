//checks loacl storage for user item 
//if user logged with token, returns HTTP authorization header
export default function authHeader() {
	const user = JSON.parse(localStorage.getItem('user'));
	if (user && user.accessToken) {
		return { Authorization: 'Bearer ' + user.accessToken };
	} else {
		return {};
	}
}