import java.util.*;

public class Ecommerce {

    // productId -> stock count
    private HashMap<String, Integer> stockMap;

    // productId -> waiting list of users
    private HashMap<String, Queue<Integer>> waitingList;

    public Ecommerce() {
        stockMap = new HashMap<>();
        waitingList = new HashMap<>();
    }

    // Add product with stock
    public void addProduct(String productId, int stock) {
        stockMap.put(productId, stock);
        waitingList.put(productId, new LinkedList<>());
    }

    // Check stock availability
    public void checkStock(String productId) {
        int stock = stockMap.getOrDefault(productId, 0);
        System.out.println(productId + " -> " + stock + " units available");
    }

    // Purchase item
    public synchronized void purchaseItem(String productId, int userId) {

        int stock = stockMap.getOrDefault(productId, 0);

        if (stock > 0) {
            stock--;
            stockMap.put(productId, stock);

            System.out.println("User " + userId + " -> Success, " + stock + " units remaining");
        }
        else {
            Queue<Integer> queue = waitingList.get(productId);
            queue.add(userId);

            System.out.println("User " + userId + " -> Added to waiting list, position #" + queue.size());
        }
    }

    // Main method for testing
    public static void main(String[] args) {

        Ecommerce store = new Ecommerce();

        // Add product
        store.addProduct("IPHONE15_256GB", 5);

        store.checkStock("IPHONE15_256GB");

        store.purchaseItem("IPHONE15_256GB", 12345);
        store.purchaseItem("IPHONE15_256GB", 67890);
        store.purchaseItem("IPHONE15_256GB", 11111);
        store.purchaseItem("IPHONE15_256GB", 22222);
        store.purchaseItem("IPHONE15_256GB", 33333);
        store.purchaseItem("IPHONE15_256GB", 99999); // waiting list

        store.checkStock("IPHONE15_256GB");
    }
}