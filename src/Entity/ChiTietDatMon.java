package Entity;

public class ChiTietDatMon
{
    private String maPDB;
    private String maMon;
    private int soLuong;

    public ChiTietDatMon() {}

    public ChiTietDatMon(String maPDB, String maMon, int soLuong)
    {
        this.maPDB = maPDB;
        this.maMon = maMon;
        this.soLuong = soLuong;
    }

    public String getMaPDB()
    {
        return maPDB;
    }

    public void setMaPDB(String maPDB)
    {
        this.maPDB = maPDB;
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
