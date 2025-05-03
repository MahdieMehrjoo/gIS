package common;

public class Borrow {
    private Long id; // شناسه منحصر به فرد برای هر امانت
    private Long bookId; // شناسه کتابی که امانت گرفته شده است
    private Long memberId; // شناسه عضوی که کتاب را امانت گرفته است
    private String borrowDate; // تاریخ امانت گرفتن کتاب
    private String returnDate; // تاریخ موعد بازگشت کتاب
    private String actualReturnDate; // تاریخ بازگشت واقعی کتاب (اگر برگردانده شده باشد)
    private boolean isReturned; // وضعیت بازگشت کتاب (آیا کتاب بازگشته یا خیر)
    private boolean isRenewed; // وضعیت تمدید (آیا مدت زمان امانت تمدید شده است یا خیر)

    // سازنده پیش‌فرض که به طور خودکار ایجاد می‌شود
    public Borrow() {
    }

    // سازنده با پارامتر که تمامی ویژگی‌ها را مقداردهی می‌کند
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

    // Getter و Setter ها برای دسترسی و تغییر مقادیر فیلدهای کلاس

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

    // متد toString برای نمایش اطلاعات امانت در قالبی قابل فهم
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
