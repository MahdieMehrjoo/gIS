package mlib;

import common.Borrow;
import managers.BorrowManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class frmBorrow extends JFrame {
    private final BorrowManager borrowManager;
    
    // فیلدهای ورودی
    private JTextField txtId;
    private JTextField txtBookId;
    private JTextField txtMemberId;
    private JTextField txtBorrowDate;
    private JTextField txtReturnDate;
    private JTextField txtActualReturnDate;
    private JCheckBox chkReturned;
    private JCheckBox chkRenewed;
    
    // دکمه‌ها
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnClear;
    private JButton btnShowAll;
    private JButton btnShowActive;
    private JButton btnShowOverdue;
    
    // ناحیه نمایش نتایج
    private JTextArea txtResult;
    
    public BorrowForm() {
        super("مدیریت امانت‌ها");
        borrowManager = new BorrowManager();
        
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
        
        panel.add(new JLabel("شناسه کتاب:"));
        txtBookId = new JTextField();
        panel.add(txtBookId);
        
        panel.add(new JLabel("شناسه عضو:"));
        txtMemberId = new JTextField();
        panel.add(txtMemberId);
        
        panel.add(new JLabel("تاریخ امانت:"));
        txtBorrowDate = new JTextField();
        panel.add(txtBorrowDate);
        
        panel.add(new JLabel("موعد بازگشت:"));
        txtReturnDate = new JTextField();
        panel.add(txtReturnDate);
        
        panel.add(new JLabel("تاریخ بازگشت واقعی:"));
        txtActualReturnDate = new JTextField();
        panel.add(txtActualReturnDate);
        
        panel.add(new JLabel("وضعیت بازگشت:"));
        chkReturned = new JCheckBox();
        panel.add(chkReturned);
        
        panel.add(new JLabel("وضعیت تمدید:"));
        chkRenewed = new JCheckBox();
        panel.add(chkRenewed);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        btnInsert = new JButton("ثبت امانت");
        btnUpdate = new JButton("ویرایش");
        btnDelete = new JButton("حذف");
        btnSearch = new JButton("جستجو");
        btnClear = new JButton("پاک کردن");
        btnShowAll = new JButton("نمایش همه");
        btnShowActive = new JButton("امانت‌های فعال");
        btnShowOverdue = new JButton("امانت‌های معوق");
        
        panel.add(btnInsert);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnSearch);
        panel.add(btnClear);
        panel.add(btnShowAll);
        panel.add(btnShowActive);
        panel.add(btnShowOverdue);
        
        // اضافه کردن event listener ها
        btnInsert.addActionListener(e -> insertBorrow());
        btnUpdate.addActionListener(e -> updateBorrow());
        btnDelete.addActionListener(e -> deleteBorrow());
        btnSearch.addActionListener(e -> searchBorrow());
        btnClear.addActionListener(e -> clearFields());

مهدیه, [13/02/1404 01:35 ق.ظ]
btnShowAll.addActionListener(e -> showAllBorrows());
        btnShowActive.addActionListener(e -> showActiveBorrows());
        btnShowOverdue.addActionListener(e -> showOverdueBorrows());
        
        return panel;
    }
    
    private void insertBorrow() {
        try {
            Borrow borrow = createBorrowFromInput();
            borrowManager.insert(borrow);
            JOptionPane.showMessageDialog(this, "امانت با موفقیت ثبت شد.");
            clearFields();
            showAllBorrows();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateBorrow() {
        try {
            Borrow borrow = createBorrowFromInput();
            borrowManager.update(borrow);
            JOptionPane.showMessageDialog(this, "امانت با موفقیت ویرایش شد.");
            clearFields();
            showAllBorrows();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteBorrow() {
        try {
            Long id = Long.parseLong(txtId.getText());
            borrowManager.delete(id);
            JOptionPane.showMessageDialog(this, "امانت با موفقیت حذف شد.");
            clearFields();
            showAllBorrows();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "خطا: " + ex.getMessage(), "خطا", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchBorrow() {
        String bookId = txtBookId.getText();
        if (!bookId.isEmpty()) {
            List<Borrow> borrows = borrowManager.searchByBookId(Long.parseLong(bookId));
            displayBorrows(borrows);
        } else {
            JOptionPane.showMessageDialog(this, "لطفاً شناسه کتاب را وارد کنید.");
        }
    }
    
    private void showAllBorrows() {
        List<Borrow> borrows = borrowManager.selectAll();
        displayBorrows(borrows);
    }
    
    private void showActiveBorrows() {
        List<Borrow> borrows = borrowManager.getActiveBorrows();
        displayBorrows(borrows);
    }
    
    private void showOverdueBorrows() {
        List<Borrow> borrows = borrowManager.getOverdueBorrows();
        displayBorrows(borrows);
    }
    
    private void displayBorrows(List<Borrow> borrows) {
        StringBuilder sb = new StringBuilder();
        for (Borrow borrow : borrows) {
            sb.append(borrow.toString()).append("\n\n");
        }
        txtResult.setText(sb.toString());
    }
    
    private Borrow createBorrowFromInput() {
        Borrow borrow = new Borrow();
        borrow.setId(Long.parseLong(txtId.getText()));
        borrow.setBookId(Long.parseLong(txtBookId.getText()));
        borrow.setMemberId(Long.parseLong(txtMemberId.getText()));
        borrow.setBorrowDate(txtBorrowDate.getText());
        borrow.setReturnDate(txtReturnDate.getText());
        borrow.setActualReturnDate(txtActualReturnDate.getText().isEmpty() ? null : txtActualReturnDate.getText());
        borrow.setReturned(chkReturned.isSelected());
        borrow.setRenewed(chkRenewed.isSelected());
        return borrow;
    }
    
    private void clearFields() {
        txtId.setText("");
        txtBookId.setText("");
        txtMemberId.setText("");
        txtBorrowDate.setText("");
        txtReturnDate.setText("");
        txtActualReturnDate.setText("");
        chkReturned.setSelected(false);
        chkRenewed.setSelected(false);
        txtResult.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BorrowForm());
    }
}
