import java.util.List;

public interface AirportNodeInterfaceBD {
    // public AirportNodeDW(NodeType data);
        public String getAirportName();
        public List<AirportEdgeBD> getEdgesLeaving();
        public List<AirportEdgeBD> getEdgesEntering();
    }
    
