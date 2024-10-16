package Entity;

import java.util.Objects;

public class TaiKhoan
{
    private String maTaiKhoan;
    private String tenTaiKhoan;

    public TaiKhoan() {}

    public TaiKhoan(String maTaiKhoan, String tenTaiKhoan)
    {
        this.maTaiKhoan = maTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaiKhoan taiKhoan = (TaiKhoan) o;
        return Objects.equals(maTaiKhoan, taiKhoan.maTaiKhoan);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maTaiKhoan);
    }
}
