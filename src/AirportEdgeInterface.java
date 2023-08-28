public interface AirportEdgeInterface {
    //public AirportEdgeDW(AirportNodeDW predecessor, AirportNodeDW successor, double edgeWeight, String weatherCondition);
    public AirportNodeInterface getPredecessor();
    public AirportNodeInterface getSuccessor();
    public double getEdgeWeight();
    public void setEdgeWeight(double edgeWeight);
    public String getWeatherCondition();
}
