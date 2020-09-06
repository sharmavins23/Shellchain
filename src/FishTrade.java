/**
 * Fish trade child object of a transaction instance
 *
 * @author vinst
 */
public class FishTrade extends Transaction {
    private River recipient;

    public FishTrade(River sender, int amount, River recipient) {
        super(sender, amount);
        this.recipient = recipient;
    }

    public River getRecipient() {
        return recipient;
    }
}
