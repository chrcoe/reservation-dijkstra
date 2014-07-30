import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * The Models a flight reservation system that will create a graph structure
 * in memory to hold the details of flights being passed in.
 * 
 * @author chrcoe
 * @version July 17, 2012
 */
public class ReservationSystem
{
    // do I really need a Vertex class instead of just using string?
    // if we want to expand what a Vertex can hold in the future aside from just
    // the name, then we would need to add this class anyways, I will leave it.
    private ArrayList<Vertex> inVertices = new ArrayList<Vertex>();
    private ArrayList<Edge> inEdges = new ArrayList<Edge>();
    
    // S in algorithm
    private Set<Vertex> computed = new HashSet<Vertex>();
    // V-S in algorithm
    private Set<Vertex> notComputed = new HashSet<Vertex>();
    // array d in algorithm
    private Map<Vertex, Double> distance = 
            new HashMap<Vertex, Double>();
    // array p in algorithm
    private Map<Vertex, Vertex> predecessor = new HashMap<Vertex, Vertex>();
    
    /**
     * Constructs a ReservationSystem object.  Flight info will be passed in
     * the following format:
     * 
     *  <pre>
     *      Columbus:Cleveland:120
     *      Cleveland:Detroit:40
     *      Columbus:Detroit:180
     *  </pre>
     *  
     *  Will parse this data creating a graph in memory.
     * 
     * @param flightInfo the list of flights available
     */
    public ReservationSystem(ArrayList<String> flightInfo)
    {
        for (String s : flightInfo) {
            StringTokenizer st = new StringTokenizer(s, ":");
            Vertex src = new Vertex(st.nextToken());
            Vertex dest = new Vertex(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            if (!inVertices.contains(src)) {
                // add source as a vertex if not already present
                inVertices.add(src);
            }
            if (!inVertices.contains(dest)) {
                // add source as a vertex if not already present
                inVertices.add(dest);
            }
            inEdges.add(new Edge(src, dest, weight));
        }
//        graph = new Graph(inVertices, inEdges);
    }

    /**
     * Will take in a city by name and calculate the shortest path to every
     * other city in the graph, each String item in the list will contain the
     * cities to visit in order followed by the distance of the flight.
     * 
     * <pre>
     *     (Example)
     *     Columbus,Cleveland:120
     *     Columbus,Cleveland,Detroit:160
     * </pre>
     * 
     * @param startingCity the city you wish to start from
     * @return a list containing shortest route to EVERY other city in the
     *      graph
     */
    public ArrayList<String> findFlights(String startingCity)
    {
        // need to split off starting city before processing ....
        ArrayList<String> flights = new ArrayList<String>();

        initializeDA(startingCity);
        
        // loop over the keyset in distance which contains all the destinations
        // then call the path method to get each path and add to flights list
        for (Vertex v : distance.keySet()) {
            String path = getPathToVertex(v);
            if (path != null) {
                flights.add(getPathToVertex(v));
            }
        }
        return flights;
    }
    
    /**
     * Dijkstra's algorithm. 
     * (Could probably split some more off into helper
     * methods.)
     * 
     * @param startingCity the city to start building shortest paths from
     */
    private void initializeDA(String startingCity) {
        Vertex startVertex = null;
        for (Vertex s : inVertices) {
            if (s.getName().equalsIgnoreCase(startingCity)) {
                startVertex = s;
                computed.add(s); // start vertex s
            }
            else {
                notComputed.add(s); // remaining vertices in V-S
            }
        }
        for (Vertex v : notComputed) {
            // set p[v] to s, use a Map instead of an array?
            Double dist = getDistance(startVertex, v);
            predecessor.put(v, startVertex); // original
//            predecessor.put(startVertex, v); // testing this
            // if there's an edge between s and v
            if (dist != null) {
                distance.put(v, dist);
            }
            else {
                // else set d[v] to infinity
                distance.put(v, Double.POSITIVE_INFINITY);
            }
        }
        while (!notComputed.isEmpty()) {
            Vertex smallest = null;
            for (Vertex u : notComputed) {
                // for all u in V-S, find the smallest d[u]
                if (smallest == null) {
                    smallest = u;
                }
                else if (distance.get(u) < distance.get(smallest)) {
                    smallest = u;
                }
            }
            
            ///////**********possibly in here somewhere take the unreachable
            /// vertex out? /// need it to not be in before it gets to the 
            // getPath method...
            
            // remove u from V-S, add u to S
            notComputed.remove(smallest);
            computed.add(smallest);
            // for all v adjacent to u in V-S
            List<Vertex> adjVert = getAdjacent(smallest);
            for (Vertex v : adjVert) {
                // if d[u] + w(u,v) is less than d[v].
                if (distance.get(smallest) + getDistance(smallest, v) <
                        distance.get(v)) {
                    // set d[v] to d[u] + w(u,v).
                    distance.put(v, distance.get(smallest)
                            + getDistance(smallest, v));
                    // set p[v] to u.
                    predecessor.put(v, smallest); // original
//                    predecessor.put(smallest, v); // testing this
                }
            }
        }
    }
    
    /**
     * Helper method to give a list containing all adjacent vertices to a given
     * a starting vertex.
     * 
     * @param inVert vertex to start from
     * @return gives all adjacent vertices
     */
    private List<Vertex> getAdjacent(Vertex inVert) {
        // for all v adjacent to u in V-S
        List<Vertex> adjVert = new ArrayList<Vertex>();
        for (Edge e : inEdges) {
            if (e.getSource().getName().equalsIgnoreCase(inVert.getName())
                    && !computed.contains(e.getDestination())) {
                adjVert.add(e.getDestination());
            }
        }
        return adjVert;
    }
    /**
     * Helper method to split off some tedious typing for finding the distance
     * between two vertices.
     * 
     * @param source the source vertex 
     * @param dest the destination vertex
     * @return the distance between the two vertices
     */
    private Double getDistance(Vertex source, Vertex dest) {
        Double dist = null;
        for (Edge e : inEdges) {
            if (e.getSource().getName().equalsIgnoreCase(
                    source.getName())
                    && e.getDestination().getName().equalsIgnoreCase(
                            dest.getName())) {
                // this edge has source of startVertex and dest of v
                // set d[v] to w(s,v)
                dist = e.getWeight();
                break;
            }
        }
        return dist;
    }
    
    /**
     * Helper method to gives the path to destination city. This is used to
     * format the paths properly.
     * 
     * Each string is:
     *  <pre>
     *      SourceCity,DestCity:Distance
     *      Columbus,Cleveland:120
     *      Columbus,Cleveland,Detroit:160
     *  </pre> 
     * 
     * @param dest the destination to search for
     * @return a string representation of the path
     */
    private String getPathToVertex(Vertex dest) {
        StringBuilder sb = new StringBuilder();
        if (distance.get(dest) == Double.POSITIVE_INFINITY) {
            return null;
        }
        ArrayList<Vertex> path = new ArrayList<Vertex>();
        Vertex aNode = dest;
//        if (predecessor.get(aNode) == null) {
//            return null;
//        }
        path.add(aNode); // will add to end, putting this in reverse order
        while (predecessor.get(aNode) != null) {
            aNode = predecessor.get(aNode);
            path.add(aNode);
        }
        Collections.reverse(path); // correct the order
        // then build the string accordingly
        for (Iterator<Vertex> it = path.iterator(); it.hasNext();) {
            String s = it.next().getName();
            sb.append(s);
            if (it.hasNext()) {
                sb.append(",");
            }
            else {
                sb.append(":" + distance.get(dest).intValue());
            }
        }
        return sb.toString();
    }
}
