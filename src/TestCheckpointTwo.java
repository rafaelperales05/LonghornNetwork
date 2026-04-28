import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestCheckpointTwo {
    public static void main(String[] args) throws IOException {
        String filePath = "..\\testing\\testingcheckpointtwo\\pod_sample.txt";
        List<UniversityStudent> students = DataParser.parseStudents(filePath);

        if (students == null) {
            System.out.println("Failed to parse students.");
            return;
        }

        System.out.println("Roommate Assignment:");
        GaleShapley.assignRoommates(students);
        for (UniversityStudent s : students) {
            if (s.getRoommate() != null) {
                System.out.println(s.getName() + " is roommates with " + s.getRoommate().getName());
            }
        }

        System.out.println("");

        // The prompt says: "This test assumes you start your pod formation by choosing Issac first."
        // Let's bring Issac to the front of the list, followed by Timmy to match the sample.
        Collections.swap(students, students.indexOf(getStudentByName(students, "Issac")), 0);
        Collections.swap(students, students.indexOf(getStudentByName(students, "Timmy")), 1);

        StudentGraph graph = new StudentGraph(students);
        PodFormation podFormation = new PodFormation(graph);
        
        // Looks like Max pod size is 4 in the sample output (Pod 0 has 4 members)
        podFormation.formPods(4);
    }

    private static UniversityStudent getStudentByName(List<UniversityStudent> students, String name) {
        for (UniversityStudent s : students) {
            if (s.getName().equals(name)) return s;
        }
        return null;
    }
}
