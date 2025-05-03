package domain;

public class Member {
    private String memberId;
    private String name;
    private String phoneNumber;
    private String address;
    private boolean isActive;
    private double balance;

    public Member(String memberId, String name, String phoneNumber, String address) {
        this.memberId = memberId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = true;
        this.balance = 0.0;
    }

    // Getters and Setters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Member ID: %s\nName: %s\nPhone: %s\nAddress: %s\nBalance: %.2f\nActive: %b",
                memberId, name, phoneNumber, address, balance, isActive);
    }
}
