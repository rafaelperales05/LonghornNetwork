public class ChatThread implements Runnable {
    private UniversityStudent sender;
    private UniversityStudent receiver;
    private String message;

    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    @Override
    public void run() {
        // Build the timestamped/formatted log message
        String chatEntrySender = "You: " + message;
        String chatEntryReceiver = sender.getName() + ": " + message;

        // Add to each other's chat histories using thread-safe methods internally
        sender.addChatMessage(receiver, chatEntrySender);
        receiver.addChatMessage(sender, chatEntryReceiver);

        // Log the interaction
        System.out.println(sender.getName() + " sent a message to " + receiver.getName() + ": \"" + message + "\"");
    }
}
