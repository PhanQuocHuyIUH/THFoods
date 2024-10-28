use THFoods
-- Thêm dữ liệu vào bảng Tài khoản
INSERT INTO TaiKhoan (tenDangNhap, matKhau) VALUES 
('QL01', 'QL01'),
('NV01', 'NV01'),
('NV02', 'NV02'),
('NV03', 'NV03'),
('NV04', 'NV04');

-- Thêm dữ liệu vào bảng Người quản trị
INSERT INTO NguoiQuanTri (tenDangNhap, matKhau) VALUES 
('admin', 'admin123');

-- Thêm dữ liệu vào bảng Người quản lý
INSERT INTO NguoiQuanLy (maQL, tenQL, sdt, email, tenDangNhap) VALUES 
('QL01', N'Phan Quốc Huy', '0935004922', 'phanquochuy112233@gmail.com', 'QL01');

-- Thêm dữ liệu vào bảng Nhân viên
INSERT INTO NhanVien (maNV, tenNV, sdt, email, ngaySinh, tenDangNhap) VALUES 
('NV01', N'Phan Nhật Tiến', '0912345677', 'pnt123@gmail.com', '2004-03-27', 'NV01'),
('NV02', N'Phạm Ngọc Hùng', '0912345678', 'pnh456@gmail.com', '2004-12-12', 'NV02'),
('NV03', N'Nguyễn Văn Tòng', '0912345679', 'nvt789@gmail.com', '2004-01-10', 'NV03'),
('NV04', N'Nguyễn Đinh Xuân Trường', '0912345680', 'ntdx000@gmail.com', '2004-02-20', 'NV04');

-- Thêm dữ liệu vào bảng Khách hàng
INSERT INTO KhachHang (maKH, tenKH, sdt) VALUES 
('KH0', '', ''),
('KH1', N'Trần Thị B', '012345681'),
('KH2', N'Lê Văn C', '012345682'),
('KH3', N'Nguyễn Thị D', '012345683'),
('KH4', N'Nguyễn Văn A', '012345684'),
('KH5', N'Trần Thị B', '012345685'),
('KH6', N'Lê Văn C', '012345686'),
('KH7', N'Nguyễn Thị D', '012345687'),
('KH8', N'Nguyễn Văn A', '012345688'),
('KH9', N'Trần Thị B', '012345689'),
('KH10', N'Lê Văn C', '012345690'),
('KH11', N'Nguyễn Thị D', '012345691'),
('KH12', N'Nguyễn Văn A', '012345692'),
('KH13', N'Trần Thị B', '012345693'),
('KH14', N'Lê Văn C', '012345694'),
('KH15', N'Nguyễn Thị D', '012345695'),
('KH16', N'Nguyễn Văn A', '012345696'),
('KH17', N'Trần Thị B', '012345697'),
('KH18', N'Lê Văn C', '012345698'),
('KH19', N'Nguyễn Thị D', '012345699'),
('KH20', N'Nguyễn Văn A', '012345700');

-- Thêm dữ liệu vào bảng Bàn
INSERT INTO Ban (maBan, trangThai, soGhe) VALUES 
('B1', 'Trong', 4),
('B2', 'DaDat', 6),
('B3', 'DangDung', 2),
('B4', 'DaDat', 8),
('B5', 'Trong', 4),
('B6', 'DangDung', 4),
('B7', 'Trong', 4),
('B8', 'DangDung', 2),
('B9', 'DaDat', 8),
('B10', 'Trong', 10),
('B11', 'DangDung', 4),
('B12', 'Trong', 4),
('B13', 'DaDat', 2),
('B14', 'DangDung', 8),
('B15', 'Trong', 10),
('B16', 'DaDat', 4),
('B17', 'Trong', 6),
('B18', 'DangDung', 2),
('B19', 'DaDat', 8),
('B20', 'Trong', 10),
('B21', 'DangDung', 4),
('B22', 'Trong', 6),
('B23', 'DaDat', 2),
('B24', 'DangDung', 8),
('B25', 'Trong', 10),
('B26', 'DaDat', 4),
('B27', 'DangDung', 4),
('B28', 'Trong', 2),
('B29', 'DaDat', 8),
('B30', 'DangDung', 4);

-- Thêm dữ liệu vào bảng Món ăn
INSERT INTO MonAn (maMon, tenMon, loaiMon, donGia, moTa) VALUES 
('M1', N'Phở bò', N'Món chính', 60000, N'Phở bò thơm ngon, nấu theo công thức gia truyền'),
('M2', N'Cà phê sữa', N'Nước uống', 35000, N'Cà phê sữa đậm đà, thơm ngon'),
('M3', N'Bánh mì', N'Món ăn nhẹ', 30000, N'Bánh mì giòn tan, ăn kèm pate'),
('M4', N'Chè đậu xanh', N'Món tráng miệng', 30000, N'Chè đậu xanh ngọt lịm, giải nhiệt'),
('M5', N'Bún bò Huế', N'Món chính', 60000, N'Bún bò đậm đà, hương vị Huế'),
('M6', N'Sinh tố dâu', N'Nước uống', 30000, N'Sinh tố dâu tươi, bổ dưỡng'),
('M7', N'Gỏi cuốn', N'Món ăn nhẹ', 35000, N'Gỏi cuốn tôm thịt, chấm nước mắm chua ngọt'),
('M8', N'Kem dừa', N'Món tráng miệng', 30000, N'Kem dừa mát lạnh, vị ngọt dịu'),
('M9', N'Cơm gà', N'Món chính', 65000, N'Cơm gà chiên giòn, ăn kèm nước mắm tỏi ớt'),
('M10', N'Nước cam ép', N'Nước uống', 30000, N'Nước cam ép tươi, không đường'),
('M11', N'Salad trộn', N'Món ăn nhẹ', 30000, N'Salad rau củ, sốt mè rang'),
('M12', N'Bánh flan', N'Món tráng miệng', 30000, N'Bánh flan mềm mịn, ngọt thanh'),
('M13', N'Mì xào bò', N'Món chính', 50000, N'Mì xào bò với rau củ tươi'),
('M14', N'Trà sữa trân châu', N'Nước uống', 35000, N'Trá sữa vị truyền thống, thêm trân châu dai dai'),
('M15', N'Chả giò', N'Món ăn nhẹ', 40000, N'Chả giò giòn tan, nhân thịt nấm'),
('M16', N'Rau câu dừa', N'Món tráng miệng', 15000, N'Rau câu dừa mát lạnh, thơm ngon'),
('M17', N'Lẩu gà', N'Món chính', 120000, N'Lẩu gà nấm, nước dùng thanh ngọt'),
('M18', N'Nước chanh tươi', N'Nước uống', 15000, N'Nước chanh tươi, ít đường'),
('M19', N'Bánh xèo', N'Món ăn nhẹ', 45000, N'Bánh xèo giòn, ăn kèm rau sống'),
('M20', N'Kem chocolate', N'Món tráng miệng', 25000, N'Kem sô cô la đậm vị, tan chảy trong miệng'),
('M21', N'Bún chả Hà Nội', N'Món chính', 70000, N'Bún chả thơm ngon, hương vị truyền thống Hà Nội'),
('M22', N'Sữa đậu nành', N'Nước uống', 10000, N'Sữa đậu nành nguyên chất, bổ dưỡng'),
('M23', N'Xôi gà', N'Món ăn nhẹ', 35000, N'Xôi gà dẻo thơm, ăn kèm hành phi'),
('M24', N'Trái cây tươi', N'Món tráng miệng', 30000, N'Trái cây tươi theo mùa, mát lành'),
('M25', N'Hủ tiếu Nam Vang', N'Món chính', 60000, N'Hủ tiếu Nam Vang đầy đủ tôm thịt'),
('M26', N'Coca Cola', N'Nước uống', 15000, N'Nước ngọt Coca Cola, uống lạnh thêm ngon'),
('M27', N'Bánh cuốn', N'Món ăn nhẹ', 30000, N'Bánh cuốn nhân thịt, nước mắm chua ngọt'),
('M28', N'Bánh pudding', N'Món tráng miệng', 20000, N'Bánh pudding mát lạnh, vị ngọt dịu'),
('M29', N'Canh chua cá lóc', N'Món chính', 75000, N'Canh chua cá lóc, đậm đà vị miền Tây'),
('M30', N'Sinh tố xoài', N'Nước uống', 30000, N'Sinh tố xoài tươi, ngọt lịm'),
('M31', N'Bắp rang bơ', N'Món ăn nhẹ', 20000, N'Bắp rang bơ giòn rụm, thơm ngậy'),
('M32', N'Chè sương sa', N'Món tráng miệng', 15000, N'Chè sương sa, giải nhiệt ngày hè'),
('M33', N'Cơm tấm sườn', N'Món chính', 55000, N'Cơm tấm sườn nướng, ăn kèm nước mắm'),
('M34', N'Nước dừa', N'Nước uống', 25000, N'Nước dừa tươi, ngọt mát'),
('M35', N'Bánh tráng trộn', N'Món ăn nhẹ', 25000, N'Bánh tráng trộn với rau răm, khô bò'),
('M36', N'Tiramisu', N'Món tráng miệng', 50000, N'Tiramisu mềm mịn, vị cà phê đậm đà'),
('M37', N'Bò kho', N'Món chính', 65000, N'Bò kho với bánh mì, thơm ngon hấp dẫn'),
('M38', N'Trà sâm bổ lượng', N'Nước uống', 25000, N'Trá sâm bổ lượng, mát lành và bổ dưỡng'),
('M39', N'Bánh bèo', N'Món ăn nhẹ', 30000, N'Bánh bèo Huế, chấm mắm ngọt'),
('M40', N'Chè bắp', N'Món tráng miệng', 25000, N'Chè bắp ngọt ngào, thơm mùi lá dứa'),
('M41', N'Bún mắm', N'Món chính', 70000, N'Bún mắm đậm đà, đặc sản miền Tây'),
('M42', N'Nước sâm', N'Nước uống', 20000, N'Nước sâm nấu từ thảo mộc, tốt cho sức khỏe'),
('M43', N'Khoai tây chiên', N'Món ăn nhẹ', 25000, N'Khoai tây chiên giòn, ăn kèm tương ớt'),
('M44', N'Kem vani', N'Món tráng miệng', 30000, N'Kem vani mát lạnh, ngọt dịu'),
('M45', N'Cá kho tộ', N'Món chính', 80000, N'Cá kho tộ hương vị miền Nam, đậm đà'),
('M46', N'Nước ép cà rốt', N'Nước uống', 30000, N'Nước ép cà rốt tươi, giàu vitamin'),
('M47', N'Bánh da lợn', N'Món ăn nhẹ', 15000, N'Bánh da lợn nhiều lớp, ngọt dịu'),
('M48', N'Bánh mousse chanh', N'Món tráng miệng', 30000, N'Bánh mousse vị chanh, mềm mịn'),
('M49', N'Bún thịt nướng', N'Món chính', 55000, N'Bún thịt nướng thơm phức, ăn kèm rau sống'),
('M50', N'Sữa chua dâu', N'Món tráng miệng', 30000, N'Sữa chua dâu mát lạnh, vị chua ngọt')

	
-- Thêm dữ liệu vào bảng Hóa đơn
INSERT INTO HoaDon (maHD, ngayTao) VALUES 
('HD1', '2024-10-16'),
('HD2', '2024-10-16'),
('HD3', '2024-10-16'),
('HD4', '2024-10-16'),
('HD5', '2024-10-16'),
('HD6', '2024-10-16'),
('HD7', '2024-10-16'),
('HD8', '2024-10-16'),
('HD9', '2024-10-16'),
('HD10', '2024-10-16'),
('HD11', '2024-10-16'),
('HD12', '2024-10-16'),
('HD13', '2024-10-16'),
('HD14', '2024-10-16'),
('HD15', '2024-10-16'),
('HD16', '2024-10-16'),
('HD17', '2024-10-16'),
('HD18', '2024-10-16'),
('HD19', '2024-10-16'),
('HD20', '2024-10-16');

-- Thêm dữ liệu vào bảng Phiếu đặt món
INSERT INTO PhieuDatMon (maPDB, ngayDat, ghiChu, maBan, maNV) VALUES 
('PDB21', '2024-10-16', N'Không nấu cay', 'B11', 'NV01'),
('PDB22', '2024-10-16', N'Không hành', 'B14', 'NV02'),
('PDB23', '2024-10-16', N'Không', 'B18', 'NV03'),
('PDB24', '2024-10-16', N'Không', 'B21', 'NV04'),
('PDB25', '2024-10-16', N'Không', 'B24', 'NV01'),
('PDB26', '2024-10-16', N'Không', 'B27', 'NV01'),
('PDB27', '2024-10-16', N'Không', 'B3', 'NV02'),
('PDB28', '2024-10-16', N'Không', 'B30', 'NV03'),
('PDB29', '2024-10-16', N'Không', 'B6', 'NV04'),
('PDB30', '2024-10-16', N'Không', 'B8', 'NV01');

-- Thêm dữ liệu vào bảng Chi tiết đặt món (CTDM)
INSERT INTO CTDM (maPDB, maMon, soLuong) VALUES 
('PDB21', 'M12', 2),
('PDB21', 'M7', 3),
('PDB21', 'M25', 1),
('PDB21', 'M34', 4),
('PDB22', 'M3', 5),
('PDB22', 'M8', 2),
('PDB22', 'M27', 1),
('PDB22', 'M15', 3),
('PDB23', 'M18', 2),
('PDB23', 'M29', 4),
('PDB23', 'M42', 3),
('PDB24', 'M33', 1),
('PDB24', 'M45', 3),
('PDB24', 'M6', 2),
('PDB24', 'M11', 5),
('PDB25', 'M19', 4),
('PDB25', 'M2', 1),
('PDB25', 'M38', 2),
('PDB26', 'M27', 5),
('PDB26', 'M21', 3),
('PDB26', 'M13', 2),
('PDB26', 'M47', 4),
('PDB27', 'M34', 3),
('PDB27', 'M14', 2),
('PDB27', 'M30', 5),
('PDB27', 'M50', 1),
('PDB28', 'M6', 5),
('PDB28', 'M11', 3),
('PDB28', 'M25', 2),
('PDB29', 'M38', 1),
('PDB29', 'M24', 4),
('PDB29', 'M17', 1),
('PDB29', 'M9', 3),
('PDB30', 'M50', 4),
('PDB30', 'M5', 2),
('PDB30', 'M28', 3),
('PDB30', 'M16', 1),
('PDB30', 'M31', 5);


-- Thêm dữ liệu vào bảng Chi tiết hóa đơn (CTHD)
INSERT INTO CTHD (maHD, maMon, soLuong) VALUES 
('HD1', 'M1', 2),
('HD1', 'M12', 1),
('HD1', 'M27', 3),
('HD2', 'M3', 3),
('HD2', 'M8', 2),
('HD3', 'M15', 4),
('HD3', 'M7', 1),
('HD3', 'M32', 3),
('HD4', 'M4', 2),
('HD4', 'M19', 5),
('HD4', 'M25', 3),
('HD5', 'M9', 1),
('HD5', 'M23', 2),
('HD5', 'M14', 4),
('HD6', 'M2', 3),
('HD6', 'M18', 1),
('HD7', 'M33', 2),
('HD7', 'M11', 4),
('HD7', 'M5', 3),
('HD8', 'M26', 1),
('HD8', 'M20', 5),
('HD8', 'M6', 2),
('HD9', 'M13', 4),
('HD9', 'M37', 3),
('HD10', 'M29', 2),
('HD10', 'M40', 1),
('HD10', 'M30', 5),
('HD11', 'M10', 3),
('HD11', 'M24', 2),
('HD11', 'M22', 4),
('HD12', 'M31', 1),
('HD12', 'M35', 5),
('HD13', 'M16', 4),
('HD13', 'M47', 2),
('HD13', 'M28', 3),
('HD14', 'M21', 1),
('HD14', 'M45', 5),
('HD15', 'M43', 3),
('HD15', 'M17', 1),
('HD15', 'M8', 4),
('HD16', 'M48', 2),
('HD16', 'M14', 1),
('HD17', 'M36', 4),
('HD17', 'M50', 5),
('HD17', 'M12', 3),
('HD18', 'M46', 2),
('HD18', 'M39', 1),
('HD18', 'M7', 3),
('HD19', 'M44', 5),
('HD19', 'M34', 2),
('HD20', 'M49', 3),
('HD20', 'M38', 4),
('HD20', 'M41', 1);

-- Thêm dữ liệu vào bảng Đơn đặt bàn
INSERT INTO DonDatBan (maDDB, ngayDatBan, soGhe, ghiChu, maBan, khachHang, sdt) VALUES
('DDB1', '2024-10-17', 2, N'Bàn gần cửa sổ, 18-19h đến' , 'B3', N'Trần Thị B', '012345681'),
('DDB2', '2024-10-17', 10, N'Bàn rộng 12h đến' , 'B20', N'Nguyễn Thị D', '012345695'),
('DDB3', '2024-10-17', 4, N'10h đến' , 'B5', N'Nguyễn Văn A', '012345688'),
('DDB4', '2024-10-17', 4, N'20h đến' , 'B6', N'Lê Văn C', '012345694'),
('DDB5', '2024-10-17', 4, N'19h đến' , 'B7', N'Trần Thị B', '012345697');


select * from TaiKhoan
select * from NhanVien
select * from NguoiQuanLy
select * from NguoiQuanTri
select * from KhachHang
select * from Ban where trangThai like 'Dangdung'
select * from MonAn where loaiMon like N'Món chính'
select * from MonAn where loaiMon like N'Món ăn nhẹ'
select * from MonAn where loaiMon like N'Món tráng miệng'
select * from MonAn where loaiMon like N'Nước uống'
select * from HoaDon
select * from PhieuDatMon
select * from CTDM
select * from CTHD
select * from DonDatBan