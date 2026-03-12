import java.util.*;

public class Plagiarism {

    // n-gram -> set of documents
    private HashMap<String, Set<String>> ngramIndex;

    // document -> list of n-grams
    private HashMap<String, List<String>> documentNgrams;

    private int N = 5; // 5-gram

    public Plagiarism() {
        ngramIndex = new HashMap<>();
        documentNgrams = new HashMap<>();
    }

    // Break text into n-grams
    private List<String> generateNgrams(String text) {

        List<String> ngrams = new ArrayList<>();

        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    // Add document to database
    public void addDocument(String docName, String text) {

        List<String> ngrams = generateNgrams(text);
        documentNgrams.put(docName, ngrams);

        for (String gram : ngrams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docName);
        }
    }

    // Analyze document similarity
    public void analyzeDocument(String docName) {

        List<String> ngrams = documentNgrams.get(docName);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            Set<String> docs = ngramIndex.getOrDefault(gram, new HashSet<>());

            for (String doc : docs) {

                if (!doc.equals(docName)) {

                    matchCount.put(doc, matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : matchCount.entrySet()) {

            String otherDoc = entry.getKey();
            int matches = entry.getValue();

            double similarity = (matches * 100.0) / ngrams.size();

            System.out.println("Found " + matches + " matching n-grams with " + otherDoc);
            System.out.println("Similarity: " + similarity + "%");

            if (similarity > 50) {
                System.out.println("⚠ PLAGIARISM DETECTED\n");
            }
        }
    }

    public static void main(String[] args) {

        Plagiarism detector = new Plagiarism();

        String doc1 = "Artificial intelligence is transforming the world of technology and innovation";
        String doc2 = "Artificial intelligence is transforming the world with modern technology and innovation";
        String doc3 = "Data science and machine learning are popular fields today";

        detector.addDocument("essay_089.txt", doc1);
        detector.addDocument("essay_092.txt", doc2);
        detector.addDocument("essay_123.txt", doc3);

        detector.analyzeDocument("essay_092.txt");
    }
}