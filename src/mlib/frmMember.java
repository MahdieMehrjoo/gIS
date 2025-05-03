package mlib;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Common.Member;
import Managers.MemberManager;

public class frmMember extends JFrame 
{
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JTextArea displayArea;

    private JTextField txtMemberId;
    private JTextField txtName;
    private JTextField txtFamilyName;
    private JTextField txtNationalCode;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;

    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnShowAll;

    private MemberManager memberManager;

    public frmMember() 
    {
        super("Member Management System");
        memberManager = new MemberManager();
        initializeUI();
    }

    private void initializeUI()
     {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Input Panel
        inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Member Information"));

        inputPanel.add(new JLabel("Member ID:"));
        txtMemberId = new JTextField();
        inputPanel.add(txtMemberId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Family Name:"));
        txtFamilyName = new JTextField();
        inputPanel.add(txtFamilyName);

        inputPanel.add(new JLabel("National Code:"));
        txtNationalCode = new JTextField();
        inputPanel.add(txtNationalCode);

        inputPanel.add(new JLabel("Phone Number:"));
        txtPhoneNumber = new JTextField();
        inputPanel.add(txtPhoneNumber);

        inputPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        inputPanel.add(txtAddress);

        // Button Panel
        buttonPanel = new JPanel(new FlowLayout());
        btnInsert = new JButton("Insert");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        btnShowAll = new JButton("Show All");

        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnShowAll);

        // Display Area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Main Panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // Add Action Listeners
        btnInsert.addActionListener(e -> insertMember());
        btnUpdate.addActionListener(e -> updateMember());
        btnDelete.addActionListener(e -> deleteMember());
        btnSearch.addActionListener(e -> searchMember());
        btnShowAll.addActionListener(e -> showAllMembers());

        setVisible(true);
    }

    private void insertMember() {
        try {
            Member member = createMemberFromInput();
            memberManager.insert(member);
            displayMessage("Member inserted successfully!");
            clearInputs();
        } catch (Exception e) {
            displayMessage("Error: " + e.getMessage());
        }
    }

    private void updateMember() {
        try {
            Member member = createMemberFromInput();
            memberManager.update(member);
            displayMessage("Member updated successfully!");
            clearInputs();
        } catch (Exception e) {
            displayMessage("Error: " + e.getMessage());
        }
    }

    private void deleteMember() {
        try {
            int memberId = Integer.parseInt(txtMemberId.getText());
            memberManager.delete(memberId);
            displayMessage("Member deleted successfully!");
            clearInputs();
        } catch (Exception e) {
            displayMessage("Error: " + e.getMessage());
        }
    }

    private void searchMember() {
        try {
            int memberId = Integer.parseInt(txtMemberId.getText());
            Member member = memberManager.selectByPrimaryKey(memberId);
            if (member != null) {
                displayMember(member);
            } else {
                displayMessage("Member not found!");
            }
        } catch (Exception e) {
            displayMessage("Error: " + e.getMessage());
        }
    }

    private void showAllMembers() {
        Member[] members = memberManager.selectAll();
        StringBuilder sb = new StringBuilder();
        for (Member member : members) {
            sb.append("ID: ").append(member.getMemberId())
              .append(", Name: ").append(member.getName())
              .append(", Family: ").append(member.getFamilyName())
              .append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private Member createMemberFromInput() {
        Member member = new Member();
        member.setMemberId(Integer.parseInt(txtMemberId.getText()));
        member.setName(txtName.getText());
        member.setFamilyName(txtFamilyName.getText());
        member.setNationalCode(txtNationalCode.getText());
        member.setPhoneNumber(txtPhoneNumber.getText());
        member.setAddress(txtAddress.getText());
        return member;
    }

    private void displayMember(Member member) {
        txtMemberId.setText(String.valueOf(member.getMemberId()));
        txtName.setText(member.getName());
        txtFamilyName.setText(member.getFamilyName());
        txtNationalCode.setText(member.getNationalCode());
        txtPhoneNumber.setText(member.getPhoneNumber());
        txtAddress.setText(member.getAddress());
    }

    private void clearInputs() {
        txtMemberId.setText("");
        txtName.setText("");
        txtFamilyName.setText("");
        txtNationalCode.setText("");
        txtPhoneNumber.setText("");
        txtAddress.setText("");
    }

    private void displayMessage(String message) {
        displayArea.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frmMember());
    }
}