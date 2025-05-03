package managers;

import common.Book;
import filemanager.txtFileManager;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    // اینجا داریم از کلاس txtFileManager استفاده می‌کنیم تا فایل رو مدیریت کنیم
    private final txtFileManager fileManager;

    // سازنده کلاس که فایل کتاب‌ها رو می‌سازه
    // با هر بار ساخت یک شیء از این کلاس، فایل books.txt ساخته میشه (اگر قبلاً وجود نداشته باشه)
    public BookManager() {
        this.fileManager = new txtFileManager("books.txt");
        fileManager.createFile();  // ایجاد فایل
    }

    // این متد برای اضافه کردن یک کتاب جدید به فایل استفاده میشه
    // اول تمام داده‌های کتاب رو با استفاده از setter ها اعتبارسنجی می‌کنیم
    // بعدش داده‌ها رو به فایل اضافه می‌کنیم
    public void insert(Book book) {
        try {
            // اعتبارسنجی داده‌ها با استفاده از setter ها
            book.setId(book.getId());
            book.setTitle(book.getTitle());
            book.setAuthor(book.getAuthor());
            book.setPublisher(book.getPublisher());
            book.setIsbn(book.getIsbn());
            book.setEdition(book.getEdition());
            book.setPublishDate(book.getPublishDate());
            book.setPageCount(book.getPageCount());

            // ایجاد یک رشته از داده‌ها برای ذخیره در فایل
            String data = String.format("%d&%s&%s&%s&%s&%d&%s&%d",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getIsbn(),
                    book.getEdition(),
                    book.getPublishDate(),
                    book.getPageCount());
            fileManager.appendRow(data);  // داده‌ها رو به فایل اضافه می‌کنیم
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("اطلاعات کتاب نامعتبر است: " + e.getMessage());
        }
    }

    // این متد برای به‌روزرسانی کتاب در فایل استفاده میشه
    // اول کتاب رو اعتبارسنجی می‌کنیم و بعدش رکورد مورد نظر رو پیدا می‌کنیم
    // اگه کتاب پیدا شد، اطلاعات جدیدش رو جایگزین می‌کنیم
    public void update(Book book) {
        try {
            // اعتبارسنجی داده‌ها
            book.setId(book.getId());
            book.setTitle(book.getTitle());
            book.setAuthor(book.getAuthor());
            book.setPublisher(book.getPublisher());
            book.setIsbn(book.getIsbn());
            book.setEdition(book.getEdition());
            book.setPublishDate(book.getPublishDate());
            book.setPageCount(book.getPageCount());

            // پیدا کردن کتاب‌ها از فایل
            List<Book> books = selectAll();
            for (int i = 0; i < books.size(); i++) {
                // اگه کتاب با ID مشابه پیدا شد، داده‌ها رو به‌روزرسانی می‌کنیم
                if (books.get(i).getId().equals(book.getId())) {
                    String data = String.format("%d&%s&%s&%s&%s&%d&%s&%d",
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            book.getPublisher(),
                            book.getIsbn(),
                            book.getEdition(),
                            book.getPublishDate(),
                            book.getPageCount());
                    fileManager.updateRow(i, data);  // بروزرسانی داده در فایل
                    return;
                }
            }
            // اگه کتاب پیدا نشد
            throw new IllegalArgumentException("کتاب مورد نظر یافت نشد");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("اطلاعات کتاب نامعتبر است: " + e.getMessage());
        }
    }

    // این متد برای حذف کتاب با ID مشخص از فایل استفاده میشه
    public void delete(Long id) {
        List<Book> books = selectAll();
        for (int i = 0; i < books.size(); i++) {
            // اگه کتاب با ID مورد نظر پیدا شد، حذف می‌کنیم
            if (books.get(i).getId().equals(id)) {
                fileManager.deleteRow(i);  // حذف رکورد از فایل
                return;
            }
        }
        throw new IllegalArgumentException("کتاب مورد نظر یافت نشد");
    }

    // این متد کتاب رو بر اساس ID اولیه (Primary Key) پیدا می‌کنه
    public Book selectByPrimaryKey(Long id) {
        List<Book> books = selectAll();
        for (Book book : books) {
            // اگه کتاب با ID مورد نظر پیدا شد، برمی‌گردونیم
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;  // اگه پیدا نشد، null برمی‌گردونیم
    }

    // این متد تمام کتاب‌ها رو از فایل می‌خونه و توی یک لیست برمی‌گردونه
    public List<Book> selectAll() {
        List<Book> books = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        
        for (String line : lines) {
            String[] parts = line.split("&");  // جدا کردن اطلاعات هر کتاب با علامت "&"
            if (parts.length == 8) {
                // ساخت شیء Book از داده‌های خوانده شده
                Book book = new Book(
                    Long.parseLong(parts[0]),
                    parts[1],
                    parts[2],
                    parts[3],
                    parts[4],
                    Integer.parseInt(parts[5]),
                    parts[6],
                    Integer.parseInt(parts[7])
                );
                books.add(book);
            }
        }
        return books;  // برگردوندن لیست کتاب‌ها
    }

    // این متد برای جستجو کتاب‌ها بر اساس عنوان (title) استفاده میشه
    public List<Book> searchByTitle(String title) {
        List<Book> books = selectAll();
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            // جستجو در بین کتاب‌ها بر اساس عنوان (حساس به حروف بزرگ و کوچک نیست)
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // این متد برای جستجو کتاب‌ها بر اساس نویسنده (author) استفاده میشه
    public List<Book> searchByAuthor(String author) {
        List<Book> books = selectAll();
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            // جستجو در بین کتاب‌ها بر اساس نویسنده (حساس به حروف بزرگ و کوچک نیست)
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // این متد برای شمارش تعداد کتاب‌ها در فایل استفاده میشه
    public int selectCount() {
        return fileManager.selectCount();  // برگردوندن تعداد رکوردها از فایل
    }
}
