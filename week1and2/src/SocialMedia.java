import java.util.*;

public class SocialMedia {

    // HashMap to store username -> userId
    private HashMap<String, Integer> usernameMap;

    // HashMap to store username attempt frequency
    private HashMap<String, Integer> attemptCount;

    public SocialMedia() {
        usernameMap = new HashMap<>();
        attemptCount = new HashMap<>();
    }

    // Register a new user
    public void registerUser(String username, int userId) {
        usernameMap.put(username, userId);
    }

    // Check username availability
    public boolean checkAvailability(String username) {

        // Increase attempt count
        attemptCount.put(username, attemptCount.getOrDefault(username, 0) + 1);

        // Check if username exists
        if (usernameMap.containsKey(username)) {
            return false;
        }

        return true;
    }

    // Suggest alternative usernames
    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;

            if (!usernameMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        String modified = username.replace("_", ".");
        if (!usernameMap.containsKey(modified)) {
            suggestions.add(modified);
        }

        return suggestions;
    }

    // Get most attempted username
    public String getMostAttempted() {

        String mostUser = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptCount.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                mostUser = entry.getKey();
            }
        }

        return mostUser + " (" + max + " attempts)";
    }

    // Main method for testing
    public static void main(String[] args) {

        SocialMedia sm = new SocialMedia();

        // Register existing users
        sm.registerUser("john_doe", 101);
        sm.registerUser("admin", 102);
        sm.registerUser("alex", 103);

        System.out.println("Check john_doe: " + sm.checkAvailability("john_doe"));
        System.out.println("Check jane_smith: " + sm.checkAvailability("jane_smith"));

        System.out.println("Suggestions: " + sm.suggestAlternatives("john_doe"));

        // Simulating attempts
        sm.checkAvailability("admin");
        sm.checkAvailability("admin");
        sm.checkAvailability("admin");

        System.out.println("Most Attempted: " + sm.getMostAttempted());
    }
}