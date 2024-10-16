package Entity;

import java.time.LocalDate;
import java.util.Objects;

public class NhanVien {
    private String maNV;
    private String tenNV;
    private String sdt;
    private String email;
    private LocalDate ngaySinh;
    private TaiKhoan tenDangNhap;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, String sdt, String email, LocalDate ngaySinh, TaiKhoan tenDangNhap) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sdt = sdt;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.tenDangNhap = tenDangNhap;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public TaiKhoan getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(TaiKhoan tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NhanVien nhanVien)) return false;
        return Objects.equals(maNV, nhanVien.maNV);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maNV);
    }
}
