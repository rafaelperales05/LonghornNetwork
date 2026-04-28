import java.util.*;

public class ReferralPathFinder { 
    private StudentGraph graph; 

    public ReferralPathFinder(StudentGraph graph) {
        // Constructor 
        this.graph = graph;
    }

    private static class NodeWrapper implements Comparable<NodeWrapper> {
        UniversityStudent student;
        int distance;

        NodeWrapper(UniversityStudent student, int distance) {
            this.student = student;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeWrapper other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        if (graph == null || start == null) {
            return new ArrayList<>();
        }

        // Check if the start node itself has the target company
        if (start.getPreviousInternships().contains(targetCompany)) {
            List<UniversityStudent> path = new ArrayList<>();
            path.add(start);
            return path;
        }

        HashMap<UniversityStudent, Integer> distances = new HashMap<>();
        HashMap<UniversityStudent, UniversityStudent> predecessors = new HashMap<>();
        PriorityQueue<NodeWrapper> pq = new PriorityQueue<>();

        // Initialize distances
        for (UniversityStudent node : graph.getAllNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        pq.add(new NodeWrapper(start, 0));

        UniversityStudent targetStudent = null;

        while (!pq.isEmpty()) {
            NodeWrapper current = pq.poll();
            UniversityStudent currentStudent = current.student;

            // If we've recorded a shorter distance already, skip
            if (current.distance > distances.get(currentStudent)) {
                continue;
            }

            // Target reached!
            if (currentStudent.getPreviousInternships().contains(targetCompany)) {
                targetStudent = currentStudent;
                break;
            }

            // Explore neighbors
            for (StudentGraph.Edge edge : graph.getNeighbors(currentStudent)) {
                // Invert the connection weight as per instructions: stronger connections are shorter paths
                int edgeCost = 10 - edge.weight;
                
                int newDistance = distances.get(currentStudent) + edgeCost;
                if (newDistance < distances.get(edge.neighbor)) {
                    distances.put(edge.neighbor, newDistance);
                    predecessors.put(edge.neighbor, currentStudent);
                    pq.add(new NodeWrapper(edge.neighbor, newDistance));
                }
            }
        }

        // Reconstruct path if target found
        if (targetStudent == null) {
            return new ArrayList<>(); // No path found
        }

        List<UniversityStudent> path = new ArrayList<>();
        UniversityStudent current = targetStudent;
        while (current != null) {
            path.add(current);
            current = predecessors.get(current);
        }
        
        Collections.reverse(path);
        return path;
    }
}
