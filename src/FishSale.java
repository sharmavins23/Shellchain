/**
 * @author vinst
 */
public class FishSale extends Transaction {
    private int garbageAmount;

    public FishSale(River sender, int amount, int garbageAmount) {
        super(sender, amount);
        this.garbageAmount = garbageAmount;
    }

    public int getGarbageAmount() {
        return garbageAmount;
    }
}
