import java.util.*; 

public class StudentGraph {
    private HashMap<UniversityStudent,ArrayList<Edge>> adjList;  
    private ArrayList<UniversityStudent> nodes; 


    
    /**
     * Helper class used to store an edges connection(University Student) and 
     * weight of edge. 
     */
    public static class Edge {
        public UniversityStudent neighbor;
        public int weight;

        public Edge(UniversityStudent neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }
    }



    /**
     * Initilize hashmap adjaceny list. Each Unique Student Name maps to
     * an array of connections 
     * @param students
     */
    public StudentGraph(List<UniversityStudent> students){  
        if (students == null) {
            throw new IllegalArgumentException("students list cannot be null");
        }
  
        adjList = new HashMap<>();  
        nodes = new ArrayList<>();
        for (UniversityStudent student : students){ 
            if (student == null) {
                continue;
            }
            if (!adjList.containsKey(student)){
                adjList.put(student, new ArrayList<>()); 
                nodes.add(student);
            }

        }   
        buildGraph(); 

    } 

    /**
     * Adds edge onto adjlist 
     * @param u - University Student
     * @param v - University Student
     * @param weight - weight of edge 
     * @return boolean Indicates if edge was added successfully 
     */
    private boolean addEdge(UniversityStudent u, UniversityStudent v, int weight){  
        // Check if both students exist in the graph
        if (!adjList.containsKey(u) || !adjList.containsKey(v)){ 
            return false; 
        }
        if (hasEdge(u, v)) {
            return false;
        }
        
        // add edge on both students 
        adjList.get(u).add(new Edge(v, weight)); 
        adjList.get(v).add(new Edge(u, weight)); 
        
        return true;
    }    

    /**
     * Returns array of edges from requested student. 
     * @param u - Student/node 
     * @return ArrayList of edges 
     */
    public ArrayList<Edge> getNeighbors(UniversityStudent u){ 
        ArrayList<Edge> neighbors = adjList.get(u);
        return neighbors != null ? neighbors : new ArrayList<>();
    }  

    /**
     * Return list of Universitystudents
     */
    public ArrayList<UniversityStudent> getAllNodes(){ 
        return new ArrayList<>(nodes); 
    }

    private boolean hasEdge(UniversityStudent u, UniversityStudent v) {
        ArrayList<Edge> neighbors = adjList.get(u);
        if (neighbors == null) {
            return false;
        }
        for (Edge edge : neighbors) {
            if (edge.neighbor.equals(v)) {
                return true;
            }
        }
        return false;
    }
    
    private void buildGraph(){ 
        for (ArrayList<Edge> edges : adjList.values()) {
            edges.clear();
        }
        // Add each undirected edge once by iterating upper triangle.
        for (int i = 0; i < nodes.size(); i++) {
            UniversityStudent u = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                UniversityStudent v = nodes.get(j);
                int weight = u.calculateConnectionStrength(v);
                if (weight > 0) {
                    addEdge(u, v, weight);
                }
            }
        }
    }


}
