import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestStudentGraph {
    public static void main(String[] args) throws Exception {
        UniversityStudent alice = makeStudent("Alice", "20", "CS", "Google,Meta");
        UniversityStudent bob = makeStudent("Bob", "20", "CS", "Google");
        UniversityStudent carol = makeStudent("Carol", "21", "Math", "Amazon");

        alice.setRoommate(bob);
        bob.setRoommate(alice);

        List<UniversityStudent> students = new ArrayList<>();
        students.add(alice);
        students.add(bob);
        students.add(carol);

        StudentGraph graph = new StudentGraph(students);
        HashMap<UniversityStudent, ArrayList<StudentGraph.Edge>> adjList = getAdjList(graph);

        assertEquals(3, adjList.size(), "adjList size");
        assertEdge(adjList, alice, "Bob", 10);
        assertEdge(adjList, bob, "Alice", 10);
        assertNoEdges(adjList, carol);

        System.out.println("TestStudentGraph: all checks passed.");
    }

    private static UniversityStudent makeStudent(String name, String age, String major, String internships) {
        UniversityStudent s = new UniversityStudent();
        s.setName(name);
        s.setAge(age);
        s.setMajor(major);
        s.setPreviousInternships(internships);
        return s;
    }

    @SuppressWarnings("unchecked")
    private static HashMap<UniversityStudent, ArrayList<StudentGraph.Edge>> getAdjList(StudentGraph graph)
            throws Exception {
        Field field = StudentGraph.class.getDeclaredField("adjList");
        field.setAccessible(true);
        return (HashMap<UniversityStudent, ArrayList<StudentGraph.Edge>>) field.get(graph);
    }

    private static void assertEdge(
            HashMap<UniversityStudent, ArrayList<StudentGraph.Edge>> adjList,
            UniversityStudent from,
            String toName,
            int expectedWeight) {
        ArrayList<StudentGraph.Edge> edges = adjList.get(from);
        if (edges == null) {
            throw new AssertionError("Missing adjacency list for " + from.getName());
        }
        for (StudentGraph.Edge edge : edges) {
            if (edge.neighbor.getName().equals(toName) && edge.weight == expectedWeight) {
                return;
            }
        }
        throw new AssertionError("Missing edge from " + from.getName() + " to " + toName
                + " with weight " + expectedWeight);
    }

    private static void assertNoEdges(
            HashMap<UniversityStudent, ArrayList<StudentGraph.Edge>> adjList,
            UniversityStudent student) {
        ArrayList<StudentGraph.Edge> edges = adjList.get(student);
        if (edges == null || !edges.isEmpty()) {
            throw new AssertionError("Expected no edges for " + student.getName());
        }
    }

    private static void assertEquals(int expected, int actual, String label) {
        if (expected != actual) {
            throw new AssertionError(label + ": expected " + expected + ", got " + actual);
        }
    }
}
