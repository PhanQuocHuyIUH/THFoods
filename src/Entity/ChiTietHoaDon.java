package Entity;

public class ChiTietHoaDon
{
    private String maHD;
    private String maMon;
    private int soLuong;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(String maHD, String maMon, int soLuong)
    {
        this.maHD = maHD;
        this.maMon = maMon;
        this.soLuong = soLuong;
    }

    public String getMaHD()
    {
        return maHD;
    }

    public void setMaHD(String maHD)
    {
        this.maHD = maHD;
    }

    public String getMaMon()
    {
        return maMon;
    }

    public void setMaMon(String maMon)
    {
        this.maMon = maMon;
    }

    public int getSoLuong()
    {
        return soLuong;
    }

    public void setSoLuong(int soLuong)
    {
        this.soLuong = soLuong;
    }

}
