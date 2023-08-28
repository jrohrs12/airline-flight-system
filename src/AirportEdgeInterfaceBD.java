public interface AirportEdgeInterfaceBD extends AirportNodeInterfaceBD {
	//public AirportEdgeDW(AirportNodeDW predecessor, AirportNodeDW successor, double edgeWeight, String weatherCondition);
	public AirportNodeBD getPredecessor();
	public AirportNodeBD getSuccessor();
	public double getEdgeWeight();
	public void setEdgeWeight(double edgeWeight);
	public String getWeatherCondition();
}
