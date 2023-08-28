import java.io.FileNotFoundException;
import java.util.List;

public interface AFSBackendInterface {
 	// public AFSBackendInterface(AirplaneGraph<String, Double>, AFSDWProcessorInterface);
     public void loadData(String filename) throws FileNotFoundException; // calls DW loadFile for .dot file

     public List<String> findPathsConstraintNone(String airportA, String airportB); // find shortest paths regardless of constraints
     
     public List<String> findPathsConstraintWeather(String airportA, String airportB, String weatherCondition); // find all paths taking into account weather conditions
     
     public void addDelay(String airportA, String airportB, String addDelay);
     
     public double findCostOfPath(String airportA, String airportB); // find cost of path
         
     public String getAirportStatistics(); // airline flight system stats
     
    
}
