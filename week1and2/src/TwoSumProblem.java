import java.util.*;

class Transaction {
    int id;
    int amount;
    String merchant;
    String time;

    Transaction(int id, int amount, String merchant, String time) {
        this.id = id;
        this.amount = amount;
        this.merchant = merchant;
        this.time = time;
    }
}

public class TwoSumProblem {

    List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(int id, int amount, String merchant, String time) {
        transactions.add(new Transaction(id, amount, merchant, time));
    }

    // Classic Two Sum
    public void findTwoSum(int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {

                System.out.println("Two-Sum Found: " +
                        map.get(complement).id + " + " +
                        t.id + " = " + target);
                return;
            }

            map.put(t.amount, t);
        }

        System.out.println("No Two-Sum found");
    }

    // Detect duplicate transactions
    public void detectDuplicates() {

        HashMap<Integer, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            map.putIfAbsent(t.amount, new ArrayList<>());
            map.get(t.amount).add(t);
        }

        System.out.println("Duplicate Transactions:");

        for (Map.Entry<Integer, List<Transaction>> entry : map.entrySet()) {

            if (entry.getValue().size() > 1) {

                for (Transaction t : entry.getValue()) {
                    System.out.println("ID:" + t.id +
                            " Amount:" + t.amount +
                            " Merchant:" + t.merchant);
                }
                System.out.println();
            }
        }
    }

    // Simple K-Sum (brute force)
    public void findKSum(int k, int target) {

        int n = transactions.size();

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                for (int l = j + 1; l < n && k == 3; l++) {

                    int sum = transactions.get(i).amount +
                            transactions.get(j).amount +
                            transactions.get(l).amount;

                    if (sum == target) {

                        System.out.println("K-Sum Found: " +
                                transactions.get(i).id + ", " +
                                transactions.get(j).id + ", " +
                                transactions.get(l).id);

                        return;
                    }
                }
            }
        }

        System.out.println("No K-Sum found");
    }

    public static void main(String[] args) {

        TwoSumProblem system = new TwoSumProblem();

        system.addTransaction(1, 500, "Store A", "10:00");
        system.addTransaction(2, 300, "Store B", "10:15");
        system.addTransaction(3, 200, "Store C", "10:30");
        system.addTransaction(4, 500, "Store A", "10:40");

        system.findTwoSum(500);
        system.detectDuplicates();
        system.findKSum(3, 1000);
    }
}