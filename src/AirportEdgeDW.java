public class AirportEdgeDW implements AirportEdgeInterface {

    private AirportNodeDW predecessor;
    private AirportNodeDW successor;
    private double edgeWeight;
    private String weatherCondition;

    public AirportEdgeDW(AirportNodeDW predecessor, AirportNodeDW successor, double edgeWeight, String weatherCondition) {
            this.predecessor = predecessor;
            this.successor = successor;
            this.edgeWeight = edgeWeight;
            this.weatherCondition = weatherCondition;
    }

    public AirportNodeDW getPredecessor() {
            return predecessor;
    }

    public AirportNodeDW getSuccessor() {
            return successor;
    }

    public double getEdgeWeight() {
            return edgeWeight;
    }

    public void setEdgeWeight(double edgeWeight) {
            this.edgeWeight = edgeWeight;
    }

    public String getWeatherCondition() {
            return weatherCondition;
    }


    @Override
    public String toString() {
            String toReturn;
            toReturn = predecessor.getAirportName() + " -> " + successor.getAirportName();
            toReturn += "\nWeather: " + weatherCondition + "\nEdge Weight: " + edgeWeight;
            return toReturn;

    }
}
