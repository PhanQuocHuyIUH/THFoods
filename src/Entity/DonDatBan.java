package Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class DonDatBan {
    private String maDD;
    private KhachHang tenKH;
    private KhachHang sDT;
    private LocalDateTime ngayDat;
    private int soGhe;
    private String ghiChu;
    private Ban maBan;
    private TrangThaiDonDat trangThaiDDB;
    private KhachHang maKH;

    public DonDatBan(String maDonDat, KhachHang khachHang, String soDienThoai, LocalDateTime parse, int soNguoi, String ghiChu, Ban maBan, TrangThaiDonDat tinhTrangDon) {
    }

    public DonDatBan(String maDD, KhachHang tenKH, KhachHang sDT, LocalDateTime ngayDat, int soGhe,
                     String ghiChu, Ban maBan, TrangThaiDonDat trangThaiDDB, KhachHang maKH) {
        this.maDD = maDD;
        this.tenKH = tenKH;
        this.sDT = sDT;
        this.ngayDat = ngayDat;
        this.soGhe = soGhe;
        this.ghiChu = ghiChu;
        this.maBan = maBan;
        this.trangThaiDDB = trangThaiDDB;
        this.maKH = maKH;
    }

    public DonDatBan(String maDD, KhachHang tenKH, KhachHang sDT, LocalDateTime ngayDat, int soGhe,
                     String ghiChu, Ban maBan, TrangThaiDonDat trangThaiDDB) {
        this.maDD = maDD;
        this.tenKH = tenKH;
        this.sDT = sDT;
        this.ngayDat = ngayDat;
        this.soGhe = soGhe;
        this.ghiChu = ghiChu;
        this.maBan = maBan;
        this.trangThaiDDB = trangThaiDDB;
    }

    public String getMaDD() {
        return maDD;
    }

    public KhachHang getTenKH() {
        return tenKH;
    }

    public KhachHang getsDT() {
        return sDT;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public Ban getMaBan() {
        return maBan;
    }

    public TrangThaiDonDat getTrangThaiDDB() {
        return trangThaiDDB;
    }

    public KhachHang getMaKH() {
        return maKH;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public void setTenKH(KhachHang tenKH) {
        this.tenKH = tenKH;
    }

    public void setsDT(KhachHang sDT) {
        this.sDT = sDT;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public void setMaBan(Ban maBan) {
        this.maBan = maBan;
    }

    public void setTrangThaiDDB(TrangThaiDonDat trangThaiDDB) {
        this.trangThaiDDB = trangThaiDDB;
    }

    public void setMaKH(KhachHang maKH) {
        this.maKH = maKH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonDatBan donDatBan = (DonDatBan) o;
        return Objects.equals(maDD, donDatBan.maDD);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maDD);
    }
}
