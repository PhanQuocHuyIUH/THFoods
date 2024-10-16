package Entity;

import java.util.Objects;

public class DonDatBan
{
    private String maDDB;
    private String ngayDatBan;
    private int soGhe;
    private String ghiChu;
    private Ban maBan;

    public DonDatBan() {}

    public DonDatBan(String maDDB, String ngayDatBan, int soGhe, String ghiChu, Ban maBan)
    {
        this.maDDB = maDDB;
        this.ngayDatBan = ngayDatBan;
        this.soGhe = soGhe;
        this.ghiChu = ghiChu;
        this.maBan = maBan;
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

    public Ban getMaBan()
    {
        return maBan;
    }

    public void setMaBan(Ban maBan)
    {
        this.maBan = maBan;
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
