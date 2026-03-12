import java.util.*;

public class Autocomplete {

    // query -> frequency
    private HashMap<String, Integer> queryFrequency;

    public Autocomplete() {
        queryFrequency = new HashMap<>();
    }

    // Add or update search query
    public void updateFrequency(String query) {
        queryFrequency.put(query, queryFrequency.getOrDefault(query, 0) + 1);
    }

    // Search suggestions based on prefix
    public void search(String prefix) {

        List<Map.Entry<String, Integer>> results = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : queryFrequency.entrySet()) {

            if (entry.getKey().startsWith(prefix)) {
                results.add(entry);
            }
        }

        // Sort by frequency descending
        results.sort((a, b) -> b.getValue() - a.getValue());

        System.out.println("Suggestions for \"" + prefix + "\":");

        int count = 0;

        for (Map.Entry<String, Integer> entry : results) {

            System.out.println((count + 1) + ". " +
                    entry.getKey() + " (" +
                    entry.getValue() + " searches)");

            count++;

            if (count == 10) break;
        }
    }

    public static void main(String[] args) {

        Autocomplete system = new Autocomplete();

        system.updateFrequency("java tutorial");
        system.updateFrequency("java tutorial");
        system.updateFrequency("javascript");
        system.updateFrequency("java download");
        system.updateFrequency("java features");

        system.search("jav");
    }
}