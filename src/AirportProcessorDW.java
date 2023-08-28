import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class loads the airport DOT file data, instantiates airports and edges
 * representing the data
 */
public class AirportProcessorDW implements AirportProcessorInterface {

        /**
         * Method to load DOT file with air flight system information
         *
         * @param String filename
         * @return List<AirportNodeDW> list of airports
         * @throws FileNotFoundException
         */
        @Override
        public List<AirportNodeInterface> loadFile(String filename) throws FileNotFoundException {

                // Create a File object using the filename provided
                File file = new File(filename);

                // Create a Scanner object to read from the file
                Scanner sc = new Scanner(file);

                // Skip the first line of the file
                sc.nextLine();

                // Create a list to store all the airports
                List<AirportNodeInterface> airports = new ArrayList<>();

                // Loop through each line of the file
                String s = "";
                while (sc.hasNext()) {

                        s = sc.nextLine();

                        // Skip lines defining the airport names
                        if (!s.contains("->")) {
                                continue;
                        }

                        // If we reach the end of the graph, break out of the loop
                        if (s.equals("}")) {
                                break;
                        }

                        // Parse the line and extract the relevant information
                        String[] parts_of_line = s.split(" -> ");
                        String predecessorName = parts_of_line[0].trim();
                        String successorName = parts_of_line[1].split("\\[")[0].trim();
                        String[] attributes = parts_of_line[1].split("\\[")[1].split("\\]")[0].split("\\s*,\\s*");
                        double distance = Double.parseDouble(attributes[0].split("=")[1].trim());
                        String weatherCondition = attributes[1].split("=")[1].trim();
                        String airportPredFullName = getFullName(filename, predecessorName);
                        String airportSuccFullName = getFullName(filename, successorName);

                        // Create AirportNode objects for the predecessor and successor airports
                        AirportNodeDW pred = new AirportNodeDW(predecessorName, airportPredFullName, new ArrayList<>(),
                                        new ArrayList<>());
                        AirportNodeDW succ = new AirportNodeDW(successorName, airportSuccFullName, new ArrayList<>(),
                                        new ArrayList<>());

                        // Create an AirportEdge object to connect the two airports
                        AirportEdgeDW edge = new AirportEdgeDW(pred, succ, distance, weatherCondition);

                        // Add the edge to the appropriate lists of edges for the airports
                        pred.getEdgesLeaving().add(edge);
                        succ.getEdgesEntering().add(edge);

                        // Check if the predecessor airport already exists in the list of airports
                        // If it does, add the edge to its list of leaving edges
                        int count = 0;
                        for (AirportNodeInterface airport : airports) {
                                if (airport.getAirportName().trim().equals(pred.getAirportName().trim())) {
                                        airport.getEdgesLeaving().add(edge);
                                        count++;
                                }
                        }

                        // If the predecessor airport does not exist in the list of airports, add it to the list
                        if (count == 0) {
                                airports.add(pred);
                                count++;
                        }

                        // Check if the successor airport already exists in the list of airports
                        // If it does, add the edge to its list of entering edges
                        count = 0;
                        for (AirportNodeInterface airport : airports) {
                                if (airport.getAirportName().equals(succ.getAirportName())) {
                                        airport.getEdgesEntering().add(edge);
                                        count++;
                                }
                        }

                        // If the successor airport does not exist in the list of airports, add it to the list
                        if (count == 0) {
                                airports.add(succ);
                        }
                }

                // Close scanner before returning
                sc.close();

                // Return list of Airports
                return airports;
        }

        /**
         * Helper method to get the full name of the airports
         *
         * @param filename name of airport data file
         * @param name     shorthand name of airport
         * @return airport full name, returns null other
         */
        @Override
        public String getFullName(String filename, String name) throws FileNotFoundException {

                // Create a File object using the filename provided
                File file = new File(filename);

                // Create a Scanner object to read from the file
                Scanner sc = new Scanner(file);

                // Skip the first line of the file
                sc.nextLine();

                // Loop through each line of the file
                String s = "";
                while (sc.hasNext() && !s.contains("->")) {

                        s = sc.nextLine();

                        // Parse the line and extract the relevant information
                        String[] parts_of_line = s.split("\\[");
                        String shortName = parts_of_line[0].trim();
                        String fullName = parts_of_line[1].split("\\]")[0].replace("label=\"", "").split("\"")[0];

                        // Returns the full name if passed short name is equal to the current line's
                        // short name
                        if (name.equals(shortName)) {
                                sc.close(); // close scanner before returning
                                return fullName;
                        }

                }

                // Close scanner before returning
                sc.close();

                // To prevent compiler errors
                return null;
        }
}
