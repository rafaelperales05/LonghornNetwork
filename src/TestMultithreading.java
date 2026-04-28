import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestMultithreading {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Running multithreading tests...");

        UniversityStudent alice = new UniversityStudent();
        alice.setName("Alice");

        UniversityStudent bob = new UniversityStudent();
        bob.setName("Bob");

        // Use thread pool for concurrent friend/chat request testing
        ExecutorService pool = Executors.newFixedThreadPool(10);

        // Schedule friend requests in concurrent cross-directions (stress test deadlock safety)
        pool.execute(new FriendRequestThread(alice, bob));
        pool.execute(new FriendRequestThread(bob, alice));

        // Schedule multiple chat threads
        for (int i = 0; i < 5; i++) {
            pool.execute(new ChatThread(alice, bob, "Hello Bob " + i));
            pool.execute(new ChatThread(bob, alice, "Hi Alice " + i));
        }

        // Wait to finish
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);

        // Assert state correctly saved
        System.out.println("\nAlice's friend list size: " + alice.getFriends().size());
        System.out.println("Alice's chat history with Bob:");
        List<String> aChat = alice.getChatHistory(bob);
        for (String m : aChat) {
            System.out.println("  " + m);
        }
        
        System.out.println("\nBob's chat history with Alice:");
        List<String> bChat = bob.getChatHistory(alice);
        for (String m : bChat) {
            System.out.println("  " + m);
        }

        if (aChat.size() == 10 && bChat.size() == 10) {
            System.out.println("\nMultithreading test PASSED! No deadlocks and fully updated histories.");
        } else {
            System.out.println("\nMultithreading test FAILED.");
        }
    }
}