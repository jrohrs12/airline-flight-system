import java.util.LinkedList;
import java.util.List;

public class AirportNodeBD implements AirportNodeInterface{
    private String airportName; // shortened airport name
    // private String airportFullName; // full airport name
    private List<AirportEdgeInterface> edgesLeaving = new LinkedList<>(); // edges leaving the airport node
    private List<AirportEdgeInterface> edgesEntering = new LinkedList<>(); // edges entering the airport node

    public AirportNodeBD(String airportName, List<AirportEdgeInterface> edgesLeaving, List<AirportEdgeInterface> edgesEntering) {
		this.airportName = airportName;
		this.edgesLeaving = edgesLeaving;
		this.edgesEntering = edgesEntering;
	}

    @Override
    public String getAirportName() {
        return this.airportName;
    }

    @Override
    public String getAirportFullName(){
        return airportName;

    }

    @Override
    public List<AirportEdgeInterface> getEdgesLeaving() {
        return this.edgesLeaving;
    }

    @Override
    public List<AirportEdgeInterface> getEdgesEntering() {
        return this.edgesEntering;
    }

}
