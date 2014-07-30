/**
 * Class to model a vertex in a graph.
 * 
 * @author chrcoe
 * @version July 18, 2012
 */
public class Vertex {
    
    final private String name;
    
    /**
     * Constructs a Vertex with the given name.
     * 
     * @param inName the name to give this Vertex object
     */
    public Vertex(String inName) {
        this.name = inName;
    }
    /**
     * Gives the name of this Vertex.
     * 
     * @return the name of this Vertex
     */
    public String getName() {
        return this.name;
    }
    /**
     * String representation of a Vertex.
     * 
     * @return string representation of the vertex
     */
    @Override
    public String toString() {
        return name;
    }
    
    /**
     * Computes hashcode for this based off value of name only.
     * 
     * @return the hashCode of the name of this Vertex
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    /**
     * Computes equals for the name value only.
     * 
     * @param o object to check against this Vertex for equality
     * @return true if the incoming Vertex equals this Vertex
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Vertex) {
            return ((Vertex)o).getName().equalsIgnoreCase(name);
        }
        return false;
    }
}
