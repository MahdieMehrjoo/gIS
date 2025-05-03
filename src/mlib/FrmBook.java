package mlib;

import common.Book;
import managers.BookManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrmBook extends JFrame {
    private final BookManager bookManager;
    
    // فیلدهای ورودی
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtPublisher;
    private JTextField txtIsbn;
    private JTextField txtEdition;
    private JTextField txtPublishDate;
    private JTextField txtPageCount;
    
    // دکمه‌ها
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnClear;
    private JButton btnShowAll;
    
    // ناحیه نمایش نتایج
    private JTextArea txtResult;
    
    public FrmBook() {
        super("مدیریت کتاب‌ها");
        bookManager = new BookManager();
        
        // تنظیمات پنجره
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // ایجاد پنل ورودی
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);
        
        // ایجاد پنل دکمه‌ها
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);
        
        // ایجاد ناحیه نمایش نتایج
        txtResult = new JTextArea();
        txtResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtResult);
        add(scrollPane, BorderLayout.SOUTH);
        
        // نمایش پنجره
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // اضافه کردن فیلدها
        panel.add(new JLabel("شناسه:"));
        txtId = new JTextField();
        panel.add(txtId);
        
        panel.add(new JLabel("عنوان:"));
        txtTitle = new JTextField();
        panel.add(txtTitle);
        
        panel.add(new JLabel("نویسنده:"));
        txtAuthor = new JTextField();
        panel.add(txtAuthor);
        
        panel.add(new JLabel("ناشر:"));
        txtPublisher = new JTextField();
        panel.add(txtPublisher);
        
        panel.add(new JLabel("شابک:"));
        txtIsbn = new JTextField();
        panel.add(txtIsbn);
        
        panel.add(new JLabel("چاپ:"));
        txtEdition = new JTextField();
        panel.add(txtEdition);
        
        panel.add(new JLabel("تاریخ نشر:"));
        txtPublishDate = new JTextField();
        panel.add(txtPublishDate);
        
        panel.add(new JLabel("تعداد صفحات:"));
        txtPageCount = new JTextField();
        panel.add(txtPageCount);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        btnInsert = new JButton("ثبت");
        btnUpdate = new JButton("ویرایش");
        btnDelete = new JButton("حذف");
        btnSearch = new JButton("جستجو");
        btnClear = new JButton("پاک کردن");
        btnShowAll = new JButton("نمایش همه");
        
        panel.add(btnInsert);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnSearch);
        panel.add(btnClear);
        panel.add(btnShowAll);
        
        // اضافه کردن event listener ها
        btnInsert.addActionListener(e -> insertBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnSearch.addActionListener(e -> searchBook());
        btnClear.addActionListener(e -> clearFields());
        btnShowAll.addActionListener(e -> showAllBooks());
        
        return panel;
    }
    
    private void insertBook() {
        try {
            Book book = createBookFromInput();
            bookManager.insert(book);
            JOptionPane.showMessageDialog(this, "کتاب با موفقیت ثبت شد.");
            clearFields();
            showAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateBook() {
        try {
            Book book = createBookFromInput();
            bookManager.update(book);
            JOptionPane.showMessageDialog(this, "کتاب با موفقیت ویرایش شد.");
            clearFields();
            showAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteBook() {
        try {
            Long id = Long.parseLong(txtId.getText());
            bookManager.delete(id);
            JOptionPane.showMessageDialog(this, "کتاب با موفقیت حذف شد.");
            clearFields();
            showAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchBook() {
        String title = txtTitle.getText();
        if (!title.isEmpty()) {
            List<Book> books = bookManager.searchByTitle(title);
            displayBooks(books);
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً عنوان کتاب را وارد کنید.");
        }
    }
    
    private void showAllBooks() {
        List<Book> books = bookManager.selectAll();
        displayBooks(books);
    }
    
    private void displayBooks(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString()).append("\n\n");
        }
        txtResult.setText(sb.toString());
    }
    
    private Book createBookFromInput() {
        Book book = new Book();
        book.setId(Long.parseLong(txtId.getText()));
        book.setTitle(txtTitle.getText());
        book.setAuthor(txtAuthor.getText());
        book.setPublisher(txtPublisher.getText());
        book.setIsbn(txtIsbn.getText());
        book.setEdition(Integer.parseInt(txtEdition.getText()));
        book.setPublishDate(txtPublishDate.getText());
        book.setPageCount(Integer.parseInt(txtPageCount.getText()));
        return book;
    }
    
    private void clearFields() {
        txtId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPublisher.setText("");
        txtIsbn.setText("");
        txtEdition.setText("");
        txtPublishDate.setText("");
        txtPageCount.setText("");
        txtResult.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmBook());
    }
} 