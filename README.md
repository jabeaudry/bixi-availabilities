# Bixi Availabilities
An application to track nearby bike sharing docks, using `React`, `Java Spring` and `MySQL`.

<img width="917" alt="Screenshot 2022-07-26 221805" src="https://user-images.githubusercontent.com/56971054/181146232-d08a251d-4505-411a-8401-9691e06f2ae5.png">

## About the Project
When I'm in a rush, I never take the time to check the status of bike sharing docks near my house. And more often than not, it's too late when I realize all the docks around me are empty. I decided enough was enough and built a watchboard with all the information I need, just a click away. I also implemented a "Favorites" tab to track the bike stations near my work and ensure I'll be able to dock. Finally, I added an authentication process to share the app with my coworker!

## Implementation

**Front-end: [React](https://reactjs.org/)**  
The React app is located at `/client/`.

**Back-end: [Java Spring](https://spring.io/), [MySQL](https://www.mysql.com/), [Bixi GBFS Feed](https://gbfs.velobixi.com/gbfs/gbfs.json?_ga=2.235037897.91692927.1658890311-543543907.1657057596)**  
The back-end is located at `/src/`. This application relies on Spring Security for authorization and authentication purposes. The users, their roles and their saved bike stations are stored using the model–view–controller framework (Spring MVC) to a MySQL database. Finally, the status of the stations is fetched from the GBFS feed every minute.

## Componenents

### Map

<img width="251" alt="image" src="https://user-images.githubusercontent.com/56971054/181148847-42d7f3a2-3c90-4f39-bbfd-06f8893407ee.png">

The user's location is fetched using the HTML Geolocation API. Nearby stations are shown on the [Leaflet Map](https://leafletjs.com/) with the number of available bikes and empty docks. 

### Favorites

<img width="468" alt="Screenshot 2022-07-26 220653" src="https://user-images.githubusercontent.com/56971054/181144853-2f965aa1-5f5a-424a-b442-7130c2fa8bf0.png"><img width="152" alt="Screenshot 2022-07-26 221113" src="https://user-images.githubusercontent.com/56971054/181145328-fe371885-fd92-4768-ad42-c35710a91f03.png">

When the user successfully logs in, they can add and remove bike stations to a list of Favorites. A red button indicates that the station has already beeen added to the list. To quickly view the saved stations, the user can click on the favorite tab.

### Accounts

<img width="430" alt="Screenshot 2022-07-26 220941" src="https://user-images.githubusercontent.com/56971054/181145152-24ffc2fb-41e9-4f30-b327-8ffa5fe85f56.png">

Registration and logging in is required to use the application. The passwords are encrypted using Java Security and the authorization process utilizes JWT.

