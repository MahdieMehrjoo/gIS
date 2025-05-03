package managers;

import common.Book;
import filemanager.txtFileManager;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private final txtFileManager fileManager;

       public BookManager() {
       this.fileManager = new txtFileManager("books.txt");
       fileManager.createFile();
   }

    public void insert(Book book) {
        try {
            // اعتبارسنجی با استفاده از setter ها
            book.setId(book.getId());
            book.setTitle(book.getTitle());
            book.setAuthor(book.getAuthor());
            book.setPublisher(book.getPublisher());
            book.setIsbn(book.getIsbn());
            book.setEdition(book.getEdition());
            book.setPublishDate(book.getPublishDate());
            book.setPageCount(book.getPageCount());

            String data = String.format("%d&%s&%s&%s&%s&%d&%s&%d",
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublisher(),
                    book.getIsbn(),
                    book.getEdition(),
                    book.getPublishDate(),
                    book.getPageCount());
            fileManager.appendRow(data);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("اطلاعات کتاب نامعتبر است: " + e.getMessage());
        }
    }

    public void update(Book book) {
        try {
            // اعتبارسنجی با استفاده از setter ها
            book.setId(book.getId());
            book.setTitle(book.getTitle());
            book.setAuthor(book.getAuthor());
            book.setPublisher(book.getPublisher());
            book.setIsbn(book.getIsbn());
            book.setEdition(book.getEdition());
            book.setPublishDate(book.getPublishDate());
            book.setPageCount(book.getPageCount());

            List<Book> books = selectAll();
            for (int i = 0; i < books.size(); i++) {
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
                    fileManager.updateRow(i, data);
                    return;
                }
            }
            throw new IllegalArgumentException("کتاب مورد نظر یافت نشد");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("اطلاعات کتاب نامعتبر است: " + e.getMessage());
        }
    }

    public void delete(Long id) {
        List<Book> books = selectAll();
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(id)) {
                fileManager.deleteRow(i);
                return;
            }
        }
        throw new IllegalArgumentException("کتاب مورد نظر یافت نشد");
    }

    public Book selectByPrimaryKey(Long id) {
        List<Book> books = selectAll();
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> selectAll() {
        List<Book> books = new ArrayList<>();
        List<String> lines = fileManager.readAll();
        
        for (String line : lines) {
            String[] parts = line.split("&");
            if (parts.length == 8) {
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
        return books;
    }

    public List<Book> searchByTitle(String title) {
        List<Book> books = selectAll();
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchByAuthor(String author) {
        List<Book> books = selectAll();
        List<Book> result = new ArrayList<>();
        
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public int selectCount() {
        return fileManager.selectCount();
    }
} 