/**
 * @author vinst
 */
public abstract class Transaction {
    private River sender;
    private int amount;

    public Transaction(River sender, int amount) {
        this.sender = sender;
        this.amount = amount;
    }

    public River getSender() {
        return sender;
    }

    public int getAmount() {
        return amount;
    }
}