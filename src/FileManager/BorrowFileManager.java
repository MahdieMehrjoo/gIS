package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowFileManager {
    private static final String FILE_NAME = "borrows.txt";

    public void createFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در ایجاد فایل امانت‌ها: " + e.getMessage());
        }
    }

    public void clear() {
        try {
            new FileWriter(FILE_NAME, false).close();
        } catch (IOException e) {
            throw new RuntimeException("خطا در پاک کردن فایل امانت‌ها: " + e.getMessage());
        }
    }

    public void appendRow(String row) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(row);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException("خطا در افزودن رکورد به فایل امانت‌ها: " + e.getMessage());
        }
    }

    public int selectCount() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در شمارش رکوردهای فایل امانت‌ها: " + e.getMessage());
        }
        return count;
    }

    public void deleteRow(int row) {
        List<String> lines = readAll();
        if (row >= 0 && row < lines.size()) {
            lines.remove(row);
            try (FileWriter writer = new FileWriter(FILE_NAME, false);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("خطا در حذف رکورد از فایل امانت‌ها: " + e.getMessage());
            }
        }
    }

    public void updateRow(int row, String newData) {
        List<String> lines = readAll();
        if (row >= 0 && row < lines.size()) {
            lines.set(row, newData);
            try (FileWriter writer = new FileWriter(FILE_NAME, false);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("خطا در بروزرسانی رکورد در فایل امانت‌ها: " + e.getMessage());
            }
        }
    }

    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در خواندن فایل امانت‌ها: " + e.getMessage());
        }
        return lines;
    }

    public String readRow(int row) {
        List<String> lines = readAll();
        if (row >= 0 && row < lines.size()) {
            return lines.get(row);
        }
        return null;
    }
} 