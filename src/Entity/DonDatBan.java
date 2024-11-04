package Entity;

import java.util.Objects;

public class DonDatBan
{
    private String maDDB;
    private String ngayDatBan;
    private int soGhe;
    private String ghiChu;
    private Ban ban;
    private String khachHang;
    private String sdt;

    public DonDatBan() {}

    public DonDatBan(String maDDB, String ngayDatBan, int soGhe, String ghiChu, Ban ban, String khachHang, String sdt) {
        this.maDDB = maDDB;
        this.ngayDatBan = ngayDatBan;
        this.soGhe = soGhe;
        this.ban = ban;
        this.ghiChu = ghiChu;
        this.khachHang = khachHang;
        this.sdt = sdt;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaDDB()
    {
        return maDDB;
    }

    public void setMaDDB(String maDDB)
    {
        this.maDDB = maDDB;
    }

    public String getNgayDatBan()
    {
        return ngayDatBan;
    }

    public void setNgayDatBan(String ngayDatBan)
    {
        this.ngayDatBan = ngayDatBan;
    }

    public int getSoGhe()
    {
        return soGhe;
    }

    public void setSoGhe(int soGhe)
    {
        this.soGhe = soGhe;
    }

    public String getGhiChu()
    {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu)
    {
        this.ghiChu = ghiChu;
    }

    public Ban getBan()
    {
        return ban;
    }

    public void setBan(Ban maBan)
    {
        this.ban = maBan;
    }

    public String getKhachHang()
    {
        return khachHang;
    }

    public void setKhachHang(String khachHang)
    {
        this.khachHang = khachHang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonDatBan donDatBan = (DonDatBan) o;
        return Objects.equals(maDDB, donDatBan.maDDB);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maDDB);
    }
}
