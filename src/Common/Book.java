package common;

public class Book {
    // اینجا ویژگی‌های کتاب رو تعریف کردیم. این‌ها همون اطلاعاتی هستن که برای هر کتاب داریم:
    private Long id; // شناسه یکتای کتاب (مثلاً یه عدد که کتاب رو از بقیه کتاب‌ها متمایز می‌کنه)
    private String title; // عنوان کتاب
    private String author; // نام نویسنده کتاب
    private String publisher; // نام ناشر کتاب
    private String isbn; // شابک (کد منحصر به فرد کتاب)
    private int edition; // تیراژ (تعداد چاپ کتاب)
    private String publishDate; // تاریخ چاپ کتاب
    private int pageCount; // تعداد صفحات کتاب

    // سازنده پیش‌فرض که وقتی می‌خوایم یه کتاب بدون هیچ اطلاعاتی بسازیم، ازش استفاده می‌کنیم.
    public Book() {
    }

    // سازنده‌ای که می‌تونه تمام ویژگی‌ها رو یکجا تنظیم کنه. یعنی وقتی می‌خوایم اطلاعات کامل کتاب رو بدیم.
    public Book(Long id, String title, String author, String publisher, String isbn, 
                int edition, String publishDate, int pageCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.edition = edition;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
    }

    // متدهای Getter و Setter برای هر ویژگی
    // این متدها به ما کمک می‌کنن که بتونیم به راحتی مقادیر ویژگی‌ها رو بخونیم یا تغییر بدیم

    public Long getId() {
        return id;
    }

    // وقتی می‌خواهیم شناسه رو تغییر بدیم، باید مطمئن بشیم که نباید مقدار خالی بشه.
    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("شناسه نمی‌تواند خالی باشد");
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    // برای تنظیم عنوان کتاب، باید چک کنیم که خالی نباشه و طولش مناسب باشه.
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("عنوان کتاب نمی‌تواند خالی باشد");
        }
        if (title.length() < 2 || title.length() > 100) {
            throw new IllegalArgumentException("عنوان کتاب باید بین 2 تا 100 کاراکتر باشد");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    // همینطور باید مطمئن بشیم که اسم نویسنده خالی نباشه.
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("نام نویسنده نمی‌تواند خالی باشد");
        }
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    // ناشر هم باید خالی نباشه.
    public void setPublisher(String publisher) {
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("نام ناشر نمی‌تواند خالی باشد");
        }
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    // شابک هم باید معتبر باشه، یعنی یا 10 رقمی باشه یا 13 رقمی.
    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("شابک نمی‌تواند خالی باشد");
        }
        if (isbn.length() != 10 && isbn.length() != 13) {
            throw new IllegalArgumentException("شابک باید 10 یا 13 رقمی باشد");
        }
        this.isbn = isbn;
    }

    public int getEdition() {
        return edition;
    }

    // تیراژ هم باید یک عدد مثبت باشه.
    public void setEdition(int edition) {
        if (edition <= 0) {
            throw new IllegalArgumentException("تیراژ باید عدد مثبت باشد");
        }
        this.edition = edition;
    }

    public String getPublishDate() {
        return publishDate;
    }

    // تاریخ چاپ کتاب هم باید مشخص باشه.
    public void setPublishDate(String publishDate) {
        if (publishDate == null || publishDate.trim().isEmpty()) {
            throw new IllegalArgumentException("تاریخ نشر نمی‌تواند خالی باشد");
        }
        this.publishDate = publishDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    // تعداد صفحات هم باید یک عدد مثبت باشه.
    public void setPageCount(int pageCount) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("تعداد صفحات باید عدد مثبت باشد");
        }
        this.pageCount = pageCount;
    }

    // این متد میاد و اطلاعات کتاب رو به صورت یک رشته چاپ می‌کنه. مثلاً وقتی می‌خواهید کتاب رو به کاربر نشون بدید، ازش استفاده می‌کنید.
    @Override
    public String toString() {
        return String.format("کتاب: %s%nنویسنده: %s%nناشر: %s%nشابک: %s%nچاپ: %d%nتاریخ نشر: %s%nتعداد صفحات: %d",
                title, author, publisher, isbn, edition, publishDate, pageCount);
    }
}
