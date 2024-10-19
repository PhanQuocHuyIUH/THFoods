package Entity;

import java.util.List;

public class HoaDon
{
    private String maHD;
    private String ngayTao;
    private List<ChiTietHoaDon> chiTietHoaDon;

    public HoaDon() {}

    public HoaDon(String maHD, String ngayTao , List<ChiTietHoaDon> chiTietHoaDon)
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

    public String getNgayTao()
    {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao)
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
