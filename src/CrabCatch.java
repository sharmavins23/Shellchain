/**
 * @author vinst
 */
public class CrabCatch extends Transaction {
    private String mafia;
    private int garbageAmount;

    public CrabCatch(River sender, int amount, String mafia, int garbageAmount) {
        super(sender, amount);
        this.mafia = mafia;
        this.garbageAmount = garbageAmount;
    }

    public String getMafia() {
        return mafia;
    }

    public int getGarbageAmount() {
        return garbageAmount;
    }
}
