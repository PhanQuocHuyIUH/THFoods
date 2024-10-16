package Entity;

import java.util.Objects;

public class NguoiQuanLy extends TaiKhoan
{
    private String maQL;
    private String tenQL;
    private String sdt;
    private String email;

    public NguoiQuanLy() {}

    public NguoiQuanLy(String maQL, String tenQL, String sdt, String email)
    {
        this.maQL = maQL;
        this.tenQL = tenQL;
        this.sdt = sdt;
        this.email = email;
    }

    public String getMaQL()
    {
        return maQL;
    }

    public void setMaQL(String maQL)
    {
        this.maQL = maQL;
    }

    public String getTenQL()
    {
        return tenQL;
    }

    public void setTenQL(String tenQL)
    {
        this.tenQL = tenQL;
    }

    public String getSdt()
    {
        return sdt;
    }

    public void setSdt(String sdt)
    {
        this.sdt = sdt;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NguoiQuanLy that = (NguoiQuanLy) o;
        return Objects.equals(maQL, that.maQL);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maQL);
    }
}
