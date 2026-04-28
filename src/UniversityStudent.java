
import java.util.*;
import java.util.concurrent.*;

public class UniversityStudent extends Student {
    
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

    // Concurrency components
    private Set<UniversityStudent> friends = ConcurrentHashMap.newKeySet();
    private Map<UniversityStudent, List<String>> chatHistory = new ConcurrentHashMap<>();

    public void addFriend(UniversityStudent friend) {
        friends.add(friend);
    }

    public Set<UniversityStudent> getFriends() {
        return friends;
    }

    public synchronized void addChatMessage(UniversityStudent other, String message) {
        chatHistory.putIfAbsent(other, new ArrayList<>());
        chatHistory.get(other).add(message);
    }

    public synchronized List<String> getChatHistory(UniversityStudent other) {
        return chatHistory.containsKey(other) ? new ArrayList<>(chatHistory.get(other)) : new ArrayList<>();
    }

    public UniversityStudent getRoommate() {
        return roommate;
    }

    public void setRoommate(UniversityStudent roommate) {
        this.roommate = roommate;
    }

    @Override
    public int calculateConnectionStrength(Student other) {
        int total = 0; 

        if (other instanceof UniversityStudent) { 
            UniversityStudent otherStudent = (UniversityStudent) other; 

            //check if roomates  
            if (this.roommate != null && this.roommate.equals(otherStudent) ){ 
                total += 4; 
            }

            // check if shared internships    
            ArrayList<String> sharedInternships = otherStudent.getPreviousInternships(); 
            for (String internship : this.previousInternships){  

                if (sharedInternships.contains(internship)){ 
                    total += 3; 
                }
            }


            // check if same major  
            if (this.getMajor().equals(otherStudent.getMajor())){ 
                total += 2; 
            }

            // check if same age 
            if (this.getAge() == otherStudent.getAge()){ 
                total += 1; 
            }

        } 

        return total;
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

