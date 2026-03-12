import java.util.*;

class VideoData {
    String videoId;
    String content;

    VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

public class MultiLevel {

    // L1 cache (LRU)
    LinkedHashMap<String, VideoData> L1;

    // L2 cache
    HashMap<String, VideoData> L2;

    // L3 database
    HashMap<String, VideoData> L3;

    int L1_SIZE = 3;

    public MultiLevel() {

        L1 = new LinkedHashMap<String, VideoData>(L1_SIZE, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                return size() > L1_SIZE;
            }
        };

        L2 = new HashMap<>();
        L3 = new HashMap<>();

        // sample database
        L3.put("video_123", new VideoData("video_123", "Movie Trailer"));
        L3.put("video_999", new VideoData("video_999", "Music Video"));
    }

    // Get video
    public void getVideo(String videoId) {

        if (L1.containsKey(videoId)) {
            System.out.println(videoId + " -> L1 Cache HIT");
            return;
        }

        if (L2.containsKey(videoId)) {
            System.out.println(videoId + " -> L2 Cache HIT");

            // promote to L1
            L1.put(videoId, L2.get(videoId));
            return;
        }

        if (L3.containsKey(videoId)) {
            System.out.println(videoId + " -> L3 Database HIT");

            VideoData data = L3.get(videoId);

            // add to caches
            L2.put(videoId, data);
            L1.put(videoId, data);
            return;
        }

        System.out.println(videoId + " -> Video not found");
    }

    // Show cache statistics
    public void getStatistics() {

        System.out.println("\nL1 Cache: " + L1.keySet());
        System.out.println("L2 Cache: " + L2.keySet());
        System.out.println("Database: " + L3.keySet());
    }

    public static void main(String[] args) {

        MultiLevel cache = new MultiLevel();

        cache.getVideo("video_123");
        cache.getVideo("video_123"); // L1 hit
        cache.getVideo("video_999");

        cache.getStatistics();
    }
}