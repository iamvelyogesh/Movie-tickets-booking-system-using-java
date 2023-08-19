package movie;
import java.math.BigDecimal;
import java.util.Date;


public class Transaction {
    private int transactionId;
    private int showId;
    private String customerName;
    private BigDecimal amount;
    private Date paymentTime;

    public Transaction(int transactionId, int showId, String customerName, BigDecimal amount, Date paymentTime) {
        this.transactionId = transactionId;
        this.showId = showId;
        this.customerName = customerName;
        this.amount = amount;
        this.paymentTime = paymentTime;
    }

    // Getters and Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = BigDecimal.valueOf(amount);
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", showId=" + showId +
                ", customerName='" + customerName + '\'' +
                ", amount=" + amount +
                ", paymentTime=" + paymentTime +
                '}';
    }
    
    public Transaction() {
    	
    }
}