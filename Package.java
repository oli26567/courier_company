public class Package {
    private String trackingId;
    private String senderName;
    private String recipientName;
    private double weight;
    private String deliveryClass;
    private String status;
    private String customerId;
    private String courierId;

    // Constructor
    public Package(String trackingId, String senderName, String recipientName, double weight, String deliveryClass, String customerId, String courierId) {
        this.trackingId = trackingId;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.weight = weight;
        this.deliveryClass = deliveryClass;
        this.status = "Booked"; // Default status
        this.customerId = customerId;
        this.courierId = courierId; // Assign the associated courier ID
    }

    // Getters
    public String getTrackingId() {
        return trackingId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public double getWeight() {
        return weight;
    }

    public String getDeliveryClass() {
        return deliveryClass;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCourierId() {
        return courierId;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    // Additional method to provide a brief package overview
    public String getPackageOverview() {
        return "Package ID: " + trackingId + ", Sender: " + senderName + ", Recipient: " + recipientName
                + ", Weight: " + weight + "kg, Status: " + status + ", Customer ID: " + customerId + ", Courier ID: " + courierId;
    }
}
