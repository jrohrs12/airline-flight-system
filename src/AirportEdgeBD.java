import java.util.List;

public class AirportEdgeBD implements AirportEdgeInterface{
	private AirportNodeBD predecessor;
	private AirportNodeBD successor;
	private double edgeWeight;
	private String weatherCondition;
	
	public AirportEdgeBD(AirportNodeBD predecessor, AirportNodeBD successor, double edgeWeight, String weatherCondition) {
		this.predecessor = predecessor;
		this.successor = successor;
		this.edgeWeight = edgeWeight;
		this.weatherCondition = weatherCondition;
	}

    @Override
    public AirportNodeBD getPredecessor() {
        return this.predecessor;
    }

    @Override
    public AirportNodeBD getSuccessor() {
        return this.successor;
    }

    @Override
    public double getEdgeWeight() {
        return this.edgeWeight;
    }

    @Override
    public void setEdgeWeight(double edgeWeight) {
        this.edgeWeight = edgeWeight;
    }

    @Override
    public String getWeatherCondition() {
        return this.weatherCondition;
    }

}
