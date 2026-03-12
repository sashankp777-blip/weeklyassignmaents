import java.util.HashMap;

class TokenBucket {

    int tokens;
    int maxTokens;
    long lastRefillTime;
    int refillRate; // tokens per hour

    public TokenBucket(int maxTokens, int refillRate) {
        this.tokens = maxTokens;
        this.maxTokens = maxTokens;
        this.refillRate = refillRate;
        this.lastRefillTime = System.currentTimeMillis();
    }

    // refill tokens based on time
    private void refill() {

        long now = System.currentTimeMillis();
        long elapsedTime = now - lastRefillTime;

        int tokensToAdd = (int) (elapsedTime / 3600000.0 * refillRate);

        if (tokensToAdd > 0) {
            tokens = Math.min(maxTokens, tokens + tokensToAdd);
            lastRefillTime = now;
        }
    }

    // check request permission
    public synchronized boolean allowRequest() {

        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }

        return false;
    }

    public int getRemainingTokens() {
        return tokens;
    }
}

public class Distributed {

    // clientId -> TokenBucket
    private HashMap<String, TokenBucket> clients;

    private int limit = 1000; // requests per hour

    public Distributed() {
        clients = new HashMap<>();
    }

    public void checkRateLimit(String clientId) {

        clients.putIfAbsent(clientId, new TokenBucket(limit, limit));

        TokenBucket bucket = clients.get(clientId);

        if (bucket.allowRequest()) {
            System.out.println("Allowed (" + bucket.getRemainingTokens() + " requests remaining)");
        } else {
            System.out.println("Denied (Rate limit exceeded)");
        }
    }

    public static void main(String[] args) {

        Distributed limiter = new Distributed();

        String client = "abc123";

        limiter.checkRateLimit(client);
        limiter.checkRateLimit(client);
        limiter.checkRateLimit(client);
    }
}