package Entity;

import java.util.Objects;

public class Ban
{
    private String maBan;
    private TrangThaiBan trangThai;
    private int soGhe;

    public Ban() {}

    public Ban(String maBan) {
        this.maBan = maBan;
    }

    public Ban(String maBan, int soGhe) {
        this.maBan = maBan;
        this.soGhe = soGhe;
    }

    public Ban(String maBan, TrangThaiBan trangThai, int soGhe)
    {
        this.maBan = maBan;
        this.trangThai = trangThai;
        this.soGhe = soGhe;
    }

    public String getMaBan()
    {
        return maBan;
    }

    public void setMaBan(String maBan)
    {
        this.maBan = maBan;
    }

    public TrangThaiBan getTrangThai()
    {
        return trangThai;
    }

    public void setTrangThai(TrangThaiBan trangThai)
    {
        this.trangThai = trangThai;
    }

    public int getSoGhe()
    {
        return soGhe;
    }

    public void setSoGhe(int soGhe)
    {
        this.soGhe = soGhe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ban ban = (Ban) o;
        return Objects.equals(maBan, ban.maBan);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maBan);
    }
}
