public class FriendRequestThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;

    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void run() {
        // Individual methods are thread-safe, no need for nested locks
        sender.addFriend(receiver);
        receiver.addFriend(sender);
        
        // Log interaction for debugging
        System.out.println(sender.getName() + " sent a friend request to " + receiver.getName() + " and they are now friends.");
    }
}
