package managers;

import common.Borrow;
import filemanager.txtFileManager;
import java.util.ArrayList;
import java.util.List;

public class BorrowManager {
    private final txtFileManager fileManager; // شیء برای مدیریت فایل
    private static final String DELIMITER = "&"; // جداکننده برای ذخیره‌سازی داده‌ها در فایل

    // سازنده که شیء txtFileManager را با نام فایل "borrows.txt" ایجاد می‌کند
    public BorrowManager() {
       this.fileManager = new txtFileManager("borrows.txt");
       fileManager.createFile(); // اگر فایل وجود نداشته باشد، ایجاد می‌شود
   }

    // متد برای اضافه کردن یک امانت جدید به فایل
    public void insert(Borrow borrow) {
        try {
            // اعتبارسنجی داده‌ها با استفاده از setter ها
            borrow.setId(borrow.getId());
            borrow.setBookId(borrow.getBookId());
            borrow.setMemberId(borrow.getMemberId());
            borrow.setBorrowDate(borrow.getBorrowDate());
            borrow.setReturnDate(borrow.getReturnDate());
            borrow.setActualReturnDate(borrow.getActualReturnDate());
            borrow.setReturned(borrow.isReturned());
            borrow.setRenewed(borrow.isRenewed());

            // تبدیل شیء Borrow به یک رشته برای ذخیره در فایل
            String borrowData = String.join(DELIMITER,
                borrow.getId().toString(),
                borrow.getBookId().toString(),
                borrow.getMemberId().toString(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                borrow.getActualReturnDate() != null ? borrow.getActualReturnDate() : "",
                String.valueOf(borrow.isReturned()),
                String.valueOf(borrow.isRenewed())
            );

            // ذخیره داده امانت در فایل
            fileManager.appendRow(borrowData);
        } catch (IllegalArgumentException e) {
            // در صورت بروز خطا در اعتبارسنجی داده‌ها، خطا را نمایش می‌دهد
            throw new IllegalArgumentException("خطا در اعتبارسنجی داده‌های امانت: " + e.getMessage());
        }
    }

    // متد برای به‌روزرسانی یک امانت موجود بر اساس شناسه
    public void update(Borrow borrow) {
        try {
            // اعتبارسنجی داده‌ها مشابه متد insert
            borrow.setId(borrow.getId());
            borrow.setBookId(borrow.getBookId());
            borrow.setMemberId(borrow.getMemberId());
            borrow.setBorrowDate(borrow.getBorrowDate());
            borrow.setReturnDate(borrow.getReturnDate());
            borrow.setActualReturnDate(borrow.getActualReturnDate());
            borrow.setReturned(borrow.isReturned());
            borrow.setRenewed(borrow.isRenewed());

            // تبدیل شیء Borrow به یک رشته برای ذخیره در فایل
            String borrowData = String.join(DELIMITER,
                borrow.getId().toString(),
                borrow.getBookId().toString(),
                borrow.getMemberId().toString(),
                borrow.getBorrowDate(),
                borrow.getReturnDate(),
                borrow.getActualReturnDate() != null ? borrow.getActualReturnDate() : "",
                String.valueOf(borrow.isReturned()),
                String.valueOf(borrow.isRenewed())
            );

            // جستجو برای امانت در فایل
            List<String> lines = fileManager.readAll();
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(DELIMITER);
                if (Long.parseLong(parts[0]) == borrow.getId()) {
                    // به‌روزرسانی امانت در فایل
                    fileManager.updateRow(i, borrowData);
                    found = true;
                    break;
                }
            }

            // اگر امانت پیدا نشد، خطا را نمایش می‌دهد
            if (!found) {
                throw new IllegalArgumentException("امانت با شناسه " + borrow.getId() + " یافت نشد.");
            }
        } catch (IllegalArgumentException e) {
            // در صورت بروز خطا در اعتبارسنجی داده‌ها، خطا را نمایش می‌دهد
            throw new IllegalArgumentException("خطا در اعتبارسنجی داده‌های امانت: " + e.getMessage());
        }
    }

    // متد برای حذف یک امانت از فایل بر اساس شناسه
    public void delete(Long id) {
        List<String> lines = fileManager.readAll();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(DELIMITER);
            if (Long.parseLong(parts[0]) == id) {
                // حذف امانت از فایل
                fileManager.deleteRow(i);
                found = true;
                break;
            }
        }

        // اگر امانت پیدا نشد، خطا را نمایش می‌دهد
        if (!found) {
            throw new IllegalArgumentException("امانت با شناسه " + id + " یافت نشد.");
        }
    }

    // متد برای جستجو یک امانت بر اساس شناسه
    public Borrow selectByPrimaryKey(Long id) {
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (Long.parseLong(parts[0]) == id) {
                // تبدیل رشته به شیء Borrow
                return createBorrowFromParts(parts);
            }
        }
        return null;
    }

    // متد برای جلب تمامی امانت‌ها
    public List<Borrow> selectAll() {
        List<Borrow> borrows = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            // تبدیل رشته‌ها به شیء Borrow
            String[] parts = line.split(DELIMITER);
            borrows.add(createBorrowFromParts(parts));
        }
        return borrows;
    }

    // متد برای جستجو امانت‌ها بر اساس شناسه کتاب
    public List<Borrow> searchByBookId(Long bookId) {
        List<Borrow> borrows = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (Long.parseLong(parts[1]) == bookId) {
                borrows.add(createBorrowFromParts(parts));
            }
        }
        return borrows;
    }

    // متد برای جستجو امانت‌ها بر اساس شناسه عضو
    public List<Borrow> searchByMemberId(Long memberId) {
        List<Borrow> borrows = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (Long.parseLong(parts[2]) == memberId) {
                borrows.add(createBorrowFromParts(parts));
            }
        }
        return borrows;
    }

    // متد برای دریافت امانت‌های فعال (امانت‌هایی که هنوز بازگشت داده نشده‌اند)
    public List<Borrow> getActiveBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (!Boolean.parseBoolean(parts[6])) { // isReturned
                borrows.add(createBorrowFromParts(parts));
            }
        }
        return borrows;
    }

    // متد برای دریافت امانت‌های معوق (امانت‌هایی که تاریخ سررسید آن‌ها گذشته است)
    public List<Borrow> getOverdueBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (!Boolean.parseBoolean(parts[6])) { // isReturned
                // TODO: بررسی تاریخ سررسید
                borrows.add(createBorrowFromParts(parts));
            }
        }
        return borrows;
    }

    // متد برای دریافت تعداد کل امانت‌ها
    public int selectCount() {
        return fileManager.selectCount();
    }

    // متد کمکی برای تبدیل یک آرایه از رشته‌ها به شیء Borrow
    private Borrow createBorrowFromParts(String[] parts) {
        Borrow borrow = new Borrow();
        borrow.setId(Long.parseLong(parts[0]));
        borrow.setBookId(Long.parseLong(parts[1]));
        borrow.setMemberId(Long.parseLong(parts[2]));
        borrow.setBorrowDate(parts[3]);
        borrow.setReturnDate(parts[4]);
        borrow.setActualReturnDate(parts[5].isEmpty() ? null : parts[5]);
        borrow.setReturned(Boolean.parseBoolean(parts[6]));
        borrow.setRenewed(Boolean.parseBoolean(parts[7]));
        return borrow;
    }
}
