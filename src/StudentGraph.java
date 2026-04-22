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
  
        adjList = new HashMap<>();  
        nodes = new ArrayList<>(students); 
        for(UniversityStudent Student : students){ 
            // key is student, value is new array 
            if (!adjList.containsKey(Student)){
                adjList.put(Student, new ArrayList<>()); 
            }

        }  

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
    private ArrayList<Edge> getNeighbors(UniversityStudent u){ 
        return adjList.get(u); 
    }  

    /**
     * Return list of Universitystudents
     */
    private ArrayList<UniversityStudent> getAllNodes(){ 
        return nodes; 
    }
    







}
