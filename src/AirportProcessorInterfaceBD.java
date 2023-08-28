import java.io.FileNotFoundException;
import java.util.List;

public interface AirportProcessorInterfaceBD {
    public List<AirportNodeInterfaceBD> loadFileNodes(String fileName) throws FileNotFoundException;
    public List<AirportEdgeInterfaceBD> loadFileEdges(String fileName) throws FileNotFoundException;
    // public List<AirportDW> loadFile(String fileName) throws FileNotFoundException;
}
