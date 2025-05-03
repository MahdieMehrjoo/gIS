package common;

public class Book {
    private String id;
    private String title;
    private String author;
    private String publicationDate;
    private String isbn;
    private int copies;
    private String publisher;

    public Book(String id, String title, String author, String publicationDate, String isbn, int copies, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.copies = copies;
        this.publisher = publisher;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublicationDate() { return publicationDate; }
    public String getIsbn() { return isbn; }
    public int getCopies() { return copies; }
    public String getPublisher() { return publisher; }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + publicationDate + "," + isbn + "," + copies + "," + publisher;
    }
}
