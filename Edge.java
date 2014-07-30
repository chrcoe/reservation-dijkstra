/**
 * Class to model an edge of a graph. An Edge has source/destination vertices as
 * well as weights.
 * 
 * @author chrcoe
 * @version July 18, 2012
 */
public class Edge {

    private final Vertex source;
    private final Vertex dest;
    private final double weight;

    /**
     * Constructs an edge with given source/destination/weight.
     * 
     * @param inSource the source Vertex
     * @param inDest the destination Vertex
     * @param inWeight the weight of the edge between the two
     */
    public Edge(Vertex inSource, Vertex inDest, double inWeight) {
        this.source = inSource;
        this.dest = inDest;
        this.weight = inWeight;
    }
    /**
     * Gives the destination of this edge.
     * 
     * @return the destination of this edge.
     */
    public Vertex getDestination() {
        return this.dest;
    }
    /**
     * Gives the source of this edge.
     * 
     * @return the source of this edge.
     */
    public Vertex getSource() {
        return this.source;
    }
    /**
     * Gives the weight of this edge.
     * 
     * @return the weight of this edge.
     */
    public double getWeight() {
        return this.weight;
    }
    /**
     * Gives this edge as a string representation.
     * 
     * @return string representation of the Edge
     */
    @Override
    public String toString() {
        return String.format("src: %s, dest: %s, weight: %f",
                source, dest, Math.floor(weight));
    }
}
