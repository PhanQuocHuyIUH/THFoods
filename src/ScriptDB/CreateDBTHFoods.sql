use master
create database THFoods

use THFoods

-- Tạo bảng Tài khoản
CREATE TABLE TaiKhoan (
	tenDangNhap VARCHAR(20) PRIMARY KEY,
    matKhau VARCHAR(30) NOT NULL,
);

-- Tao bảng Người quản trị
CREATE TABLE NguoiQuanTri (
	tenDangNhap VARCHAR(20) PRIMARY KEY,
    matKhau VARCHAR(20) NOT NULL,
);

-- Tạo bảng Người quản lý
CREATE TABLE NguoiQuanLy (
    maQL VARCHAR(15) PRIMARY KEY,
    tenQL NVARCHAR(40) NOT NULL,
    sdt VARCHAR(12),
    email VARCHAR(50),
    tenDangNhap VARCHAR(20) FOREIGN KEY REFERENCES TaiKhoan(tenDangNhap)
);

-- Tạo bảng Nhân viên
CREATE TABLE NhanVien (
    maNV VARCHAR(15) PRIMARY KEY,
    tenNV NVARCHAR(40) NOT NULL,
    sdt VARCHAR(12),
    email VARCHAR(50),
    ngaySinh DATE,
    tenDangNhap VARCHAR(20) FOREIGN KEY REFERENCES TaiKhoan(tenDangNhap)
);

-- Tạo bảng Khách hàng
CREATE TABLE KhachHang (
    maKH VARCHAR(15) PRIMARY KEY,
    tenKH NVARCHAR(50) NOT NULL,
    sdt VARCHAR(12)
);

-- Tạo bảng Bàn
CREATE TABLE Ban (
    maBan VARCHAR(15) PRIMARY KEY,
    trangThai VARCHAR(20),
    soGhe INT
);

-- Tạo bảng Món ăn
CREATE TABLE MonAn (
    maMon VARCHAR(20) PRIMARY KEY,
    tenMon NVARCHAR(50) NOT NULL,
    loaiMon NVARCHAR(20),
    donGia FLOAT,
    moTa NVARCHAR(MAX)
);

-- Tạo bảng Hóa đơn
CREATE TABLE HoaDon (
    maHD VARCHAR(20) PRIMARY KEY,
    ngayTao DATETIME,
);

-- Tạo bảng Phiếu đặt mon
CREATE TABLE PhieuDatMon (
    maPDB VARCHAR(20) PRIMARY KEY,
    ngayDat DATETIME,
    ghiChu NVARCHAR(MAX),
    maBan VARCHAR(15) FOREIGN KEY REFERENCES Ban(maBan),
	nhanVien NVARCHAR(40),
);

-- Tạo bảng chi tiết đặt món
CREATE TABLE CTDM (
	maPDB VARCHAR(20),
    maMon VARCHAR(20),
    soLuong INT,
    PRIMARY KEY (maPDB, maMon),
    FOREIGN KEY (maPDB) REFERENCES PhieuDatMon(maPDB),
    FOREIGN KEY (maMon) REFERENCES MonAn(maMon)
);

-- Tạo bảng Chi tiết hóa đơn
CREATE TABLE CTHD (
    maHD VARCHAR(20),
    monAn NVARCHAR(50),
    soLuong INT,
	donGia FLOAT,
    PRIMARY KEY (maHD, monAn),
);

-- Tạo bảng Đơn đặt bàn
CREATE TABLE DonDatBan (
    maDDB VARCHAR(20) PRIMARY KEY,
    ngayDatBan DATE,
    soGhe INT,
	ghiChu NVARCHAR(MAX),
    maBan VARCHAR(15) FOREIGN KEY REFERENCES Ban(maBan)
);

