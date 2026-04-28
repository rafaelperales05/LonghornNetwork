import java.util.*;

public class PodFormation {
    private StudentGraph graph;

    public PodFormation(StudentGraph graph) {
        this.graph = graph;
    }

    // A utility function to find the vertex with maximum
    // key value (strongest connection), from the set of vertices not yet included
    // in the current pod (and not previously assigned to another pod)
    private int maxKey(int[] key, boolean[] mstSet, boolean[] assigned) {
        int max = -1, max_index = -1;

        for (int v = 0; v < key.length; v++) {
            if (!mstSet[v] && !assigned[v]) {
                if (key[v] > max) {
                    max = key[v];
                    max_index = v;
                } else if (key[v] == max && max != -1) {
                    max_index = v;
                }
            }
        }

        return max_index;
    }

    public void formPods(int podSize) {
        List<UniversityStudent> nodes = graph.getAllNodes();
        int V = nodes.size();
        
        if (V == 0 || podSize <= 0) return;

        Map<UniversityStudent, Integer> nodeIndex = new HashMap<>();
        for (int i = 0; i < V; i++) {
            nodeIndex.put(nodes.get(i), i);
        }

        // Tracks whether a student has been placed in ANY pod
        boolean[] assigned = new boolean[V];
        
        System.out.println("Pod Assignments:");
        int podCount = 0;

        for (int i = 0; i < V; i++) {
            // Unvisited node implies a new pod or disconnected component
            if (!assigned[i]) {
                List<UniversityStudent> currentPod = new ArrayList<>();
                
                // Key values used to pick maximum weight edge from the base node
                int[] key = new int[V];
                // To represent set of vertices included in current pod
                boolean[] mstSet = new boolean[V];

                // Initialize all keys as -1 (since weights are >= 0)
                for (int j = 0; j < V; j++) {
                    key[j] = -1;
                    mstSet[j] = false;
                }

                // Seed this pod with the base node
                mstSet[i] = true;
                assigned[i] = true;
                UniversityStudent baseStudent = nodes.get(i);
                currentPod.add(baseStudent);

                // Set key values based on the base node's direct connections only
                for (StudentGraph.Edge edge : graph.getNeighbors(baseStudent)) {
                    Integer v = nodeIndex.get(edge.neighbor);
                    if (v == null || assigned[v]) {
                        continue;
                    }
                    if (edge.weight > key[v]) {
                        key[v] = edge.weight;
                    }
                }

                // Form a pod up to the maximum podSize
                for (int count = 1; count < podSize; count++) {
                    // Pick the maximum key vertex from the unassigned nodes
                    int u = maxKey(key, mstSet, assigned);

                    // If u is -1, there are no more nodes connected to this pod's base
                    if (u == -1) {
                        break; 
                    }

                    // Add the picked vertex to the current pod
                    mstSet[u] = true;
                    assigned[u] = true;
                    UniversityStudent pickedStudent = nodes.get(u);
                    currentPod.add(pickedStudent);
                }

                // Print the constructed pod in the requested format
                System.out.print("  Pod " + podCount + ": ");
                for (UniversityStudent student : currentPod) {
                    System.out.print(student.getName() + ", ");
                }
                System.out.println();
                
                podCount++;
            }
        }
    }
}
