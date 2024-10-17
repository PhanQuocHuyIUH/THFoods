package Entity;

import java.util.Objects;

public class NguoiQuanTri
{
    private String tenDangNhap;
    private String matKhau;

    public NguoiQuanTri(String tenDangNhap, String matKhau)
    {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NguoiQuanTri that = (NguoiQuanTri) o;
        return Objects.equals(tenDangNhap, that.tenDangNhap);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tenDangNhap);
    }
}
