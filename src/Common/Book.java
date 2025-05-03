package common;

public class Book {
    private Long id; // کلید اصلی
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int edition;
    private String publishDate;
    private int pageCount;

    // سازنده پیش‌فرض
    public Book() {
    }

    // سازنده با پارامتر
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

    public String getTitle() {
        return title;
    }

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

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("نام نویسنده نمی‌تواند خالی باشد");
        }
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher == null || publisher.trim().isEmpty()) {
            throw new IllegalArgumentException("نام ناشر نمی‌تواند خالی باشد");
        }
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

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

    public void setEdition(int edition) {
        if (edition <= 0) {
            throw new IllegalArgumentException("تیراژ باید عدد مثبت باشد");
        }
        this.edition = edition;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        if (publishDate == null || publishDate.trim().isEmpty()) {
            throw new IllegalArgumentException("تاریخ نشر نمی‌تواند خالی باشد");
        }
        this.publishDate = publishDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if (pageCount <= 0) {
            throw new IllegalArgumentException("تعداد صفحات باید عدد مثبت باشد");
        }
        this.pageCount = pageCount;
    }

    @Override
    public String toString() {
        return String.format("کتاب: %s%nنویسنده: %s%nناشر: %s%nشابک: %s%nچاپ: %d%nتاریخ نشر: %s%nتعداد صفحات: %d",
                title, author, publisher, isbn, edition, publishDate, pageCount);
    }
} 