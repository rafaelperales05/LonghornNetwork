import java.util.*;
import java.io.*;

public class TestGaleShapley {

    public static void main(String[] args) {
        System.out.println("====== Testing on roommate_sample.txt ======");
        testFromFile("../testing/testingcheckpointtwo/roommate_sample.txt");

        System.out.println("\n====== Testing Edge Cases ======");
        testEdgeCases();
    }

    private static void testFromFile(String relativePath) {
        try {
            // Adjust the path to look relative to the src folder running location
            String path = "testing/testingcheckpointtwo/roommate_sample.txt";
            File f = new File(path);
            if (!f.exists()) {
                path = "../" + path;
            }

            List<UniversityStudent> students = DataParser.parseStudents(path);
            if (students == null || students.isEmpty()) {
                System.out.println("Failed to parse students from file. Make sure the path is correct.");
                return;
            }

            GaleShapley.assignRoommates(students);
            printRoommates(students);

        } catch (Exception e) {
            System.err.println("Error reading file or assigning roommates:");
            e.printStackTrace();
        }
    }

    private static void testEdgeCases() {
        System.out.println("\n--- Case 1: Odd number of students (someone remains single) ---");
        List<UniversityStudent> oddStudents = new ArrayList<>();
        oddStudents.add(createStudent("A", Arrays.asList("B", "C")));
        oddStudents.add(createStudent("B", Arrays.asList("A", "C")));
        oddStudents.add(createStudent("C", Arrays.asList("A", "B")));
        GaleShapley.assignRoommates(oddStudents);
        printRoommates(oddStudents);

        System.out.println("\n--- Case 2: No preferences (nobody gets paired) ---");
        List<UniversityStudent> noPrefStudents = new ArrayList<>();
        noPrefStudents.add(createStudent("X", new ArrayList<>()));
        noPrefStudents.add(createStudent("Y", new ArrayList<>()));
        GaleShapley.assignRoommates(noPrefStudents);
        printRoommates(noPrefStudents);

        System.out.println("\n--- Case 3: One-sided preferences (only works if target accepts, but in stable roommates often nobody pairs if strictly one-sided unless handled) ---");
        List<UniversityStudent> oneSided = new ArrayList<>();
        oneSided.add(createStudent("P1", Arrays.asList("P2")));
        oneSided.add(createStudent("P2", new ArrayList<>())); // P2 doesn't prefer P1
        GaleShapley.assignRoommates(oneSided);
        printRoommates(oneSided);

        System.out.println("\n--- Case 4: Cyclical preferences (A->B, B->C, C->A) ---");
        List<UniversityStudent> cyclic = new ArrayList<>();
        cyclic.add(createStudent("Student1", Arrays.asList("Student2", "Student3")));
        cyclic.add(createStudent("Student2", Arrays.asList("Student3", "Student1")));
        cyclic.add(createStudent("Student3", Arrays.asList("Student1", "Student2")));
        GaleShapley.assignRoommates(cyclic);
        printRoommates(cyclic);
    }

    private static UniversityStudent createStudent(String name, List<String> preferences) {
        UniversityStudent s = new UniversityStudent();
        s.setName(name);
        // We use the empty array string conversion or a direct list assignment if available,
        // Since we don't have a direct set method for the list, we can concatenate to string
        // and use the existing setRoommatePreferences(String).
        if (preferences == null || preferences.isEmpty()) {
            s.setRoommatePreferences("");
        } else {
            String prefsStr = String.join(",", preferences);
            s.setRoommatePreferences(prefsStr);
        }
        return s;
    }

    private static void printRoommates(List<UniversityStudent> students) {
        for (UniversityStudent s : students) {
            String roommateName = (s.getRoommate() != null) ? s.getRoommate().getName() : "None";
            System.out.println(s.getName() + " is paired with: " + roommateName);
        }
    }
}
