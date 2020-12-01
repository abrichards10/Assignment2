/*
- Given a Starting City
- Ending City
- List of Events/ places of interest
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.HashMap;

public class Road_Trip_Plan {

    HashMap<String, String> attractions = new HashMap<> (145);
    HashMap<String, String> roads_travelled = new HashMap<> (522);
    ArrayList<Routes> routes = new ArrayList<>(522);
    //Stack for the update function
    Stack<String> stack = new Stack<>();
    // HashMap to store what cities exist --> used in the readFiles function
    HashMap<String, Boolean> city_exists = new HashMap<>();
    // keeps track of the places visited in update function
    // String represents what node was visited, boolean represents whether or not it was visited
    HashMap<String, Boolean> visited = new HashMap<>();
    // keeps track of the lines of the file to make sure everything has only been visited once
    // Used in update function
    ArrayList<Integer> check = new ArrayList<>(522);
    HashMap<String,Integer> miles = new HashMap<>();
    int mile = 0;
    ArrayList<String> total_cities = new ArrayList<>();

    public class Routes{
        String first_location;
        String second_location;
        int miles;
        int minutes;

        public Routes(String first_loc, String second_loc, int mile, int min){
            first_location = first_loc;
            second_location = second_loc;
            miles = mile;
            minutes = min;
        }
    }

    public void readFiles(String first_file, String second_file){
        // This function reads the files and parses them
        // This is NOT what the function is supposed to do, but this is just a basic
        // parsing function to represent reading each file
        // In the real version I would split each line by a comma, parse the ints and set each
        // character to lowercase in the array storing the values
        // This function just reads and prints the files to get started

        try{
            BufferedReader br = new BufferedReader(new FileReader(first_file));
            String line = "";

            // Opens, splits, reads & adds information from roads.csv
            while((line = br.readLine()) != null){
                String[] temp = line.split(","); // split at the comma
                // Create a new Object --> route that stores one line of info
                // reads: starting point, ending point, miles, minutes
                Routes r1 = new Routes(temp[0].toLowerCase(), temp[1].toLowerCase(), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
                // put the temporary values (of the start/end potential cities) into the HashMap for cities visited
                city_exists.put(temp[0].toLowerCase(), true);
                city_exists.put(temp[1].toLowerCase(), true);
                // Add the new info to the ArrayList that stores the objects from the first file (roads.csv)
                routes.add(r1);
            }

            br = new BufferedReader((new FileReader(second_file)));

            while(((line = br.readLine()) != null)){
                String[] temp = line.split(",");
                attractions.put(temp[0].toLowerCase(), temp[1].toLowerCase());
            }
        }

        catch(FileNotFoundException e){
            System.out.println(e);
            System.out.println("File error --> exit system");
            System.exit(1);
        }
        catch (Exception e) {
            System.out.println(e);
            System.out.println("File error --> exit system");
            System.exit(1);
        }
    }

    public List<String> route(String starting_city, String ending_city, List<String> attraction){
        // returns a list representing the route the user should take
        // must include a definition of the class or type used in implementation

        // Change all char to lowercase to make search easier:
        starting_city = starting_city.toLowerCase();
        ending_city = ending_city.toLowerCase();

        // go through the list of attractions, and set each char to lowercase
        for(int i = 0; i < attraction.size(); i++){
            attraction.set(i, attraction.get(i).toLowerCase());
        }

        if(city_exists.get(starting_city) == null){
            System.out.println("Cannot find " + starting_city + " or it is misspelled");
            return total_cities;
        }

        if(city_exists.get(ending_city) == null){
            System.out.println("Cannot find " + ending_city + " or it is misspelled");
            return total_cities;
        }

        boolean tell = false;
        for(int i = 0; i < attraction.size(); i++){
            if(attractions.get(attraction.get(i)) == null){
                System.out.println("Cannot find " + attraction.get(i) + "or it's misspelled");
                tell = true;
            }
        }
        if(tell)
            return total_cities;
        update(starting_city);
        String temp = starting_city;
        while(!(attraction.isEmpty())){
            int a = miles.get(attractions.get(attraction.get(0)));
            int index = 0;
            for(int i = 0; i < attraction.size(); i++){
                if(a > miles.get(attractions.get(attraction.get(i)))){
                    index = i;
                    a = miles.get(attractions.get(attraction.get(i)));
                }
            }
            add_cities_visited(temp, roads_travelled.get(attractions.get(attraction.get(index))), attractions.get(attraction.get(index)));
            temp = attractions.get(attraction.get(index));

            visited = new HashMap<String, Boolean>(522);
            miles = new HashMap<String, Integer>(522);
            roads_travelled = new HashMap<String, String>(522);
            check = new ArrayList<Integer>(522);
            update(temp);
            attraction.remove(index);
        }

        add_cities_visited(temp, ending_city, ending_city);
        return total_cities;
        /*
        FIRST
                - Search each file for the starting/ ending point by checking if null
        SECOND
                - Search for attractions in the file by checking if null/misspelled
        THIRD
                - update the map to include the starting_city
        - create a temp array that has the starting city within it
        - While we are till searching for the attractions,
        - Go through the list of the attractions
        - test what has already been travelled with the new attractions
                - and their indices
        - then get the index of the attraction
        - Create new hashmap/arraylist to accomodate for what has been visited,
        the total miles travelled, and what the most efficient route is
        - Update the map to accomodate for the temporary array
                - test the new temp array with the ending city
                - return the shortest path

         */

    }

    public void update(String city){
        int a = 0;
        stack.add(city);
        miles.put(city, 0);
        roads_travelled.put(null, city);

        while(!(stack.isEmpty())){
            // pop the first item from the stack and add it to the temp array
            String temp = stack.pop();
            visited.put(temp, false);
            // go through our current list of routes
            for(int i = 0; i < routes.size(); i++){
                // find the first and second location in routes and get the index
                // if either equals the temp -->
                if(routes.get(i).first_location.equals(temp) || routes.get(i).second_location.equals(temp)){
                    if(routes.get(i).first_location.equals(temp) && check.contains(i)){
                        if(visited.get(routes.get(i).second_location) == null && !(stack.contains(routes.get(i).second_location))){
                            a++;
                            stack.add(routes.get(i).second_location);
                            roads_travelled.put(routes.get(i).second_location, temp);
                            miles.put(routes.get(i).second_location, routes.get(i).miles + miles.get(temp));
                        }
                        check.add(i);
                    }
                    else if(routes.get(i).second_location.equals(temp) && !(check.contains(i))) {
                        if (visited.get(routes.get(i).first_location) == null && !(stack.contains(routes.get(i).first_location))) {
                            a++;
                            stack.add(routes.get(i).first_location);
                            roads_travelled.put(routes.get(i).first_location, temp);
                            miles.put(routes.get(i).first_location, routes.get(i).miles + miles.get(temp));
                        }
                        else if(miles.get(temp) + routes.get(i).miles < miles.get(routes.get(i).first_location)){
                            roads_travelled.put(routes.get(i).first_location, temp);
                            miles.put(routes.get(i).first_location, routes.get(i).miles + miles.get(temp));
                        }
                        check.add(i);
                    }
                }
            }
            String[] arr = new String[1000];
            int count = 0;
            while(!(stack.isEmpty())){
                arr[count] = stack.pop();
                count++;
            }
            for(int i = 0; i < count; i++){
                boolean tell = false;
                int m = miles.get(arr[i]);
                int index = i;
                for(int j = i; j < count; j++){
                    if(m < miles.get(arr[j])){
                        m = miles.get(arr[j]);
                        index = j;
                        tell = true;
                    }
                }
                if(tell == true){
                    String b = arr[i];
                    arr[i] = arr[index];
                    arr[index] = b;
                }
            }
            for(int i = 0; i < count; i++)
                stack.add(arr[i]);
            a = 0;
        }

    }

    public void add_cities_visited(String city1, String city2, String city3){
        update(city1);
        ArrayList<String> temp = new ArrayList<>();
        temp.add(city2);
        String str = city2;
        mile += miles.get(city3);

        while(roads_travelled.get(str) != null){
            str = roads_travelled.get(str);
            temp.add(str);
        }
        for(int i = 0; i < temp.size(); i++){
            total_cities.add(temp.get(temp.size()-i-1));
        }
    }

    public static void main(String[] args){

        Road_Trip_Plan plan = new Road_Trip_Plan();
        ArrayList<String> event_list = new ArrayList<>();

        // Asks for starting/ ending point and number of events
        // Then loops over the number of events given, adding them to the array
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a source: ");
        String source = input.nextLine();

        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter a destination: ");
        String destination = input1.nextLine();

        Scanner input2 = new Scanner(System.in);
        System.out.println("Enter the number of events: ");
        int event_count = input2.nextInt();

        for(int i = 0; i < event_count; i++){
            System.out.println("Name an event: ");
            Scanner input3 = new Scanner(System.in);
            String event = input3.nextLine();
            event_list.add(event);
        }

        System.out.println("Source: " + source);
        System.out.println("Destination: " + destination);
        System.out.println("Number of events: " + event_count);
        System.out.println("Events in an array: " + event_list);

        plan.readFiles("roads.csv", "attractions.csv");
        System.out.println(plan.route(source, destination, event_list));
        System.out.println("Total Miles: " + plan.mile);

    }
}
