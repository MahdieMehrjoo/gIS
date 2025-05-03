package common;

public class Borrow {
    private Long id; // کلید اصلی
    private Long bookId;
    private Long memberId;
    private String borrowDate;
    private String returnDate;
    private String actualReturnDate;
    private boolean isReturned;
    private boolean isRenewed;

    // سازنده پیش‌فرض
    public Borrow() {
    }

    // سازنده با پارامتر
    public Borrow(Long id, Long bookId, Long memberId, String borrowDate, 
                 String returnDate, String actualReturnDate, boolean isReturned, boolean isRenewed) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.actualReturnDate = actualReturnDate;
        this.isReturned = isReturned;
        this.isRenewed = isRenewed;
    }

    // Getter و Setter ها
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("شناسه نمی‌تواند خالی باشد");
        }
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        if (bookId == null) {
            throw new IllegalArgumentException("شناسه کتاب نمی‌تواند خالی باشد");
        }
        this.bookId = bookId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("شناسه عضو نمی‌تواند خالی باشد");
        }
        this.memberId = memberId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        if (borrowDate == null || borrowDate.trim().isEmpty()) {
            throw new IllegalArgumentException("تاریخ امانت نمی‌تواند خالی باشد");
        }
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        if (returnDate == null || returnDate.trim().isEmpty()) {
            throw new IllegalArgumentException("تاریخ موعد بازگشت نمی‌تواند خالی باشد");
        }
        this.returnDate = returnDate;
    }

    public String getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(String actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public boolean isRenewed() {
        return isRenewed;
    }

    public void setRenewed(boolean renewed) {
        isRenewed = renewed;
    }

    @Override
    public String toString() {
        return String.format("امانت:%nشناسه: %d%nکتاب: %d%nعضو: %d%nتاریخ امانت: %s%nموعد بازگشت: %s%n" +
                "تاریخ بازگشت واقعی: %s%nوضعیت بازگشت: %s%nوضعیت تمدید: %s",
                id, bookId, memberId, borrowDate, returnDate,
                actualReturnDate != null ? actualReturnDate : "برنگشته",
                isReturned ? "بازگشت شده" : "برنگشته",
                isRenewed ? "تمدید شده" : "تمدید نشده");
    }
} 