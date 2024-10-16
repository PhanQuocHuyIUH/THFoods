package Entity;

public class PhieuDatMon
{
    private String maPDB;
    private String ngayDat;
    private String ghiChu;
    private Ban maBan;
    private NhanVien maNV;

    public PhieuDatMon() {}

    public PhieuDatMon(String maPDB, String ngayDat, String ghiChu, Ban maBan, NhanVien maNV)
    {
        this.maPDB = maPDB;
        this.ngayDat = ngayDat;
        this.ghiChu = ghiChu;
        this.maBan = maBan;
        this.maNV = maNV;
    }

    public String getMaPDB()
    {
        return maPDB;
    }

    public void setMaPDB(String maPDB)
    {
        this.maPDB = maPDB;
    }

    public String getNgayDat()
    {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat)
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

    public Ban getMaBan()
    {
        return maBan;
    }

    public void setMaBan(Ban maBan)
    {
        this.maBan = maBan;
    }

    public NhanVien getMaNV()
    {
        return maNV;
    }

    public void setMaNV(NhanVien maNV)
    {
        this.maNV = maNV;
    }
}
