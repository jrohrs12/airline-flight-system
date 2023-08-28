import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class AirportProcessorBD implements AirportProcessorInterface{

    /*@Override
    public List<AirportNodeInterfaceBD> loadFileNodes(String fileName) throws FileNotFoundException {
        List<AirportNodeInterfaceBD> hardCode = new LinkedList<>();
        AirportNodeInterfaceBD fakeNode0 = new AirportNodeBD("fakeNode0", null, null);
        AirportNodeInterfaceBD fakeNode1 = new AirportNodeBD("fakeNode1", null, null);
        AirportNodeInterfaceBD fakeNode2 = new AirportNodeBD("fakeNode2", null, null);
        AirportNodeInterfaceBD fakeNode3 = new AirportNodeBD("fakeNode3", null, null);
        hardCode.add(fakeNode0);
        hardCode.add(fakeNode1);       
        hardCode.add(fakeNode2);
        hardCode.add(fakeNode3);

        return hardCode;

    }

    @Override
    public List<AirportEdgeInterfaceBD> loadFileEdges(String fileName) throws FileNotFoundException {
        List<AirportEdgeInterfaceBD> hardCode = new LinkedList<>();
        AirportNodeInterfaceBD fakeNode0 = new AirportNodeBD("fakeNode0", null, null);
        AirportNodeInterfaceBD fakeNode1 = new AirportNodeBD("fakeNode1", null, null);
        AirportNodeInterfaceBD fakeNode2 = new AirportNodeBD("fakeNode2", null, null);
        AirportNodeInterfaceBD fakeNode3 = new AirportNodeBD("fakeNode3", null, null);
        AirportEdgeInterfaceBD fakeEdge0 = new AirportEdgeBD((AirportNodeBD)fakeNode0, (AirportNodeBD)fakeNode1, 2.0, "Thunderstorm");
        AirportEdgeInterfaceBD fakeEdge1 = new AirportEdgeBD((AirportNodeBD)fakeNode0, (AirportNodeBD)fakeNode2, 4.0, "Rain");
        AirportEdgeInterfaceBD fakeEdge2 = new AirportEdgeBD((AirportNodeBD)fakeNode1, (AirportNodeBD)fakeNode3, 1.0, "Rain");
        AirportEdgeInterfaceBD fakeEdge3 = new AirportEdgeBD((AirportNodeBD)fakeNode2, (AirportNodeBD)fakeNode3, 5.0, "Rain");
        hardCode.add(fakeEdge0);
        hardCode.add(fakeEdge1);
        hardCode.add(fakeEdge2);
        hardCode.add(fakeEdge3);
        System.out.println(fakeEdge0.getWeatherCondition());

        return hardCode;

    }
//* */
    @Override
    public List<AirportNodeInterface> loadFile(String airportGraphFileName) throws FileNotFoundException{
        List<AirportNodeInterface> hardCodeNodes = new LinkedList<>();
        
        AirportNodeInterface fakeNode0 = new AirportNodeBD("fakeNode0", new LinkedList<>(), new LinkedList<>());
        AirportNodeInterface fakeNode1 = new AirportNodeBD("fakeNode1", new LinkedList<>(), new LinkedList<>());
        AirportNodeInterface fakeNode2 = new AirportNodeBD("fakeNode2", new LinkedList<>(), new LinkedList<>());
        AirportNodeInterface fakeNode3 = new AirportNodeBD("fakeNode3", new LinkedList<>(), new LinkedList<>());
        AirportEdgeInterface fakeEdge0 = new AirportEdgeBD((AirportNodeBD)fakeNode0, (AirportNodeBD)fakeNode1, 2.0, "Thunderstorm");
        AirportEdgeInterface fakeEdge1 = new AirportEdgeBD((AirportNodeBD)fakeNode0, (AirportNodeBD)fakeNode2, 4.0, "Rain");
        AirportEdgeInterface fakeEdge2 = new AirportEdgeBD((AirportNodeBD)fakeNode1, (AirportNodeBD)fakeNode3, 1.0, "Rain");
        AirportEdgeInterface fakeEdge3 = new AirportEdgeBD((AirportNodeBD)fakeNode2, (AirportNodeBD)fakeNode3, 5.0, "Rain");
        fakeNode0.getEdgesLeaving().add(fakeEdge0);
        fakeNode0.getEdgesLeaving().add(fakeEdge1);

        fakeNode1.getEdgesLeaving().add(fakeEdge2);
        // fakeNode1.getEdgesEntering().add(fakeEdge0);

        fakeNode2.getEdgesLeaving().add(fakeEdge3);
        // fakeNode2.getEdgesEntering().add(fakeEdge1);

        fakeNode3.getEdgesEntering().add(fakeEdge2);
        fakeNode3.getEdgesEntering().add(fakeEdge3);

        hardCodeNodes.add(fakeNode0);
        hardCodeNodes.add(fakeNode1);       
        hardCodeNodes.add(fakeNode2);
        hardCodeNodes.add(fakeNode3);

        return hardCodeNodes;
        

    }
    
    @Override
    public String getFullName(String airportGraphFileName, String shorthandName) throws FileNotFoundException{
        return "madison airport"; // arbitrary full name

    }
    
}
