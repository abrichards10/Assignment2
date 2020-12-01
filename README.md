# Assignment2

 /* DESCRIPTION:
    So we somehow have to relate the information in attractions to the information in roads -->
    - roads.csv gives us: the distance from one location to another, how far it is, and how long it takes
    - attractions.csv gives us: the attraction, the location
    These two files are related through location. In order to go to every attraction given,
    we have to take these steps:
    1) Read if any attractions exist --> if they don't, then we can skip reading through attractions
        altogether.
    2) If attractions exist, we need to add the attraction locations to the roads included on our trip
        - What I mean by this, is that we are given a start and end and attractions from the user, and we
        need to search through the attractions to find their location, and add them to the places we need to visit
    3) So read though attractions and add the locations to the list (which I totally talked about before
        oh btw there's a list)
    4) Use Dijkstras algorithm to search through roads and the given locations of the attractions, and
        find the shortest distance between all of the locations which by this point would be added to a list/ array

    5) In Dijkstras algorithm, we have unsettled & settled nodes --> the unsettled nodes will be the ones not visited,
        we need to visit the locations in roads and attractions, and find the shortest path between the possible pathways
        while including the attractions we need to visit. So the way I would do it is I would first use BufferedReader
        to take in the info of both attractions and roads
    6) Dijkstra's algorithm uses edges and vertices --> the edges will store the miles between two locations, while the
        vertices will store the name/ id of the location
         While we will Search through the attractions for their names
    6) We only want to visit each line in each file once so we have the shortest runtime


    * Important Notes:
        - The length (number of roads) of roads.csv is 522
        - The number of attractions is 144 - (The first line just says, Attraction, Location)
        - We need to keep track of the number of locations we visit: 522
        - We need to keep track of the number of miles (star)treked: 522
        - We need to keep track of the number of miles travelled so far (using Dijkstras algorithm we need to check
        multiple routes by comparing the current to previous)
        - We need to store the number of cities visited in order, we can do so through an ArrayList
        - Example of how destination is represented:
            d[0,1] = 2 means the weight from distances 0 to 1 is 2.

            EXAMPLE:
                d[s, v] > d[s, i] + d[i, v]

                [source to destination] > [source to in-between source] + [in-between source to destination]
                - means that the destination from the source to the destination
                is greater than the distance from the source to the intermediate source
                plus the distance from the intermediate source to the destination

                - our s would be the starting city
                - our v would be the ending city
                - our i would be all the locations we have to visit on the way

        - We have DESTINATION NODES (intermediate/in-between nodes) which are the nodes we visit to find the
         shortest path between ALL of the nodes


        - What data structures will represent the data in the file: "attractions.csv"?
            - Answer: I will use a HashMap to represent the data in attractions
            - HashMap<String, String> attraction = new HashMap<String, String> 145 (number of lines)

        - What data structures will represent the data in the file: "roads.csv"?
            - Well first I will make public class called Routes to store the locations, miles and minutes
            - Answer: I will use an ArrayList to store the data in roads of type Routes
            - ArrayList<Routes> routes = new ArrayList<Routes> (522) --> number of lines

        - What algorithms will you use to find the shortest route through all of the possible cities and events?
            - Answer: I will implement Dijkstra's algorithm because it finds the shortest path between two nodes while
            keeping track of the edges and the weight of the edges (it's directed --> one route and weighted --> keeps
            track of the miles)

        - What classes would you use?
            - Answer: I would use a Routes class that would store the locations, miles and minutes of each node

        - What public or private functions would they have?
            - Answer: route (obviously) which will return a List of Strings that are the starting/ending cities and
             attractions. It will call another public function that continuously updates the shortest path using
              Dijkstra's algorithm --> update method will perform the algorithm work
            - I will also include a function that will read the files and separate them through their commas
            - This will need to throw an exception because it never allows me to read files :(

        - Which class/ function contains the 'route' function?
            - Answer: the route function is stored in the class Road_Trip_Plan

        - How will I store the data from a distance file?
            - Answer: The distance file (roads) stores the start/end points as well as the miles and minutes
            It will be read with a FileReader in a separate function called readFiles
            - readFiles will take in two file names, and read them through BufferedReader
            - I will need a HashMap that stores a boolean containing information on which cities are visited in order

        - How will I store the data from the instance file?
            - Answer:

        Really Rough Skeleton Code:

        // known imports:
        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException // to throw the filereader exception

        // known needed data structures
        HashMap<String, String> attractions = new HashMap<String, String> (145)
        HashMap<String, String> roads_travelled = new HashMap<String, String> (522)
        ArrayList<Routes> routes = new ArrayList<Routes>(522)
        // ArrayList of type Routes (the class mentioned earlier) that stores the Objects from roads.csv


    // Update method for routes function
    // THIS uses Dijkstras algorithm to calculate the shortest route, it also
    keeps track of the previous city(s?) to find the most efficient route
    We use a stack to keep track of the cities, and the loop in the algorithm will end
    once the stack is empty.

     */
