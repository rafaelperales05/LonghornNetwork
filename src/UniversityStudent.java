
import java.util.*;


public class UniversityStudent extends Student {
    // TODO: Constructor and additional methods to be implemented 
    
    public String getName(){ 
        return name; 
    }
    public int getAge(){ 
        return age; 
    }
    public String getGender(){ 
        return gender; 
    } 

    public int getYear(){ 
        return year; 
    } 

    public String getMajor(){ 
        return major; 
    } 
    public double getGPA(){ 
        return gpa; 
    } 

    public ArrayList<String> getRoommatePreferences(){ 
        return (ArrayList<String>)roommatePreferences; 
    }
    public ArrayList<String> getPreviousInternships(){ 
        return (ArrayList<String>) previousInternships; 
    }

    private UniversityStudent roommate;

    public UniversityStudent getRoommate() {
        return roommate;
    }

    public void setRoommate(UniversityStudent roommate) {
        this.roommate = roommate;
    }

    @Override
    public int calculateConnectionStrength(Student other) {
        // TODO: Implement the connection strength logic
        return 0;
    }
 
    public void setRoommateName(String name){ 
        this.name = name; 
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) throws NumberFormatException {
        this.age = Integer.parseInt(age);
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setYear(String year) throws NumberFormatException {
        this.year = Integer.parseInt(year);
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGpa(String gpa) throws NumberFormatException {
        this.gpa = Double.parseDouble(gpa);
    }

    public void setRoommatePreferences(String prefs) {
        if (prefs != null && !prefs.isEmpty()) {
            this.roommatePreferences = new ArrayList<>(Arrays.asList(prefs.split("\\s*,\\s*")));
        } else {
            this.roommatePreferences = new ArrayList<>();
        }
    }

    public void setPreviousInternships(String internships) {
        if (internships != null && !internships.isEmpty()) {
            this.previousInternships = new ArrayList<>(Arrays.asList(internships.split("\\s*,\\s*")));
        } else {
            this.previousInternships = new ArrayList<>();
        }
    }
}

