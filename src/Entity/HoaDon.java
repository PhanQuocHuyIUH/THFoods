package Entity;

import java.util.Date;
import java.util.List;

public class HoaDon
{
    private String maHD;
    private Date ngayTao; // Year(+ 1900) - Month(+1) - Date
    private List<ChiTietHoaDon> chiTietHoaDon;

    public HoaDon() {}

    public HoaDon(String maHD, Date ngayTao , List<ChiTietHoaDon> chiTietHoaDon)
    {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
        this.chiTietHoaDon = chiTietHoaDon;
    }
    {
        this.maHD = maHD;
        this.ngayTao = ngayTao;
    }

    public String getMaHD()
    {
        return maHD;
    }

    public void setMaHD(String maHD)
    {
        this.maHD = maHD;
    }

    public Date getNgayTao()
    {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao)
    {
        this.ngayTao = ngayTao;
    }

    public List<ChiTietHoaDon> getChiTietHoaDon() {
        return chiTietHoaDon;
    }

    public void setChiTietHoaDon(List<ChiTietHoaDon> chiTietHoaDon) {
        this.chiTietHoaDon = chiTietHoaDon;
    }
}
