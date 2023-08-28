import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Backend Developer Tests
 */
public class BackendDeveloperTests {

    /**
     * Test constructor, loadData
     */
    @Test
    public void test0(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphBD<>();
        AirportProcessorInterface airportReader = new AirportProcessorBD();
        AFSBackendBD test0 = new AFSBackendBD(airportGraph, airportReader);

        
        assertDoesNotThrow(() -> {
            test0.loadData("fake.txt"); // not actually loading a file so shouldnt throw anything
        });

        
         // make sure fake nodes from DW are being loaded in
         assertEquals(true, airportGraph.containsNode("fakeNode0"));
         assertEquals(true, airportGraph.containsNode("fakeNode1"));
         assertEquals(true, airportGraph.containsNode("fakeNode2"));
         assertEquals(true, airportGraph.containsNode("fakeNode3"));

        

        // make sure fake edge from DW being loaded in
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode1"));
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode2"));
        assertEquals(true, airportGraph.containsEdge("fakeNode1", "fakeNode3"));
        assertEquals(true, airportGraph.containsEdge("fakeNode2", "fakeNode3"));

        // check edge weights
        assertEquals(2.0, airportGraph.getEdge("fakeNode0", "fakeNode1"));
        assertEquals(4.0, airportGraph.getEdge("fakeNode0", "fakeNode2"));
        assertEquals(1.0, airportGraph.getEdge("fakeNode1", "fakeNode3"));
        assertEquals(5.0, airportGraph.getEdge("fakeNode2", "fakeNode3"));


        // make sure counts are expected
        assertEquals(4, airportGraph.getNodeCount());
        assertEquals(4, airportGraph.getEdgeCount());
        
    }

    /**
     * Test findCostOfPath
     */
    @Test
    public void test1(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphBD<>();
        AirportProcessorInterface airportReader = new AirportProcessorBD();
        AFSBackendBD test1 = new AFSBackendBD(airportGraph, airportReader);
        
        assertDoesNotThrow(() -> {
            test1.loadData("fake.txt"); // not actually loading a file so shouldnt throw anything
        });
        
         // make sure fake nodes from DW are being loaded in
         assertEquals(true, airportGraph.containsNode("fakeNode0"));
         assertEquals(true, airportGraph.containsNode("fakeNode1"));
         assertEquals(true, airportGraph.containsNode("fakeNode2"));
         assertEquals(true, airportGraph.containsNode("fakeNode3"));

        // make sure fake edge from DW being loaded in
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode1"));
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode2"));
        assertEquals(true, airportGraph.containsEdge("fakeNode1", "fakeNode3"));
        assertEquals(true, airportGraph.containsEdge("fakeNode2", "fakeNode3"));

        // check edge weights
        assertEquals(2.0, airportGraph.getEdge("fakeNode0", "fakeNode1"));
        assertEquals(4.0, airportGraph.getEdge("fakeNode0", "fakeNode2"));
        assertEquals(1.0, airportGraph.getEdge("fakeNode1", "fakeNode3"));
        assertEquals(5.0, airportGraph.getEdge("fakeNode2", "fakeNode3"));

        // make sure counts are expected
        assertEquals(4, airportGraph.getNodeCount());
        assertEquals(4, airportGraph.getEdgeCount());

        // arbitrary cost, test that its finding correct value
        assertEquals(2.0, test1.findCostOfPath("fakeNode0", "fakeNode1"));
        
    }

    /**
     * Test addDelay
     */
    @Test
    public void test2(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphBD<>();
        AirportProcessorInterface airportReader = new AirportProcessorBD();
        AFSBackendBD test2 = new AFSBackendBD(airportGraph, airportReader);

        
        assertDoesNotThrow(() -> {
            test2.loadData("fake.txt"); // not actually loading a file so shouldnt throw anything
        });

        // make sure edge correct before adding delay
        assertEquals(2.0, airportGraph.getEdge("fakeNode0", "fakeNode1"));

        test2.addDelay("fakeNode0", "fakeNode1", "3.0");

        // make sure edge correct after adding delay
        assertEquals(5.0, airportGraph.getEdge("fakeNode0", "fakeNode1"));

    }

    /**
     * test findPathsConstraintNone & findPathsConstraintWeather
     */
    @Test
    public void test3(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphBD<>();
        AirportProcessorInterface airportReader = new AirportProcessorBD();
        AFSBackendBD test3 = new AFSBackendBD(airportGraph, airportReader);
   
        assertDoesNotThrow(() -> {
            test3.loadData("fake.txt"); // not actually loading a file so shouldnt throw anything
        });

        // make sure fake nodes from DW are being loaded in
        assertEquals(true, airportGraph.containsNode("fakeNode0"));
        assertEquals(true, airportGraph.containsNode("fakeNode1"));
        assertEquals(true, airportGraph.containsNode("fakeNode2"));
        assertEquals(true, airportGraph.containsNode("fakeNode3"));   
        
        // make sure fake edge from DW being loaded in
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode1"));
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode2"));
        assertEquals(true, airportGraph.containsEdge("fakeNode1", "fakeNode3"));
        assertEquals(true, airportGraph.containsEdge("fakeNode2", "fakeNode3"));
        
        // check edge weights
        assertEquals(2.0, airportGraph.getEdge("fakeNode0", "fakeNode1"));
        assertEquals(4.0, airportGraph.getEdge("fakeNode0", "fakeNode2"));
        assertEquals(1.0, airportGraph.getEdge("fakeNode1", "fakeNode3"));
        assertEquals(5.0, airportGraph.getEdge("fakeNode2", "fakeNode3"));

        //System.out.println(test3.findPathsConstraintNone("fakeNode0", "fakeNode3"));
        
        List<String> expct = new LinkedList<>();
        expct.add("fakeNode0");
        expct.add("fakeNode1");
        expct.add("fakeNode3");

        // use AE to get shortest path w/ no constraints
        assertEquals(expct, test3.findPathsConstraintNone("fakeNode0", "fakeNode3"));

        List<String> expct1 = new LinkedList<>();
        expct1.add("fakeNode0");
        expct1.add("fakeNode2");
        expct1.add("fakeNode3");
        
        assertEquals(expct1, test3.findPathsConstraintWeather("fakeNode0", "fakeNode3", "Thunderstorm"));

    }

    /**
     * Test getAirportStatistics
     */
    @Test
    public void test4(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphBD<>();
        AirportProcessorInterface airportReader = new AirportProcessorBD();
        AFSBackendBD test4 = new AFSBackendBD(airportGraph, airportReader);
   
        assertDoesNotThrow(() -> {
            test4.loadData("fake.txt"); // not actually loading a file so shouldnt throw anything
        });

        // make sure fake nodes from DW are being loaded in
        assertEquals(true, airportGraph.containsNode("fakeNode0"));
        assertEquals(true, airportGraph.containsNode("fakeNode1"));
        assertEquals(true, airportGraph.containsNode("fakeNode2"));
        assertEquals(true, airportGraph.containsNode("fakeNode3"));   
        
        // make sure fake edge from DW being loaded in
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode1"));
        assertEquals(true, airportGraph.containsEdge("fakeNode0", "fakeNode2"));
        assertEquals(true, airportGraph.containsEdge("fakeNode1", "fakeNode3"));
        assertEquals(true, airportGraph.containsEdge("fakeNode2", "fakeNode3"));

        test4.addDelay("fakeNode0", "fakeNode1", "2.0");

        System.out.println(test4.getAirportStatistics());



        String expct = "Airline Flight System contains:\n" + "    " + 4 + " Airports\n" + "    " 
        + 4 + " Total flights\n" + "    " + 1 + " Total delays\n" + "    " 
        +   "Weather conditions: " + "[Thunderstorm, Rain]" + "    ";

        assertEquals(expct, test4.getAirportStatistics());

    }

    // ----------------- INTEGRATION TESTS ----------------

    /**
     * Integration test with BD and DW
     */
    @Test
    public void integrationTest0(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphAE<>();
        AirportProcessorInterface airportReader = new AirportProcessorDW();
        AFSBackendBD test4 = new AFSBackendBD(airportGraph, airportReader);
   
        // test exception throwing from DW
        assertThrows(FileNotFoundException.class, () ->{
            test4.loadData("fake.dot");
        });

        assertDoesNotThrow(() -> {
            test4.loadData("C:\\Users\\jrohr\\.vscode\\DessertGame\\P3W2.Dijkstra Implementation\\src\\airportdata.dot"); 
        }); 

        // make sure nodes from DW are being loaded in
        assertEquals(airportGraph.containsNode("ATL"), true);
        assertEquals(airportGraph.containsNode("LAX"), true);
        assertEquals(airportGraph.containsNode("ORD"), true);
        assertEquals(airportGraph.containsNode("DFW"), true);
        assertEquals(airportGraph.containsNode("DEN"), true);        
        assertEquals(airportGraph.containsNode("MCO"), true);
                        
        // make sure edges from DW being loaded in
        assertEquals(airportGraph.containsEdge("ATL", "LAX"), true);
        assertEquals(airportGraph.containsEdge("ATL", "ORD"), true);
        assertEquals(airportGraph.containsEdge("ATL", "DFW"), true);
        assertEquals(airportGraph.containsEdge("ATL", "DEN"), true);


    }

    /**
     * Integration test with BD, DW, AE
     */
    @Test
    public void integrationTest1(){
        AirportGraphInterface<String, Double> airportGraph =  new AirportGraphAE<>();
        AirportProcessorInterface airportReader = new AirportProcessorDW();
        AFSBackendBD test5 = new AFSBackendBD(airportGraph, airportReader);

        
   
        // test exception throwing from DW
        assertThrows(FileNotFoundException.class, () ->{
            test5.loadData("fake.dot");
        });

        assertDoesNotThrow(() -> {
            test5.loadData("C:\\Users\\jrohr\\.vscode\\DessertGame\\P3W2.Dijkstra Implementation\\src\\airportdata.dot"); 
        }); 

        // make sure nodes from DW are being loaded in
        assertEquals(airportGraph.containsNode("ATL"), true);
        assertEquals(airportGraph.containsNode("LAX"), true);
        assertEquals(airportGraph.containsNode("ORD"), true);
        assertEquals(airportGraph.containsNode("DFW"), true);
        assertEquals(airportGraph.containsNode("DEN"), true);        
        assertEquals(airportGraph.containsNode("MCO"), true);
                        
        // make sure edges from DW being loaded in
        assertEquals(airportGraph.containsEdge("ATL", "LAX"), true);
        assertEquals(airportGraph.containsEdge("ATL", "ORD"), true);
        assertEquals(airportGraph.containsEdge("ATL", "DFW"), true);
        assertEquals(airportGraph.containsEdge("ATL", "DEN"), true);

        // check flight distances
        assertEquals(404.0, test5.findCostOfPath("ATL", "MCO"));

        test5.addDelay("MCO", "DEN", "5.0");

        
 

    }

    /**
     * Code review of DW: first additional test
     * testing loadFile()
     */
    @Test
    public void CodeReviewOfDataWrangler0(){
        AirportProcessorInterface airportReader = new AirportProcessorDW();

        // test exception throwing from DW
        assertThrows(FileNotFoundException.class, () ->{
            airportReader.loadFile("fake.dot");
        });
        
        assertDoesNotThrow(() -> {
            List<AirportNodeInterface> returnNodes = airportReader.loadFile("C:\\Users\\jrohr\\.vscode\\DessertGame\\P3W2.Dijkstra Implementation\\src\\airportdata.dot");
            List<AirportNodeInterface> expct = new LinkedList<>();
            System.out.println(returnNodes.get(0));
            expct.add(returnNodes.get(0));
            expct.add(returnNodes.get(1));
            expct.add(returnNodes.get(2));
            assertEquals(true, returnNodes.containsAll(expct));
            assertEquals("ATL", returnNodes.get(0).getAirportName());
             
        }); 

    }

    /**
     * Code review of DW: second additional test
     * testing getFullName()
     */
    @Test
    public void CodeReviewOfDataWrangler1(){
        AirportProcessorInterface airportReader = new AirportProcessorDW();

        // test exception throwing from DW
        assertThrows(FileNotFoundException.class, () ->{
            airportReader.loadFile("fake.dot");
        });
        
        assertDoesNotThrow(() -> {
            List<AirportNodeInterface> returnNodes = airportReader.loadFile("C:\\Users\\jrohr\\.vscode\\DessertGame\\P3W2.Dijkstra Implementation\\src\\airportdata.dot");
            List<AirportNodeInterface> expct = new LinkedList<>();
            System.out.println(returnNodes.get(0));
            expct.add(returnNodes.get(0));
            expct.add(returnNodes.get(1));
            expct.add(returnNodes.get(2));
            assertEquals(true, returnNodes.containsAll(expct));
            assertEquals("ATL", returnNodes.get(0).getAirportName());

            assertEquals("Hartsfield-Jackson Atlanta International Airport", returnNodes.get(0).getAirportFullName());
            
             
        }); 

        
    }



    
}
