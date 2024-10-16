package Entity;

import java.util.Objects;

public class MonAn
{
    private String maMon;
    private String tenMon;
    private String loaiMon;
    private double donGia;
    private String moTa;

    public MonAn() {}

    public MonAn(String maMon, String tenMon, String loaiMon, double donGia, String moTa)
    {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.loaiMon = loaiMon;
        this.donGia = donGia;
        this.moTa = moTa;
    }

    public String getMaMon()
    {
        return maMon;
    }

    public void setMaMon(String maMon)
    {
        this.maMon = maMon;
    }

    public String getTenMon()
    {
        return tenMon;
    }

    public void setTenMon(String tenMon)
    {
        this.tenMon = tenMon;
    }

    public String getLoaiMon()
    {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon)
    {
        this.loaiMon = loaiMon;
    }

    public double getDonGia()
    {
        return donGia;
    }

    public void setDonGia(double donGia)
    {
        this.donGia = donGia;
    }

    public String getMoTa()
    {
        return moTa;
    }

    public void setMoTa(String moTa)
    {
        this.moTa = moTa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonAn monAn = (MonAn) o;
        return Objects.equals(maMon, monAn.maMon);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maMon);
    }
}
