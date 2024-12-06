package Entity;

public class ChiTietHoaDon
{
    private String maHD;
    private String monAn;
    private int soLuong;
    private double donGia;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(String maHD, String monAn, int soLuong, double donGia) {
        this.maHD = maHD;
        this.monAn = monAn;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMonAn() {
        return monAn;
    }

    public void setMonAn(String monAn) {
        this.monAn = monAn;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
}
