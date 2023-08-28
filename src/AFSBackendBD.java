import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;



/**
 * BackendDeveloper class 
 */
public class AFSBackendBD implements AFSBackendInterface{
    private AirportGraphInterface<String, Double> airportGraph;
    private AirportProcessorInterface airportReader;
    private List<AirportNodeInterface> nodes = new LinkedList<>(); // for bd use only
    private List<AirportEdgeInterface> edges = new LinkedList<>(); // for bd use only
    private List<String> weatherConditions = new LinkedList<String>();
    private int totalDelays = 0;
    private AirportEdgeInterface restoreEdge;

        /**
     * Initialize BD to make use of AE's AirportGraph and DW's 
     */
    public AFSBackendBD(AirportGraphInterface<String, Double> airportGraph, AirportProcessorInterface airportReader){
        this.airportGraph = airportGraph;
        this.airportReader = airportReader;
    }


    /**
     * load data into graph
     */
    @Override
    public void loadData(String filename) throws FileNotFoundException {
        List<AirportNodeInterface> airports =  this.airportReader.loadFile(filename); 
        List<AirportEdgeInterface> flightsLeaving = new LinkedList<>();

        // load nodes
        for(AirportNodeInterface airport : airports){
            this.nodes.add(airport);
            addAirportToGraph(airport);
            flightsLeaving.addAll(airport.getEdgesLeaving());
            
        }
 
        // load edges
        for(AirportEdgeInterface flight : flightsLeaving){
            this.edges.add(flight);
            addFlightToGraph(flight);

            if(flight.getWeatherCondition() != null && !this.weatherConditions.contains(flight.getWeatherCondition())){
                this.weatherConditions.add(flight.getWeatherCondition());   
            }
            
        
        }

    }

    /**
     * helper method for load data, adds airports
     */
    private void addAirportToGraph(AirportNodeInterface newAirport){
        this.airportGraph.insertNode(newAirport.getAirportName());

    }

    /**
     * helper method for load data, adds edges
     */
    private void addFlightToGraph(AirportEdgeInterface newFlight) {
        this.airportGraph.insertEdge(newFlight.getPredecessor().getAirportName(), newFlight.getSuccessor().getAirportName(), 
        newFlight.getEdgeWeight());
  
    }

    /**
     * Helper method for findPathsConstraintWeather, sets specified condition to consider
     * @param weatherCondition - specified condition to check
     */
    public void setWeatherDelays(String weatherCondition){
        for(int i = 0; i < this.edges.size(); i++){ // loop thru all edges/flights
            // if edge/flight weather matches condition, check what type/severity
            // its the quotes
            // System.out.println(this.edges.get(i).getWeatherCondition());
            if(this.edges.get(i).getWeatherCondition().equalsIgnoreCase(weatherCondition)){
                if(weatherCondition.equalsIgnoreCase("Rainy") || weatherCondition.equalsIgnoreCase("Windy") 
                || weatherCondition.equalsIgnoreCase("Snowy")){
                    // add delay of 5.0 for these conditions
                    this.addDelay(this.edges.get(i).getPredecessor().getAirportName(), this.edges.get(i).getSuccessor().getAirportName(), "5.0");

                }
                // for severe weather, remove edge/flight / cancel flight
                else if(weatherCondition.equalsIgnoreCase("Thunderstorm") || weatherCondition.equalsIgnoreCase("Blizzard")){
                    this.airportGraph.removeEdge(this.edges.get(i).getPredecessor().getAirportName(), this.edges.get(i).getSuccessor().getAirportName());
                    this.airportGraph.removeEdge(this.edges.get(i).getSuccessor().getAirportName(), this.edges.get(i).getPredecessor().getAirportName());
                    this.restoreEdge = this.edges.remove(i);
                }
                    
                
            }
        }
        
    }


    /**
     * find paths with no constraints
     */
    @Override
    public List<String> findPathsConstraintNone(String airportA, String airportB) {
        return airportGraph.shortestPathData(airportA, airportB);
    }




    /**
     * considers weather in getting shortest path
     */
    @Override
    public List<String> findPathsConstraintWeather(String airportA, String airportB, String weatherCondition) {
        this.setWeatherDelays(weatherCondition); // set specified weather condition to consider
        List<String> save = this.airportGraph.shortestPathData(airportA, airportB);
        if(this.restoreEdge != null){
            this.airportGraph.insertEdge(this.restoreEdge.getPredecessor().getAirportName(), this.restoreEdge.getSuccessor().getAirportName(), this.restoreEdge.getEdgeWeight());
        }
        
        return save;
        
    }

    /**
     * add on delay to an edge between airportA and airportB
     */
    @Override
    public void addDelay(String airportA, String airportB, String addDelay){
        double newEdgeWeight = this.airportGraph.getEdge(airportA, airportB) + Double.parseDouble(addDelay);
        this.airportGraph.removeEdge(airportA, airportB);
        this.airportGraph.insertEdge(airportA, airportB, newEdgeWeight);
        this.totalDelays++;

    }




    /**
     * Shortest path cost
     */
    @Override
    public double findCostOfPath(String airportA, String airportB) {
        return this.airportGraph.shortestPathCost(airportA, airportB); // call AE's shortestPathCost
    }


    /**
     * shows number of airports and total flights in our graph
     */
    @Override
    public String getAirportStatistics() {  
        return "Airline Flight System contains:\n" + "    " + this.airportGraph.getNodeCount() + " Airports\n" + "    " 
        + this.airportGraph.getEdgeCount() + " Total flights\n" + "    " + this.totalDelays + " Total delays\n" + "    " 
        +   "Weather conditions: " + this.weatherConditions.toString() + "    ";
        
    }


    
}
