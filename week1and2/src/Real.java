import java.util.*;

public class Real {

    // pageUrl -> visit count
    private HashMap<String, Integer> pageViews;

    // pageUrl -> unique users
    private HashMap<String, HashSet<String>> uniqueVisitors;

    // traffic source -> count
    private HashMap<String, Integer> trafficSources;

    public Real() {
        pageViews = new HashMap<>();
        uniqueVisitors = new HashMap<>();
        trafficSources = new HashMap<>();
    }

    // Process page view event
    public void processEvent(String url, String userId, String source) {

        // Update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Update unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // Update traffic source
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    // Display dashboard
    public void getDashboard() {

        System.out.println("\n--- Top Pages ---");

        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(pageViews.entrySet());

        // Sort pages by visits
        list.sort((a, b) -> b.getValue() - a.getValue());

        int count = 0;

        for (Map.Entry<String, Integer> entry : list) {

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println((count + 1) + ". " + url +
                    " - " + views + " views (" + unique + " unique)");

            count++;
            if (count == 10) break;
        }

        System.out.println("\n--- Traffic Sources ---");

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        Real analytics = new Real();

        analytics.processEvent("/article/breaking-news", "user123", "google");
        analytics.processEvent("/article/breaking-news", "user456", "facebook");
        analytics.processEvent("/sports/championship", "user789", "google");
        analytics.processEvent("/sports/championship", "user123", "direct");
        analytics.processEvent("/article/breaking-news", "user789", "google");

        analytics.getDashboard();
    }
}