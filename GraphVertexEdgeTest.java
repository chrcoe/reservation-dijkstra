import java.util.ArrayList;
import junit.framework.TestCase;

/**
 * Tests the Graph, Edge and Vertex classes.
 * 
 * @author chrcoe
 * @version July 18, 2012
 */
public class GraphVertexEdgeTest extends TestCase {
    
    private Vertex vTex;
    private Edge edge;
//    private Graph graph;
    private ArrayList<Vertex> vTexList;
    private ArrayList<Edge> edgeList;
    
    /**
     * Sets up the test case.
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        vTex = new Vertex("Columbus");
        edge = new Edge(vTex, new Vertex("Cleveland"), 120);
        fillVertices(10);
        fillEdges(10);
//        graph = new Graph(vTexList, edgeList);
    }

    /**
     * Helper method to fill the list of vertices.
     * 
     * @param numOfVerts number of vertices to input
     */
    private void fillVertices(int numOfVerts) {
        vTexList = new ArrayList<Vertex>();
        for (int i = 0; i < numOfVerts; i++) {
            vTexList.add(new Vertex(String.format("test%d", i)));
        }
    }
    
    /**
     * Helper method to fill the list of edges.
     * 
     * @param numOfEdges number of edges to input
     */
    private void fillEdges(int numOfEdges) {
        edgeList = new ArrayList<Edge>();
        int i = numOfEdges;
        for (Vertex v : vTexList) {
            edgeList.add(new Edge(v, new Vertex(String.format("test%d", i)),
                    i * numOfEdges));
            i--;
        }
    }
    
    /**
     * Test method for {@link Vertex#getName()}.
     */
    public void testGetName() {
        assertEquals("getName did not return proper name",
                "Columbus", vTex.getName());
    }

    /**
     * Test method for {@link Edge#getDestination()}.
     */
    public void testGetDest() {
        assertEquals("did not return proper destination",
                "Cleveland", edge.getDestination().getName());
    }
    
    /**
     * Test method for {@link Edge#getSource()}.
     */
    public void testGetSource() {
        assertEquals("did not return proper source",
                "Columbus", edge.getSource().getName());
    }

    /**
     * Test method for {@link Edge#getWeight()}.
     */
    public void testGetWeight() {
        assertEquals("did not return proper weight",
                120.0, edge.getWeight());
    }

//    /**
//     * Test method for {@link Graph#getVertices()}.
//     */
//    public void testGetVertices() {
//        assertTrue("did not contain all elements",
//                vTexList.containsAll(graph.getVertices()));
//    }
//    
//    /**
//     * Test method for {@link Graph#getEdges()}.
//     */
//    public void testGetEdges() {
//        assertTrue("did not contain all elements",
//                edgeList.containsAll(graph.getEdges()));
//    }

    /**
     * Test method for {@link Edge#toString()}.
     */
    public void testToString() {
        assertTrue("did not contain the proper src/dest/weight",
                edge.toString().contains("Columbus"));
        assertTrue("did not contain the proper src/dest/weight",
                edge.toString().contains("Cleveland"));
        assertTrue("did not contain the proper src/dest/weight",
                edge.toString().contains("120"));
    }

    /**
     * Test method for {@link Vertex#equals(Object)}.
     */
    public void testEquals() {
        Vertex v = new Vertex("test");
        Vertex g = v;
        String stringV = "test";
        assertEquals("expected items to be equal", g, v);
        assertFalse("should not be equal since it is not the same object type",
                v.equals(stringV));
        
    }
}
