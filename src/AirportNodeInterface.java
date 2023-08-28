import java.util.List;

public interface AirportNodeInterface {
// public AirportNodeDW(String airportName, String airportFullName, List<AirportEdgeDW> edgesLeaving, List<AirportEdgeDW> edgesEntering);
        public String getAirportName();
        public String getAirportFullName();
        public List<AirportEdgeInterface> getEdgesLeaving();
        public List<AirportEdgeInterface> getEdgesEntering();
}
