import java.util.ArrayList;

public class Courier {
    private String courierId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private ArrayList<Package> assignedPackages;

    // Constructor
    public Courier(String courierId, String name, String email, String phone, String address) {
        this.courierId = courierId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.assignedPackages = new ArrayList<>();
    }

    // Getters
    public String getCourierId() {
        return courierId;
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

    public ArrayList<Package> getAssignedPackages() {
        return assignedPackages;
    }

    // Methods for package assignment
    public void assignPackage(Package p) {
        assignedPackages.add(p);
    }

    public void markPackageInTransit(String trackingId) {
        for (Package p : assignedPackages) {
            if (p.getTrackingId().equals(trackingId)) {
                p.setStatus("In Transit");
            }
        }
    }

    public void markPackageDelivered(String trackingId) {
        for (Package p : assignedPackages) {
            if (p.getTrackingId().equals(trackingId)) {
                p.setStatus("Delivered");
            }
        }
    }
}
