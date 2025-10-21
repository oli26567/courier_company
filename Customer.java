import java.util.ArrayList;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private ArrayList<String> bookedPackages;

    // Constructor
    public Customer(String customerId, String name, String email, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bookedPackages = new ArrayList<>();
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getBookedPackages() {
        return bookedPackages;
    }

    // Methods for adding/removing packages
    public void addPackage(String trackingId) {
        bookedPackages.add(trackingId);
    }

    public void removePackage(String trackingId) {
        bookedPackages.remove(trackingId);
    }
}
