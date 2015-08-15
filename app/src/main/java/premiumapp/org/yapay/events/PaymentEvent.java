package premiumapp.org.yapay.events;

public class PaymentEvent {

    private String sum;
    private String vendor;
    private String account;

    public PaymentEvent(String sum, String vendor, String account) {
        this.sum = sum;
        this.vendor = vendor;
        this.account = account;
    }

    public String getSum() {
        return sum;
    }

    public String getVendor() {
        return vendor;
    }

    public String getAccount() {
        return account;
    }
}
