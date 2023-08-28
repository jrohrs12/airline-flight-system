import java.io.FileNotFoundException;
import java.util.List;

public interface AirportProcessorInterface {
// public AirportProcessor();
        public List<AirportNodeInterface> loadFile(String airportGraphFileName) throws FileNotFoundException;
        public String getFullName(String airportGraphFileName, String shorthandName) throws FileNotFoundException;
}
