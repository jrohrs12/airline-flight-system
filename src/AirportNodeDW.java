import java.util.List;

/**
 * This class represents an airport and stores all of the necessary data to construct an airport object
 */
public class AirportNodeDW implements AirportNodeInterface {

        private String airportName; // shortened airport name
        private String airportFullName; // full airport name
        private List<AirportEdgeInterface> edgesLeaving; // edges leaving the airport node
        private List<AirportEdgeInterface> edgesEntering; // edges entering the airport node

        /**
         * Constructor for airport node
         */
        public AirportNodeDW(String airportName, String airportFullName, List<AirportEdgeInterface> edgesLeaving, List<AirportEdgeInterface> edgesEntering) {
                this.airportName = airportName;
                this.airportFullName = airportFullName;
                this.edgesLeaving = edgesLeaving;
                this.edgesEntering = edgesEntering;
        }

        /**
         * Getter for airport name (shortened)
         * @return String shortened airport name
         */
        public String getAirportName() {
                return airportName;
        }

        /**
         * Getter for airport full name
         * @return String airport full name
         */
        public String getAirportFullName() {
                return airportFullName;
        }

        /**
         * Getter for edges leaving the node
         * @return List<AirportEdgeDW> edges leaving
         */
        public List<AirportEdgeInterface> getEdgesLeaving() {
                return edgesLeaving;
        }

        /**
         * Getter for edges entering the node
         * @return List<AirportEdgeDW> edges entering
         */
        public List<AirportEdgeInterface> getEdgesEntering() {
                return edgesEntering;
        }

        /**
         * toString for airport including name, out routes, and in routes, using edge toString
         * @return String
         */
        @Override
        public String toString() {
                String toReturn;
                toReturn = "[" + airportName + " (" + airportFullName + ")]\n";
                toReturn += "Out Routes:\n";
                for (AirportEdgeInterface edge : edgesLeaving) {
                        toReturn += edge.toString() + "\n";
                }
                toReturn += "\nIn Routes:\n";
                for (AirportEdgeInterface edge : edgesEntering) {
                        toReturn += edge.toString() + "\n";
                }
                return toReturn;
        }
}