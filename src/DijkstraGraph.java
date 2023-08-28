// --== CS400 File Header Information ==--
// Name: <James Rohrs>
// Email: <jrohrs@wisc.edu>
// Group and Team: <CR, red>
// Group TA: <Rahul>
// Lecturer: <Gary Dahl>
// Notes to Grader: <optional extra notes>
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType,EdgeType>
    implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph.  The final node in this path is stored in it's node
     * field.  The total cost of this path is stored in its cost field.  And the
     * predecessor SearchNode within this path is referenced by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it's node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) throws NoSuchElementException{
        if(!this.containsNode(end) || !this.containsNode(start)){ // throws NSEE when either node not in graph
            throw new NoSuchElementException("Error, either start or end not in graph");
        }


        PriorityQueue<SearchNode> path = new PriorityQueue<SearchNode>(); // greedily explore shorter path possibilities before longer ones
        Hashtable<NodeType, Double> costTable = new Hashtable<NodeType, Double>(); // keep track of the nodes that you have already visited (and found the shortest path reaching them from the start node)

        
        SearchNode startNode = new SearchNode(this.nodes.get(start), 0, null); 
        path.add(startNode); // add starting node to path
        costTable.put(start, 0.0); // add to visited
        
        // while priority queue is not empty
        while(!path.isEmpty()){
            SearchNode curr = path.poll(); // remove node w/ lowest cost

            if(curr.node.data.equals(end)){ // if curr == end, stop and return
                return curr;
            }

            for(Edge edge : curr.node.edgesLeaving){ // for each neighboring node of removed node
                // calc cost of path from starting node to neighboring node through removed node
                Node neighborNode = edge.successor; 
                double newCost = curr.cost + edge.data.doubleValue();

                // if cost lower than curr cost of neighboring node(or neighbor node not in table), update cost of neighboring node in costTable and add to pq w/ new cost and predecessor
                if(!costTable.containsKey(neighborNode.data) || newCost < costTable.get(neighborNode.data)){
                    costTable.put(neighborNode.data, newCost);
                    path.add(new SearchNode(neighborNode, newCost, curr));

                }
            }
        }



        throw new NoSuchElementException("No path found from " + start + " to " + end); // throw NSEE if no path found
    }

    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
        List<NodeType> shortPathList = new LinkedList<>(); // linked list for helper
        SearchNode endNode = this.computeShortestPath(start, end); // SearchNode for helper
         
        return shortestPathDataHelper(shortPathList, endNode); // call helper
    }

    /**
     * Helper method for shortestPathData to return list from start to end
     * @param shortPathList the linked list containing the path of nodes
     * @param endNode the SearchNode containing links of the path through predecessors
     * @return
     */
    private List<NodeType> shortestPathDataHelper(List<NodeType> shortPathList, SearchNode endNode){
        // base case: if endNode is null, meaning no more nodes 
        if(endNode == null){
            return null;
        }
        

        shortestPathDataHelper(shortPathList, endNode.predecessor); // recurse through predecessor links

        shortPathList.add(endNode.node.data); // once at start node, add up nodes

        return shortPathList; // return list
        

    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {  
        return this.computeShortestPath(start, end).cost; // get cost of SearchNode(already done)
    }


    /**
     * test that makes use of an example that you traced through in lecture, 
     * and confirm that the results of your implementation match what you previously computed by hand
     */
    @Test
    public void test0(){
        // create graph
        DijkstraGraph<String, Double> test0 = new DijkstraGraph<>();
        // node insertions
        test0.insertNode("A");
        test0.insertNode("B");
        test0.insertNode("C");
        test0.insertNode("D");
        test0.insertNode("E");
        test0.insertNode("F");
        test0.insertNode("G");
        test0.insertNode("H");
        test0.insertNode("I");
        test0.insertNode("J");
        test0.insertNode("K");
        test0.insertNode("L");
        test0.insertNode("M");

        // edge insertions
        test0.insertEdge("A", "B", 1.0);
        test0.insertEdge("A", "H", 8.0);
        test0.insertEdge("A", "M", 5.0);
        test0.insertEdge("B", "M", 3.0);
        test0.insertEdge("D", "A", 7.0);
        test0.insertEdge("D", "G", 2.0);
        test0.insertEdge("F", "G", 9.0);
        test0.insertEdge("G", "L", 7.0);
        test0.insertEdge("H", "B", 6.0);
        test0.insertEdge("H", "I", 2.0);
        test0.insertEdge("I", "D", 1.0);
        test0.insertEdge("I", "L", 5.0);
        test0.insertEdge("I", "H", 2.0);
        test0.insertEdge("M", "E", 3.0);
        test0.insertEdge("M", "F", 4.0);

        String list = "[D, A, H, I]"; // expected path

        // path expected == actual path
        assertEquals(test0.shortestPathData("D", "I").toString(), list);
        // searchNode == "I" / end node is correct    
        assertEquals(test0.computeShortestPath("D", "I").node.data, "I");
        // cost == 17
        assertEquals(test0.shortestPathCost("D", "I"), 17);

    }

    /**
     * test using the same graph as you did for the test above, 
     * but check the cost and sequence of data along the shortest path between a different start and end node. 
     */
    @Test
    public void test1(){
        // create graph
        DijkstraGraph<String, Double> test1 = new DijkstraGraph<>();
        // node insertions
        test1.insertNode("A");
        test1.insertNode("B");
        test1.insertNode("C");
        test1.insertNode("D");
        test1.insertNode("E");
        test1.insertNode("F");
        test1.insertNode("G");
        test1.insertNode("H");
        test1.insertNode("I");
        test1.insertNode("J");
        test1.insertNode("K");
        test1.insertNode("L");
        test1.insertNode("M");

        // edge insertions
        test1.insertEdge("A", "B", 1.0);
        test1.insertEdge("A", "H", 8.0);
        test1.insertEdge("A", "M", 5.0);
        test1.insertEdge("B", "M", 3.0);
        test1.insertEdge("D", "A", 7.0);
        test1.insertEdge("D", "G", 2.0);
        test1.insertEdge("F", "G", 9.0);
        test1.insertEdge("G", "L", 7.0);
        test1.insertEdge("H", "B", 6.0);
        test1.insertEdge("H", "I", 2.0);
        test1.insertEdge("I", "D", 1.0);
        test1.insertEdge("I", "L", 5.0);
        test1.insertEdge("I", "H", 2.0);
        test1.insertEdge("M", "E", 3.0);
        test1.insertEdge("M", "F", 4.0);

        String list = "[M, F, G, L]"; // expected path

        // path expected == actual path
        assertEquals(test1.shortestPathData("M", "L").toString(), list); 
        // searchNode == "L" / end node is correct 
        assertEquals(test1.computeShortestPath("M", "L").node.data, "L");
        // cost == 20
        assertEquals(test1.shortestPathCost("M", "L"), 20);

    }

    /**
     * test that checks the behavior of your implementation when the node that you are searching for a path between exist in the graph, 
     * but there is no sequence of directed edges that connects them from the start to the end.
     */
    @Test
    public void test2(){
        // create graph
        DijkstraGraph<String, Double> test1 = new DijkstraGraph<>();
        // node insertions
        test1.insertNode("A");
        test1.insertNode("B");
        test1.insertNode("C");
        test1.insertNode("D");
        test1.insertNode("E");
        test1.insertNode("F");
        test1.insertNode("G");
        test1.insertNode("H");
        test1.insertNode("I");
        test1.insertNode("J");
        test1.insertNode("K");
        test1.insertNode("L");
        test1.insertNode("M");

        // edge insertions
        test1.insertEdge("A", "B", 1.0);
        test1.insertEdge("A", "H", 8.0);
        test1.insertEdge("A", "M", 5.0);
        test1.insertEdge("B", "M", 3.0);
        test1.insertEdge("D", "A", 7.0);
        test1.insertEdge("D", "G", 2.0);
        test1.insertEdge("F", "G", 9.0);
        test1.insertEdge("G", "L", 7.0);
        test1.insertEdge("H", "B", 6.0);
        test1.insertEdge("H", "I", 2.0);
        test1.insertEdge("I", "D", 1.0);
        test1.insertEdge("I", "L", 5.0);
        test1.insertEdge("I", "H", 2.0);
        test1.insertEdge("M", "E", 3.0);
        test1.insertEdge("M", "F", 4.0);

        // no path available, should throw NSEE for all methods
        assertThrows(NoSuchElementException.class, () -> {
            test1.shortestPathData("E", "G");
            test1.shortestPathCost("E", "G");
            test1.computeShortestPath("E", "G");
        });

    }

    public static void main(String[] args){

    }
 
}