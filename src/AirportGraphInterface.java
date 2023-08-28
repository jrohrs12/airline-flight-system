import java.util.List;

public interface AirportGraphInterface<NodeType, EdgeType extends Number> extends GraphADT<NodeType, EdgeType>{
    //public AirplaneGraph<NodeType, EdgeType> implements GraphADT    

    public boolean checkDelay(NodeType node);

    public String customerPreferences(); //method returns String of all preferences from customer between routes

   
}
