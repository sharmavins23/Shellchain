import java.util.ArrayList;

/**
 * @author vinst
 */
public class Shell {
    private int index;
    private String previous_hash;
    private int proof;
    private double timestamp;
    private ArrayList<Transaction> transactions;

    public Shell(int index, String previous_hash, int proof, double timestamp, ArrayList<Transaction> transactions) {
        this.index = index;
        this.previous_hash = previous_hash;
        this.proof = proof;
        this.timestamp = timestamp;
        this.transactions = transactions;
    }

    public int getIndex() {
        return index;
    }

    public String getPrevious_hash() {
        return previous_hash;
    }

    public int getProof() {
        return proof;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
