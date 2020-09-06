/**
 * @author vinst
 */
public class River {
    private String name;
    private int amount;
    private int garbageAmount;

    public River(String name, int amount, int garbageAmount) {
        this.name = name;
        this.amount = amount;
        this.garbageAmount = garbageAmount;
    }

    public int getAmount() {
        return amount;
    }

    public int getGarbageAmount() {
        return garbageAmount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
