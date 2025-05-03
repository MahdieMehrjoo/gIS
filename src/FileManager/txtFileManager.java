package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class txtFileManager {
    private static final String FILE_NAME = "books.txt";
    private static final String DELIMITER = "&";

    public void createFile() {
        try {
            File file = new File(FILE_NAME);
            if (file.createNewFile()) {
                System.out.println("فایل کتاب‌ها ایجاد شد.");
            } else {
                System.out.println("فایل کتاب‌ها از قبل وجود دارد.");
            }
        } catch (IOException e) {
            System.err.println("خطا در ایجاد فایل: " + e.getMessage());
        }
    }

    public void clear() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.print("");
        } catch (IOException e) {
            System.err.println("خطا در پاک کردن فایل: " + e.getMessage());
        }
    }

    public void appendRow(String row) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true);
             PrintWriter printWriter = new PrintWriter(writer)) {
            printWriter.println(row);
        } catch (IOException e) {
            System.err.println("خطا در نوشتن در فایل: " + e.getMessage());
        }
    }

    public int selectCount() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("خطا در شمارش رکوردها: " + e.getMessage());
        }
        return count;
    }

    public void deleteRow(int row) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                if (currentRow != row) {
                    lines.add(line);
                }
                currentRow++;
            }
        } catch (IOException e) {
            System.err.println("خطا در خواندن فایل: " + e.getMessage());
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("خطا در نوشتن فایل: " + e.getMessage());
        }
    }

    public void updateRow(int row, String newData) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                if (currentRow == row) {
                    lines.add(newData);
                } else {
                    lines.add(line);
                }
                currentRow++;
            }
        } catch (IOException e) {
            System.err.println("خطا در خواندن فایل: " + e.getMessage());
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.err.println("خطا در نوشتن فایل: " + e.getMessage());
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
            System.err.println("خطا در خواندن فایل: " + e.getMessage());
        }
        return lines;
    }

    public String readRow(int row) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int currentRow = 0;
            while ((line = reader.readLine()) != null) {
                if (currentRow == row) {
                    return line;
                }
                currentRow++;
            }
        } catch (IOException e) {
            System.err.println("خطا در خواندن فایل: " + e.getMessage());
        }
        return null;
    }
} 