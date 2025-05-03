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
    
    // فیلدهای ورودی برای اطلاعات کتاب
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtPublisher;
    private JTextField txtIsbn;
    private JTextField txtEdition;
    private JTextField txtPublishDate;
    private JTextField txtPageCount;
    
    // دکمه‌ها برای انجام عملیات مختلف
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnClear;
    private JButton btnShowAll;
    
    // ناحیه نمایش نتایج جستجو و کتاب‌ها
    private JTextArea txtResult;
    
    // سازنده کلاس که پنجره‌ی اصلی رو راه‌اندازی می‌کنه
    public FrmBook() {
        super("مدیریت کتاب‌ها");
        bookManager = new BookManager(); // ساخت شی BookManager برای انجام عملیات‌ها روی کتاب‌ها
        
        // تنظیمات پنجره
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // در صورت بستن پنجره، برنامه بسته بشه
        setLayout(new BorderLayout()); // استفاده از BorderLayout برای چیدمان پنل‌ها
        
        // اضافه کردن پنل ورودی که شامل فیلدهای اطلاعات کتاب هست
        JPanel inputPanel = createInputPanel();
        add(inputPanel, BorderLayout.NORTH);
        
        // اضافه کردن پنل دکمه‌ها
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);
        
        // اضافه کردن ناحیه نمایش نتایج که برای نمایش کتاب‌ها یا پیام‌ها استفاده میشه
        txtResult = new JTextArea();
        txtResult.setEditable(false); // غیرقابل ویرایش کردن ناحیه نتایج
        JScrollPane scrollPane = new JScrollPane(txtResult); // استفاده از اسکرول برای نمایش راحت‌تر
        add(scrollPane, BorderLayout.SOUTH);
        
        // نمایش پنجره در مرکز صفحه
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // متد برای ایجاد پنل ورودی که فیلدهای اطلاعات کتاب رو می‌سازد
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5)); // استفاده از GridLayout برای چیدمان فیلدها
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // تنظیم فاصله‌های داخلی پنل
        
        // اضافه کردن فیلدهای ورودی برای هر خصوصیت کتاب
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
    
    // متد برای ایجاد پنل دکمه‌ها
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout()); // استفاده از FlowLayout برای چیدمان دکمه‌ها
        
        // ساخت دکمه‌های مختلف برای انجام عملیات‌ها
        btnInsert = new JButton("ثبت");
        btnUpdate = new JButton("ویرایش");
        btnDelete = new JButton("حذف");
        btnSearch = new JButton("جستجو");
        btnClear = new JButton("پاک کردن");
        btnShowAll = new JButton("نمایش همه");
        
        // اضافه کردن دکمه‌ها به پنل
        panel.add(btnInsert);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnSearch);
        panel.add(btnClear);
        panel.add(btnShowAll);
        
        // افزودن event listener ها برای هر دکمه
        btnInsert.addActionListener(e -> insertBook()); // برای ثبت کتاب
        btnUpdate.addActionListener(e -> updateBook()); // برای ویرایش کتاب
        btnDelete.addActionListener(e -> deleteBook()); // برای حذف کتاب
        btnSearch.addActionListener(e -> searchBook()); // برای جستجو کتاب
        btnClear.addActionListener(e -> clearFields()); // برای پاک کردن فیلدها
        btnShowAll.addActionListener(e -> showAllBooks()); // برای نمایش تمام کتاب‌ها
        
        return panel;
    }
    
    // متد برای ثبت کتاب
    private void insertBook() {
        try {
            Book book = createBookFromInput(); // ساخت شیء کتاب از فیلدهای ورودی
            bookManager.insert(book); // ثبت کتاب در مدیریت کتاب‌ها
            JOptionPane.showMessageDialog(this, "کتاب با موفقیت ثبت شد.");
            clearFields(); // پاک کردن فیلدها پس از ثبت
            showAllBooks(); // نمایش لیست تمام کتاب‌ها
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // متد برای ویرایش کتاب
    private void updateBook() {
        try {
            Book book = createBookFromInput(); // ساخت شیء کتاب از فیلدهای ورودی
            bookManager.update(book); // بروزرسانی کتاب در مدیریت کتاب‌ها
            JOptionPane.showMessageDialog(this, "کتاب با موفقیت ویرایش شد.");
            clearFields();
            showAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // متد برای حذف کتاب
    private void deleteBook() {
        try {
            Long id = Long.parseLong(txtId.getText()); // خواندن شناسه کتاب از فیلد ورودی
            bookManager.delete(id); // حذف کتاب با شناسه مشخص
            JOptionPane.showMessageDialog(this, "کتاب با موفقیت حذف شد.");
            clearFields();
            showAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // متد برای جستجو کتاب بر اساس عنوان
    private void searchBook() {
        String title = txtTitle.getText(); // گرفتن عنوان از فیلد ورودی
        if (!title.isEmpty()) {
            List<Book> books = bookManager.searchByTitle(title); // جستجو کتاب‌ها بر اساس عنوان
            displayBooks(books); // نمایش نتایج جستجو
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً عنوان کتاب را وارد کنید.");
        }
    }
    
    // متد برای نمایش تمام کتاب‌ها
    private void showAllBooks() {
        List<Book> books = bookManager.selectAll(); // گرفتن تمام کتاب‌ها
        displayBooks(books); // نمایش تمام کتاب‌ها
    }
    
    // متد برای نمایش کتاب‌ها در JTextArea
    private void displayBooks(List<Book> books) {
        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(book.toString()).append("\n\n");
        }
        txtResult.setText(sb.toString()); // نمایش نتایج در جعبه متن
    }
    
    // متد برای ساخت شیء کتاب از فیلدهای ورودی
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
    
    // متد برای پاک کردن تمامی فیلدها
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
    
    // متد main برای راه‌اندازی پنجره
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmBook());
    }
}
