import java.util.ArrayList;
import java.util.List;

public class TestReferralPathFinder {
    public static void main(String[] args) {
        System.out.println("Running TestReferralPathFinder...");

        // Setup students
        UniversityStudent alice = makeStudent("Alice", "20", "CS", "Oracle");
        UniversityStudent bob = makeStudent("Bob", "20", "CS", "Stripe");
        UniversityStudent carol = makeStudent("Carol", "21", "EE", "Stripe,Meta");
        UniversityStudent dave = makeStudent("Dave", "20", "CS", "Meta");

        // Alice and Bob are roommates
        alice.setRoommate(bob);
        bob.setRoommate(alice);

        List<UniversityStudent> students = new ArrayList<>();
        students.add(alice);
        students.add(bob);
        students.add(carol);
        students.add(dave);

        // Build the graph
        // Edge weights before inversion:
        // Alice & Bob: roommate(4) + major(2) + age(1) = 7. (Cost = 10 - 7 = 3)
        // Alice & Carol: no shared = 0. (No Edge)
        // Alice & Dave: major(2) + age(1) = 3. (Cost = 10 - 3 = 7)
        // Bob & Carol: shared Stripe(3) = 3. (Cost = 10 - 3 = 7)
        // Bob & Dave: major(2) + age(1) = 3. (Cost = 10 - 3 = 7)
        // Carol & Dave: shared Meta(3) = 3. (Cost = 10 - 3 = 7)
        StudentGraph graph = new StudentGraph(students);
        ReferralPathFinder finder = new ReferralPathFinder(graph);

        // Test 1: Start node has the target internship
        List<UniversityStudent> path1 = finder.findReferralPath(alice, "Oracle");
        assertPath(path1, "Test 1 (Start node has target)", "Alice");

        // Test 2: Direct connection (Alice -> Bob for Stripe)
        List<UniversityStudent> path2 = finder.findReferralPath(alice, "Stripe");
        assertPath(path2, "Test 2 (Direct connection)", "Alice", "Bob");

        // Test 3: Shortest path routing
        // Alice->Bob->Carol for Meta (Cost: 3 + 7 = 10)
        // Alice->Dave for Meta (Cost: 7)
        // Dijkstra's should pick Alice -> Dave because 7 < 10.
        List<UniversityStudent> path3 = finder.findReferralPath(alice, "Meta");
        assertPath(path3, "Test 3 (Shortest path via weights)", "Alice", "Dave");
        
        // Test 4: Target not found at all
        List<UniversityStudent> path4 = finder.findReferralPath(alice, "Netflix");
        assertEquals(0, path4.size(), "Test 4 (Target not found)");

        System.out.println("TestReferralPathFinder: all checks passed.");
    }

    private static UniversityStudent makeStudent(String name, String age, String major, String internships) {
        UniversityStudent s = new UniversityStudent();
        s.setName(name);
        s.setAge(age);
        s.setMajor(major);
        s.setPreviousInternships(internships);
        return s;
    }

    private static void assertPath(List<UniversityStudent> actualPath, String testName, String... expectedNames) {
        if (actualPath.size() != expectedNames.length) {
            throw new AssertionError(testName + " failed: Expected path length " + expectedNames.length + ", got " + actualPath.size());
        }
        for (int i = 0; i < expectedNames.length; i++) {
            if (!actualPath.get(i).getName().equals(expectedNames[i])) {
                throw new AssertionError(testName + " failed: Expected node " + i + " to be " + expectedNames[i] + ", got " + actualPath.get(i).getName());
            }
        }
        System.out.println(testName + " passed!");
    }

    private static void assertEquals(int expected, int actual, String label) {
        if (expected != actual) {
            throw new AssertionError(label + " failed: Expected " + expected + ", got " + actual);
        }
        System.out.println(label + " passed!");
    }
}