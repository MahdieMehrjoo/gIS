package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class txtFileManager {
    protected final String fileName;

    public txtFileManager(String fileName) {
        this.fileName = fileName;
    }

    public void createFile() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در ایجاد فایل " + fileName + ": " + e.getMessage());
        }
    }

    public void clear() {
        try {
            new FileWriter(fileName, false).close();
        } catch (IOException e) {
            throw new RuntimeException("خطا در پاک کردن فایل " + fileName + ": " + e.getMessage());
        }
    }

    public void appendRow(String row) {
        try (FileWriter writer = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(row);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException("خطا در افزودن رکورد به فایل " + fileName + ": " + e.getMessage());
        }
    }

    public int selectCount() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در شمارش رکوردهای فایل " + fileName + ": " + e.getMessage());
        }
        return count;
    }

    public void deleteRow(int row) {
        List<String> lines = readAll();
        if (row >= 0 && row < lines.size()) {
            lines.remove(row);
            try (FileWriter writer = new FileWriter(fileName, false);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("خطا در حذف رکورد از فایل " + fileName + ": " + e.getMessage());
            }
        }
    }

    public void updateRow(int row, String newData) {
        List<String> lines = readAll();
        if (row >= 0 && row < lines.size()) {
            lines.set(row, newData);
            try (FileWriter writer = new FileWriter(fileName, false);
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (String line : lines) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("خطا در بروزرسانی رکورد در فایل " + fileName + ": " + e.getMessage());
            }
        }
    }

    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در خواندن فایل " + fileName + ": " + e.getMessage());
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