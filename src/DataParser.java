import java.io.*;
import java.util.*; 

public class DataParser {

    public static List<UniversityStudent> parseStudents(String filename) throws IOException { 

        try(Scanner scanner = new Scanner(new File(filename))){ 
            List<UniversityStudent> students = new ArrayList<>();   

            // Create list that will be used to track lines of only 1 student 
            // at a time 
            List<String> CurrStudentLines = new ArrayList<>(); 

            while(scanner.hasNextLine()){ 
                String line = scanner.nextLine().trim();  
                
                if (line.isEmpty() || line.startsWith("Student:")){  
                    if (!CurrStudentLines.isEmpty()){ 
                        UniversityStudent s = parseSingleStudent(CurrStudentLines);
                        if (s != null) {
                            students.add(s); 
                        }
                        CurrStudentLines.clear(); 
                    }
                    if (line.startsWith("Student:")) {
                        CurrStudentLines.add(line);
                    }
                } else { 
                    CurrStudentLines.add(line); 
                } 
            }
            
            // If there are no more lines left in the file, process the last student!
            if (!CurrStudentLines.isEmpty()) {
                UniversityStudent s = parseSingleStudent(CurrStudentLines);
                if (s != null) {
                    students.add(s); 
                }
            }
            return students;
        } catch(IOException e){ 
            System.out.println("Error reading file: " + e); 
            return null;
        }
    } 


    /**
     * Helper function that returns a University Student. 
     * @param StudentLines Array that contains input  
     * @return UniversityStudent object 
     */ 
    private static UniversityStudent parseSingleStudent(List<String> StudentLines){  
        UniversityStudent student = new UniversityStudent(); 
        Set<String> foundFields = new HashSet<>();

        for (String line : StudentLines){ 

            // Parse by splitting at the first colon
            String[] fields = line.split(":", 2);  
            if (fields.length > 1){ 

                String key = fields[0].trim();
                String value = fields[1].trim();
                
                foundFields.add(key);

                switch (key){ 
                    
                    case "Student":  
                        break; 
                    
                    case "Name": 
                        student.setName(value);
                        break; 

                    case "Age": 
                        try {
                            student.setAge(value);
                        } catch (NumberFormatException e) {
                            System.out.println("Number format error: Invalid number format for age: '" + value + "' in student entry for " + student.name + ".");
                            return null; // Skip this student
                        }
                        break; 

                    case "Gender": 
                        student.setGender(value);
                        break; 

                    case "Year": 
                        try {
                            student.setYear(value);
                        } catch (NumberFormatException e) {
                            System.out.println("Number format error: Invalid number format for Year: '" + value + "' in student entry for " + student.name + ".");
                            return null;
                        }
                        break; 

                    case "Major": 
                        student.setMajor(value);
                        break; 

                    case "GPA": 
                        try {
                            student.setGpa(value);
                        } catch (NumberFormatException e) {
                            System.out.println("Number format error: Invalid number format for GPA: '" + value + "' in student entry for " + student.name + ".");
                            return null;
                        }
                        break; 

                    case "RoommatePreferences": 
                        student.setRoommatePreferences(value);
                        break; 

                    case "PreviousInternships": 
                        student.setPreviousInternships(value);
                        break; 
                    
                    default: 
                        break;
                }
            } else if (!line.trim().equals("Student:") && !line.trim().isEmpty()) {
                // If there's no colon and it's not the "Student:" header or a blank line, it's missing the colon
                String firstWord = line.split(" ")[0];
                System.out.println("Parsing error: Incorrect format in line: '" + line + "'. Expected format '" + firstWord + ": <value>'.");
                return null; // Skip parsing this invalid student completely
            }

        } 
        
        // Define all required fields that should have been parsed
        String[] requiredFields = {"Name", "Age", "Gender", "Year", "Major", "GPA", "RoommatePreferences", "PreviousInternships"};
        for (String req : requiredFields) {
            if (!foundFields.contains(req)) {
                String studentName = student.name != null ? student.name : "unknown";
                System.out.println("Parsing error: Missing required field '" + req + "' in student entry for " + studentName + ".");
                return null; // Skip this student due to missing field
            }
        }
        
        return student; 
    }
}
