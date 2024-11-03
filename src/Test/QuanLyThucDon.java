package Test;

public class QuanLyThucDon  {
    public static void main(String[] args) {
        // Tao 1 String lay a ngay gio hien tai
        String a = java.time.LocalDateTime.now().toString();
        // format lai chi giu so trong chuoi nay chi giu 14 so
        a = a.replaceAll("[^0-9]", "");
        a = a.substring(0, 14);
        System.out.println(a);
    }
}

