import java.util.*;

public class GaleShapley { 

    /**
     * Assigns roommates based on the Gale-Shapley stable matching algorithm adapted for a single pool.
     * @param students List of UniversityStudent objects to be matched.
     */
    public static void assignRoommates(List<UniversityStudent> students) {   
        Map<String, UniversityStudent> nameToStudent = new HashMap<>();
        
        Map<UniversityStudent, Integer> proposedToCount = new HashMap<>();
        
        Queue<UniversityStudent> unpaired = new LinkedList<>();

        for (UniversityStudent student : students) {
            student.setRoommate(null); // Ensure everyone starts with a clean slate
            nameToStudent.put(student.getName().trim(), student);

            List<String> prefs = student.getRoommatePreferences();
            // If the student has an empty or null preference list, they remain unpaired (skipped)
            if (prefs != null && !prefs.isEmpty()) {
                unpaired.add(student);
                proposedToCount.put(student, 0); // Start at the 0th preference
            }
        }

        while (!unpaired.isEmpty()) {
            UniversityStudent proposer = unpaired.poll();
            int proposalIndex = proposedToCount.get(proposer);
            List<String> preferences = proposer.getRoommatePreferences();

            if (preferences == null || proposalIndex >= preferences.size()) {
                continue; // They remain unpaired permanently
            }

            String targetName = preferences.get(proposalIndex).trim();
            UniversityStudent target = nameToStudent.get(targetName);
            proposedToCount.put(proposer, proposalIndex + 1);

            if (target == null || target.equals(proposer)) {
                unpaired.add(proposer); // Add back to queue to try their next preference
                continue;
            }

            UniversityStudent currentRoommate = target.getRoommate();

            if (currentRoommate == null) {
                setMutualRoommates(proposer, target, unpaired);
            } 
            else {
                List<String> targetPrefs = target.getRoommatePreferences();
                
                int proposerRank = getRank(targetPrefs, proposer.getName().trim());
                int currentRank = getRank(targetPrefs, currentRoommate.getName().trim());

                if (proposerRank < currentRank) {
                    setMutualRoommates(proposer, target, unpaired);
                } else {
                    // Target rejects the proposer. Proposer goes back into the queue.
                    unpaired.add(proposer);
                }
            }
        }
    }

    /**
     * Helper to safely get the rank of a student in a preference list.
     * If the student is not on the list, they are assigned the worst possible rank.
     */
    private static int getRank(List<String> preferences, String name) {
        if (preferences == null) return Integer.MAX_VALUE;
        int index = preferences.indexOf(name);
        return index == -1 ? Integer.MAX_VALUE : index;
    }

    /**
     * Helper to establish a mutual roommate pairing and handle the fallout of broken matches.
     */
    private static void setMutualRoommates(UniversityStudent s1, UniversityStudent s2, Queue<UniversityStudent> unpaired) {
        // If s1 is stealing s2, and s2 had a previous roommate, the previous roommate gets dumped
        if (s2.getRoommate() != null) {
            UniversityStudent dumpedStudent = s2.getRoommate();
            dumpedStudent.setRoommate(null);
            
            // Put the dumped student back into the queue so they can continue proposing down their list
            if (!unpaired.contains(dumpedStudent)) {
                unpaired.add(dumpedStudent);
            }
        }

        // Just in case s1 was paired to someone else (handling initial queue state overlaps)
        if (s1.getRoommate() != null) {
            UniversityStudent dumpedStudent = s1.getRoommate();
            dumpedStudent.setRoommate(null);
            if (!unpaired.contains(dumpedStudent)) {
                unpaired.add(dumpedStudent);
            }
        }

        // Assign mutually
        s1.setRoommate(s2);
        s2.setRoommate(s1);
        
        // Ensure neither of the newly matched students is actively sitting in the unpaired queue
        unpaired.remove(s1);
        unpaired.remove(s2);
    }
}