use THFoods

CREATE PROCEDURE getInforTkNV
AS
BEGIN
    SELECT nv.maNV, nv.tenNV, nv.sdt, nv.email, nv.ngaySinh, tk.tenDangNhap, matKhau
    FROM NhanVien nv
    JOIN TaiKhoan tk ON nv.tenDangNhap= tk.tenDangNhap;
END;

EXECUTE getInforTkNV

CREATE PROCEDURE getInforTkQL
AS
BEGIN
    SELECT ql.maQL, ql.tenQL, ql.sdt, ql.email, tk.tenDangNhap, matKhau
    FROM NguoiQuanLy ql
    JOIN TaiKhoan tk ON ql.tenDangNhap= tk.tenDangNhap;
END;

EXECUTE getInforTkQL



Delete from TaiKhoan
where tenDangNhap = 'QL02'




