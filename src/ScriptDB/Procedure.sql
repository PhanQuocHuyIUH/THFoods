use THFoods

CREATE PROCEDURE getInforTkN
AS
BEGIN
    SELECT nv.maNV, nv.tenNV, nv.sdt, nv.email, nv.ngaySinh, tk.tenDangNhap, matKhau
    FROM NhanVien nv
    JOIN TaiKhoan tk ON nv.tenDangNhap= tk.tenDangNhap;
END;

EXECUTE getInforTkN

