package Entity;

import java.time.LocalDateTime;

public class PhieuDatMon
{
    private String maPDB;
    private LocalDateTime ngayDat;
    private String ghiChu;
    private Ban maBan;
    private String tenNV;

    public PhieuDatMon() {}

    public PhieuDatMon(String maPDB, LocalDateTime ngayDat, String ghiChu, Ban maBan, String tenNV)
    {
        this.maPDB = maPDB;
        this.ngayDat = ngayDat;
        this.ghiChu = ghiChu;
        this.maBan = maBan;
        this.tenNV = tenNV;
    }

    public String getMaPDB()
    {
        return maPDB;
    }

    public void setMaPDB(String maPDB)
    {
        this.maPDB = maPDB;
    }

    public LocalDateTime getNgayDat()
    {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat)
    {
        this.ngayDat = ngayDat;
    }

    public String getGhiChu()
    {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu)
    {
        this.ghiChu = ghiChu;
    }

    public String getMaBan()
    {
        return maBan.getMaBan();
    }

    public void setMaBan(Ban maBan)
    {
        this.maBan = maBan;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
}
