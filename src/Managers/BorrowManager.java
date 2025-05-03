package managers;

import common.Borrow;
import filemanager.txtFileManager;
import java.util.ArrayList;
import java.util.List;

public class BorrowManager {
    private final txtFileManager fileManager;
    private static final String DELIMITER = "&";

      public BorrowManager() {
       this.fileManager = new txtFileManager("borrows.txt");
       fileManager.createFile();
   }

    public void insert(Borrow borrow) {
        try {
            // اعتبارسنجی داده‌ها
            borrow.setId(borrow.getId());
            borrow.setBookId(borrow.getBookId());
            borrow.setMemberId(borrow.getMemberId());
            borrow.setBorrowDate(borrow.getBorrowDate());
            borrow.setReturnDate(borrow.getReturnDate());
            borrow.setActualReturnDate(borrow.getActualReturnDate());
            borrow.setReturned(borrow.isReturned());
            borrow.setRenewed(borrow.isRenewed());

            // تبدیل امانت به رشته
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

            // ذخیره در فایل
            fileManager.appendRow(borrowData);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("خطا در اعتبارسنجی داده‌های امانت: " + e.getMessage());
        }
    }

    public void update(Borrow borrow) {
        try {
            // اعتبارسنجی داده‌ها
            borrow.setId(borrow.getId());
            borrow.setBookId(borrow.getBookId());
            borrow.setMemberId(borrow.getMemberId());
            borrow.setBorrowDate(borrow.getBorrowDate());
            borrow.setReturnDate(borrow.getReturnDate());
            borrow.setActualReturnDate(borrow.getActualReturnDate());
            borrow.setReturned(borrow.isReturned());
            borrow.setRenewed(borrow.isRenewed());

            // تبدیل امانت به رشته
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

            // جستجوی امانت در فایل
            List<String> lines = fileManager.readAll();
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(DELIMITER);
                if (Long.parseLong(parts[0]) == borrow.getId()) {
                    fileManager.updateRow(i, borrowData);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalArgumentException("امانت با شناسه " + borrow.getId() + " یافت نشد.");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("خطا در اعتبارسنجی داده‌های امانت: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        List<String> lines = fileManager.readAll();
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(DELIMITER);
            if (Long.parseLong(parts[0]) == id) {
                fileManager.deleteRow(i);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("امانت با شناسه " + id + " یافت نشد.");
        }
    }

    public Borrow selectByPrimaryKey(Long id) {
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            if (Long.parseLong(parts[0]) == id) {
                return createBorrowFromParts(parts);
            }
        }
        return null;
    }

    public List<Borrow> selectAll() {
        List<Borrow> borrows = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        for (String line : lines) {
            String[] parts = line.split(DELIMITER);
            borrows.add(createBorrowFromParts(parts));
        }
        return borrows;
    }

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

    public int selectCount() {
        return fileManager.selectCount();
    }

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
