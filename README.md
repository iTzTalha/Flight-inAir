# Flight inAir
A flight booking app designed for users to search and book flights between cities, while airlines can log in to declare flight schedules for the day. The app supports finding the shortest route by hops or the cheapest route by cost, with optional filtering for meal availability and other customizable features.

## Features
* **Flight Registration:** Airlines can declare flights between cities with specified costs.
* **Flight Search:**
    * **Minimum Hops:** Finds the route between two cities with the least connections.
    * **Cheapest Route:** Finds the most cost-effective route between two cities.
* **Search Filters:** Allows filtering flights based on amenities (e.g., meals, excess baggage).
* **Sorting by Time:** With departure and arrival times, flights can be sorted in ascending/descending order.
* **Concurrency:** Handles multiple users searching and booking flights simultaneously.

## Sample Usage
```
register flight-> JetAir -> DEL -> BLR -> 500
register flight-> Delta -> LON -> NYC -> 2000
search flight-> DEL -> NYC

Output:
Route with Minimum Hops:
DEL to LON via Delta for 2000
LON to NYC via Delta for 2000
Total Flights = 2
Total Cost = 4000

Cheapest Route:
DEL to BLR via JetAir for 500
BLR to LON via JetAir for 1000
LON to NYC via Delta for 2000
Total Flights = 3
Total Cost = 3500
```

## Setup
1. Clone the repository:
```console 
git clone https://github.com/iTzTalha/Flight-inAir.git
```

2. Register Flights: Use the register flight command to add flights between cities with their respective prices.
 
3. Compile and run:
```console 
cd Flight-inAir
javac -d bin src/*.java
java -cp bin Driver
```

## Design & Architecture
* **Modular Design:** Separate classes for flight registration, search, and filtering.
* **In-Memory Data Structures:** No database is required; all data is stored in memory for simplicity.
* **Extensible Code:** Designed with interfaces to easily add new features without rewriting core logic.

## Expectations & Guidelines
* **Demo-Friendly:** Includes a main driver class to simulate flight registration and search functions.
* **Error Handling:** Manages edge cases gracefully and provides readable error messages.
* **Coding Standards:** Clear, maintainable, and well-structured code with a focus on readability.

## Contributing
Contributions to the Splitwise Clone project are welcome! If you find any bugs or have suggestions for improvements, feel free to open an issue or submit a pull request.

## Show Your Support
Give a ⭐️ if you like this project!
