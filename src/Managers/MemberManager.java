package Managers;

import Common.Member;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberManager {
    private static final String MEMBERS_FILE = "members.txt";
    private List<Member> members;

    public MemberManager() {
        members = new ArrayList<>();
        loadMembers();
    }

    public void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("عضو نمی‌تواند null باشد");
        }
        
        // Check for duplicate national code
        for (Member m : members) {
            if (m.getNationalCode().equals(member.getNationalCode())) {
                throw new IllegalArgumentException("کد ملی تکراری است");
            }
        }
        
        members.add(member);
        saveMembers();
    }

    public void removeMember(int memberId) {
        Member member = findMemberById(memberId);
        if (member != null) {
            members.remove(member);
            saveMembers();
        }
    }

    public void updateMember(Member updatedMember) {
        if (updatedMember == null) {
            throw new IllegalArgumentException("عضو نمی‌تواند null باشد");
        }

        Member existingMember = findMemberById(updatedMember.getMemberId());
        if (existingMember != null) {
            members.remove(existingMember);
            members.add(updatedMember);
            saveMembers();
        }
    }

    public Member findMemberById(int memberId) {
        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                return member;
            }
        }
        return null;
    }

    public Member findMemberByNationalCode(String nationalCode) {
        for (Member member : members) {
            if (member.getNationalCode().equals(nationalCode)) {
                return member;
            }
        }
        return null;
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }

    private void loadMembers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MEMBERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Member.SPLITTER);
                if (parts.length == 6) {
                    Member member = new Member(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4],
                        parts[5]
                    );
                    members.add(member);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet, that's okay
        } catch (IOException e) {
            System.err.println("خطا در خواندن فایل اعضا: " + e.getMessage());
        }
    }

    private void saveMembers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member member : members) {
                writer.println(member.toString());
            }
        } catch (IOException e) {
            System.err.println("خطا در ذخیره فایل اعضا: " + e.getMessage());
        }
    }
}
