import java.io.IOException;
import java.util.List;

public class TestParser {
    public static void main(String[] args) {
        // Array of the file paths for all normal inputs and error cases
        String[] filePaths = {
            "../testing/testing_checkpointone/inputs/normal_1.txt",
            "../testing/testing_checkpointone/inputs/normal_2.txt",
            "../testing/testing_checkpointone/inputs/normal_3.txt",
            "../testing/testing_checkpointone/inputs/normal_4.txt",
            "../testing/testing_checkpointone/inputs/normal_5.txt",
            "../testing/testing_checkpointone/inputs/testing_incorrect_format_exception.txt",
            "../testing/testing_checkpointone/inputs/testing_invalid_age_exception.txt",
            "../testing/testing_checkpointone/inputs/testing_invalid_gpa_exception.txt",
            "../testing/testing_checkpointone/inputs/testing_missing_field_exception.txt"
        };

        for (int i = 0; i < filePaths.length; i++) {
            System.out.println("=========================================");
            System.out.println("Testing DataParser on: " + filePaths[i].substring(filePaths[i].lastIndexOf('/') + 1));
            System.out.println("=========================================");
            
            try {
                List<UniversityStudent> students = DataParser.parseStudents(filePaths[i]);
                
                if (students == null || students.isEmpty()) {
                    System.out.println("No students found or error occurred.");
                } else {
                    for (int j = 0; j < students.size(); j++) {
                        UniversityStudent s = students.get(j);
                        System.out.println("Student " + (j + 1) + ":");
                        System.out.println("  Name: " + s.name);
                        System.out.println("  Age: " + s.age);
                        System.out.println("  Gender: " + s.gender);
                        System.out.println("  Year: " + s.year);
                        System.out.println("  Major: " + s.major);
                        System.out.println("  GPA: " + s.gpa);
                        System.out.println("  Roommate Preferences: " + s.roommatePreferences);
                        System.out.println("  Previous Internships: " + s.previousInternships);
                        System.out.println();
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to read file: " + filePaths[i]);
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
