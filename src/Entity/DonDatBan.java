package Entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class DonDatBan {
    private String maDD;
    private String tenKH;
    private String sdt;
    private LocalDateTime ngayDat;
    private int soGhe;
    private String ghiChu;
    private Ban maBan;
    private TrangThaiDonDat trangThaiDDB;
    private KhachHang maKH;


    public DonDatBan(String maDD, String tenKH, String sDT, LocalDateTime ngayDat, int soGhe, String ghiChu,
                     Ban maBan, TrangThaiDonDat trangThaiDDB, KhachHang maKH) {
        this.maDD = maDD;
        this.tenKH = tenKH;
        this.sdt = sDT;
        this.ngayDat = ngayDat;
        this.soGhe = soGhe;
        this.ghiChu = ghiChu;
        this.maBan = maBan;
        this.trangThaiDDB = trangThaiDDB;
        this.maKH = maKH;
    }

    public String getMaDD() {
        return maDD;
    }

    public void setMaDD(String maDD) {
        this.maDD = maDD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getsDT() {
        return sdt;
    }

    public void setsDT(String sDT) {
        this.sdt = sDT;
    }

    public LocalDateTime getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(LocalDateTime ngayDat) {
        this.ngayDat = ngayDat;
    }

    public int getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(int soGhe) {
        this.soGhe = soGhe;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Ban getMaBan() {
        return maBan;
    }

    public void setMaBan(Ban maBan) {
        this.maBan = maBan;
    }

    public TrangThaiDonDat getTrangThaiDDB() {
        return trangThaiDDB;
    }

    public void setTrangThaiDDB(TrangThaiDonDat trangThaiDDB) {
        this.trangThaiDDB = trangThaiDDB;
    }

    public KhachHang getMaKH() {
        return maKH;
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
