package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class txtFileManager {
    // این متغیر نام فایل رو ذخیره می‌کنه
    protected final String fileName;

    // سازنده که نام فایل رو دریافت می‌کنه
    public txtFileManager(String fileName) {
        this.fileName = fileName;
    }

    // این متد برای ایجاد فایل جدید استفاده میشه
    // اگه فایل از قبل وجود نداشته باشه، میسازیمش
    public void createFile() {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();  // ایجاد فایل
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در ایجاد فایل " + fileName + ": " + e.getMessage());
        }
    }

    // این متد برای پاک کردن محتوای فایل استفاده میشه
    // وقتی فایل رو پاک می‌کنیم، تمام اطلاعات داخلش رو از بین می‌بریم
    public void clear() {
        try {
            new FileWriter(fileName, false).close();  // باز کردن فایل و بستن اون بدون نوشتن چیزی
        } catch (IOException e) {
            throw new RuntimeException("خطا در پاک کردن فایل " + fileName + ": " + e.getMessage());
        }
    }

    // این متد برای اضافه کردن یک خط جدید به فایل استفاده میشه
    // وقتی می‌خواهیم رکورد جدیدی رو در فایل ذخیره کنیم، از این متد استفاده می‌کنیم
    public void appendRow(String row) {
        try (FileWriter writer = new FileWriter(fileName, true); // باز کردن فایل به صورت اضافه‌کردن (append)
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(row);  // نوشتن خط جدید
            bufferedWriter.newLine();  // اضافه کردن خط جدید
        } catch (IOException e) {
            throw new RuntimeException("خطا در افزودن رکورد به فایل " + fileName + ": " + e.getMessage());
        }
    }

    // این متد تعداد رکوردهای فایل رو شمارش می‌کنه
    // مثلاً می‌خواهیم بدونیم چقدر داده توی فایل ذخیره شده
    public int selectCount() {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {  // هر خط رو می‌خونه و به تعدادش اضافه می‌کنه
                count++;
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در شمارش رکوردهای فایل " + fileName + ": " + e.getMessage());
        }
        return count;  // تعداد رکوردهای فایل رو برمی‌گردونه
    }

    // این متد برای حذف یک رکورد از فایل استفاده میشه
    // اگه رکوردی رو مشخص کردیم (به عنوان مثال، یک خط از فایل) از فایل حذف میشه
    public void deleteRow(int row) {
        List<String> lines = readAll();  // اول تمام خطوط رو می‌خونیم
        if (row >= 0 && row < lines.size()) {  // چک می‌کنیم که رکورد مورد نظر موجود باشه
            lines.remove(row);  // رکورد مورد نظر رو حذف می‌کنیم
            try (FileWriter writer = new FileWriter(fileName, false);  // باز کردن فایل برای نوشتن مجدد
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (String line : lines) {  // هر خط باقی‌مونده رو دوباره می‌نویسیم
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("خطا در حذف رکورد از فایل " + fileName + ": " + e.getMessage());
            }
        }
    }

    // این متد برای به‌روزرسانی یک رکورد از فایل استفاده میشه
    // اگه رکوردی رو بخوایم تغییر بدیم، با این متد می‌تونیم اون رو به‌روزرسانی کنیم
    public void updateRow(int row, String newData) {
        List<String> lines = readAll();  // اول تمام خطوط رو می‌خونیم
        if (row >= 0 && row < lines.size()) {  // چک می‌کنیم که رکورد مورد نظر وجود داشته باشه
            lines.set(row, newData);  // داده جدید رو به جای رکورد قبلی قرار می‌دیم
            try (FileWriter writer = new FileWriter(fileName, false);  // باز کردن فایل برای نوشتن مجدد
                 BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                for (String line : lines) {  // هر خط باقی‌مونده رو دوباره می‌نویسیم
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("خطا در بروزرسانی رکورد در فایل " + fileName + ": " + e.getMessage());
            }
        }
    }

    // این متد تمام خطوط فایل رو می‌خونه و توی یک لیست برمی‌گردونه
    // مثلاً وقتی می‌خواهیم تمام محتویات فایل رو بررسی کنیم، از این متد استفاده می‌کنیم
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {  // هر خط از فایل رو می‌خونه و به لیست اضافه می‌کنه
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("خطا در خواندن فایل " + fileName + ": " + e.getMessage());
        }
        return lines;  // لیست تمام خطوط فایل رو برمی‌گردونه
    }

    // این متد یک رکورد خاص رو از فایل می‌خونه و برمی‌گردونه
    // اگه می‌خواهیم فقط یک خط خاص از فایل رو بخونیم، از این متد استفاده می‌کنیم
    public String readRow(int row) {
        List<String> lines = readAll();  // اول تمام خطوط فایل رو می‌خونیم
        if (row >= 0 && row < lines.size()) {  // چک می‌کنیم که رکورد موجود باشه
            return lines.get(row);  // رکورد مورد نظر رو برمی‌گردونیم
        }
        return null;  // اگه رکورد موجود نباشه، هیچ چیزی برنمی‌گردونیم
    }
}
