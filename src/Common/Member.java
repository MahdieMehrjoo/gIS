package Common;

public class Member {
    private static final String SPLITTER = "|";
    
    private int memberId;
    private String name;
    private String familyName;
    private String nationalCode;
    private String phoneNumber;
    private String address;

    public Member() {
    }

    public Member(int memberId, String name, String familyName, String nationalCode, String phoneNumber, String address) {
        setMemberId(memberId);
        setName(name);
        setFamilyName(familyName);
        setNationalCode(nationalCode);
        setPhoneNumber(phoneNumber);
        setAddress(address);
    }

    // Getters and Setters with validation
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        if (memberId <= 0) {
            throw new IllegalArgumentException("شناسه عضو باید بزرگتر از صفر باشد");
        }
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("نام نمی‌تواند خالی باشد");
        }
        if (name.length() < 2 || name.length() > 50) {
            throw new IllegalArgumentException("نام باید بین ۲ تا ۵۰ کاراکتر باشد");
        }
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        if (familyName == null || familyName.trim().isEmpty()) {
            throw new IllegalArgumentException("نام خانوادگی نمی‌تواند خالی باشد");
        }
        if (familyName.length() < 2 || familyName.length() > 50) {
            throw new IllegalArgumentException("نام خانوادگی باید بین ۲ تا ۵۰ کاراکتر باشد");
        }
        this.familyName = familyName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        if (nationalCode == null || nationalCode.trim().isEmpty()) {
            throw new IllegalArgumentException("کد ملی نمی‌تواند خالی باشد");
        }
        if (!nationalCode.matches("\\d{10}")) {
            throw new IllegalArgumentException("کد ملی باید ۱۰ رقم باشد");
        }
        this.nationalCode = nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("شماره تلفن نمی‌تواند خالی باشد");
        }
        if (!phoneNumber.matches("\\d{11}")) {
            throw new IllegalArgumentException("شماره تلفن باید ۱۱ رقم باشد");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("آدرس نمی‌تواند خالی باشد");
        }
        if (address.length() < 5 || address.length() > 200) {
            throw new IllegalArgumentException("آدرس باید بین ۵ تا ۲۰۰ کاراکتر باشد");
        }
        this.address = address;
    }

    @Override
    public String toString() {
        return memberId + SPLITTER + 
               name + SPLITTER + 
               familyName + SPLITTER + 
               nationalCode + SPLITTER + 
               phoneNumber + SPLITTER + 
               address;
    }
}