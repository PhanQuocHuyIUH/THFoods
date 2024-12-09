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
('B1', 'Trong', 2),
('B2', 'Trong', 2),
('B3', 'DangDung', 2),
('B4', 'Trong', 2),
('B5', 'Trong', 2),
('B6', 'DangDung', 2),
('B7', 'Trong', 2),
('B8', 'DangDung', 2),
('B9', 'Trong', 4),
('B10', 'Trong', 4),
('B11', 'DangDung', 4),
('B12', 'Trong', 4),
('B13', 'Trong', 4),
('B14', 'DangDung', 4),
('B15', 'Trong', 4),
('B16', 'Trong', 4),
('B17', 'Trong', 6),
('B18', 'DangDung', 6),
('B19', 'Trong', 6),
('B20', 'Trong', 6),
('B21', 'DangDung', 6),
('B22', 'Trong', 8),
('B23', 'Trong', 8),
('B24', 'DangDung', 8),
('B25', 'Trong', 8),
('B26', 'Trong', 10),
('B27', 'DangDung', 10),
('B28', 'Trong', 10),
('B29', 'Trong', 10),
('B30', 'DangDung', 10);

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
('HD1', '2024-10-16 00:00:00'),
('HD2', '2024-10-16 00:00:00'),
('HD3', '2024-10-16 00:00:00'),
('HD4', '2024-10-16 00:00:00'),
('HD5', '2024-10-16 00:00:00'),
('HD6', '2024-10-16 00:00:00'),
('HD7', '2024-10-16 00:00:00'),
('HD8', '2024-10-16 00:00:00'),
('HD9', '2024-10-16 00:00:00'),
('HD10', '2024-10-16 00:00:00'),
('HD11', '2024-10-16 00:00:00'),
('HD12', '2024-10-16 00:00:00'),
('HD13', '2024-10-16 00:00:00'),
('HD14', '2024-10-16 00:00:00'),
('HD15', '2024-10-16 00:00:00'),
('HD16', '2024-10-16 00:00:00'),
('HD17', '2024-10-16 00:00:00'),
('HD18', '2024-10-16 00:00:00'),
('HD19', '2024-10-16 00:00:00'),
('HD20', '2024-10-16 00:00:00');

INSERT INTO HoaDon (maHD, ngayTao) VALUES
('HD21', '2024-11-01 10:30:00'),
('HD22', '2024-11-03 14:20:00'),
('HD23', '2024-11-05 19:45:00'),
('HD24', '2024-11-06 08:15:00'),
('HD25', '2024-11-07 13:50:00'),
('HD26', '2024-11-10 18:05:00'),
('HD27', '2024-11-11 09:30:00'),
('HD28', '2024-11-12 12:00:00'),
('HD29', '2024-11-14 20:10:00'),
('HD30', '2024-11-15 16:25:00'),
('HD31', '2024-11-16 11:40:00'),
('HD32', '2024-11-18 17:30:00'),
('HD33', '2024-11-19 08:00:00'),
('HD34', '2024-11-21 15:45:00'),
('HD35', '2024-11-22 10:50:00'),
('HD36', '2024-11-24 19:30:00'),
('HD37', '2024-11-25 13:20:00'),
('HD38', '2024-11-27 18:00:00'),
('HD39', '2024-11-28 11:15:00'),
('HD40', '2024-11-30 14:35:00');

-- Thêm dữ liệu vào bảng Phiếu đặt món
INSERT INTO PhieuDatMon (maPDB, ngayDat, ghiChu, maBan, nhanVien) VALUES
('PDB21', '2024-10-16 00:00:00', N'Không nấu cay', 'B11', N'Phan Nhật Tiến'),
('PDB22', '2024-10-16 00:00:00', N'Không hành', 'B14', N'Phan Nhật Tiến'),
('PDB23', '2024-10-16 00:00:00', N'Không', 'B18', N'Phan Nhật Tiến'),
('PDB24', '2024-10-16 00:00:00', N'Không', 'B21', N'Phan Nhật Tiến'),
('PDB25', '2024-10-16 00:00:00', N'Không', 'B24', N'Phan Nhật Tiến'),
('PDB26', '2024-10-16 00:00:00', N'Không', 'B27', N'Nguyễn Đinh Xuân Trường'),
('PDB27', '2024-10-16 00:00:00', N'Không', 'B3', N'Nguyễn Đinh Xuân Trường'),
('PDB28', '2024-10-16 00:00:00', N'Không', 'B30', N'Nguyễn Văn Tòng'),
('PDB29', '2024-10-16 00:00:00', N'Không', 'B6', N'Nguyễn Văn Tòng'),
('PDB30', '2024-10-16 00:00:00', N'Không', 'B8', N'Phạm Ngọc Hùng');

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
INSERT INTO CTHD (maHD, monAn, soLuong, donGia) VALUES
('HD1', N'Phở bò', 2, 60000),
('HD1', N'Cà phê sữa', 1, 35000),
('HD2', N'Bánh mì', 3, 30000),
('HD2', N'Chè đậu xanh', 2, 30000),
('HD2', N'Bún bò Huế', 1, 60000),
('HD3', N'Sinh tố dâu', 1, 30000),
('HD3', N'Gỏi cuốn', 4, 35000),
('HD3', N'Kem dừa', 2, 30000),
('HD4', N'Cơm gà', 1, 65000),
('HD4', N'Nước cam ép', 2, 30000),
('HD4', N'Salad trộn', 1, 30000),
('HD5', N'Bánh flan', 3, 30000),
('HD5', N'Mì xào bò', 2, 50000),
('HD6', N'Trá sữa trân châu', 2, 35000),
('HD6', N'Chả giò', 3, 40000),
('HD7', N'Rau câu dừa', 1, 15000),
('HD7', N'Lẩu gà', 1, 120000),
('HD8', N'Nước chanh tươi', 3, 15000),
('HD8', N'Bánh xèo', 2, 45000),
('HD9', N'Kem chocolate', 2, 25000),
('HD9', N'Bún chả Hà Nội', 1, 70000),
('HD9', N'Sữa đậu nành', 4, 10000),
('HD10', N'Xôi gà', 2, 35000),
('HD10', N'Trái cây tươi', 1, 30000),
('HD11', N'Hủ tiếu Nam Vang', 2, 60000),
('HD11', N'Coca Cola', 3, 15000),
('HD12', N'Bánh cuốn', 2, 30000),
('HD12', N'Bánh pudding', 1, 20000),
('HD13', N'Canh chua cá lóc', 1, 75000),
('HD13', N'Sinh tố xoài', 2, 30000),
('HD14', N'Bắp rang bơ', 2, 20000),
('HD14', N'Chè sương sa', 3, 15000),
('HD15', N'Cơm tấm sườn', 1, 55000),
('HD15', N'Nước dừa', 3, 25000),
('HD16', N'Bánh tráng trộn', 1, 25000),
('HD16', N'Tiramisu', 2, 50000),
('HD17', N'Bò kho', 1, 65000),
('HD17', N'Trà sâm bổ lượng', 2, 25000),
('HD18', N'Bánh bèo', 3, 30000),
('HD18', N'Chè bắp', 2, 25000),
('HD19', N'Bún mắm', 1, 70000),
('HD19', N'Nước sâm', 3, 20000),
('HD20', N'Khoai tây chiên', 4, 25000),
('HD20', N'Kem vani', 1, 30000),
('HD20', N'Cá kho tộ', 1, 80000);

INSERT INTO CTHD (maHD, monAn, soLuong, donGia) VALUES
-- HD21
('HD21', N'Phở bò', 1, 60000),
('HD21', N'Cà phê sữa', 2, 35000),
('HD21', N'Bánh mì', 3, 30000),
-- HD22
('HD22', N'Bún bò Huế', 2, 60000),
('HD22', N'Sinh tố dâu', 1, 30000),
('HD22', N'Gỏi cuốn', 2, 35000),
-- HD23
('HD23', N'Kem dừa', 3, 30000),
('HD23', N'Nước cam ép', 1, 30000),
-- HD24
('HD24', N'Bánh flan', 2, 30000),
('HD24', N'Salad trộn', 1, 30000),
-- HD25
('HD25', N'Cơm gà', 2, 65000),
('HD25', N'Trà sữa trân châu', 1, 35000),
('HD25', N'Bánh mì', 3, 30000),
-- HD26
('HD26', N'Bánh xèo', 2, 45000),
('HD26', N'Chè đậu xanh', 1, 30000),
-- HD27
('HD27', N'Nước cam ép', 2, 30000),
('HD27', N'Cơm tấm sườn', 3, 55000),
('HD27', N'Nước dừa', 1, 25000),
-- HD28
('HD28', N'Bún thịt nướng', 1, 55000),
('HD28', N'Kem chocolate', 2, 25000),
-- HD29
('HD29', N'Lẩu gà', 1, 120000),
('HD29', N'Nước sâm', 2, 20000),
('HD29', N'Chả giò', 3, 40000),
-- HD30
('HD30', N'Mì xào bò', 2, 50000),
('HD30', N'Xôi gà', 2, 35000),
('HD30', N'Bắp rang bơ', 1, 20000),
-- HD31
('HD31', N'Hủ tiếu Nam Vang', 3, 60000),
('HD31', N'Bánh flan', 2, 30000),
('HD31', N'Nước ép cà rốt', 1, 30000),
-- HD32
('HD32', N'Bún mắm', 2, 70000),
('HD32', N'Khoai tây chiên', 1, 25000),
('HD32', N'Nước chanh tươi', 1, 15000),
-- HD33
('HD33', N'Canh chua cá lóc', 1, 75000),
('HD33', N'Bánh bèo', 2, 30000),
-- HD34
('HD34', N'Kem vani', 2, 30000),
('HD34', N'Bánh pudding', 1, 20000),
('HD34', N'Tiramisu', 1, 50000),
-- HD35
('HD35', N'Cá kho tộ', 1, 80000),
('HD35', N'Nước ép cà rốt', 2, 30000),
('HD35', N'Salad trộn', 2, 30000),
-- HD36
('HD36', N'Bún chả Hà Nội', 1, 70000),
('HD36', N'Sữa đậu nành', 3, 10000),
-- HD37
('HD37', N'Kem chocolate', 2, 25000),
('HD37', N'Bánh da lợn', 1, 15000),
('HD37', N'Bánh tráng trộn', 1, 25000),
-- HD38
('HD38', N'Rau câu dừa', 2, 15000),
('HD38', N'Trái cây tươi', 3, 30000),
-- HD39
('HD39', N'Lẩu gà', 1, 120000),
('HD39', N'Nước dừa', 2, 25000),
-- HD40
('HD40', N'Bánh xèo', 2, 45000),
('HD40', N'Trá sữa trân châu', 1, 35000),
('HD40', N'Bún thịt nướng', 1, 55000);

-- Thêm dữ liệu vào bảng Đơn đặt bàn
INSERT INTO DonDatBan (maDDB, ngayDatBan, soGhe, ghiChu, maBan) VALUES
('DDB1', '2024-10-17 12:00:00', 2, N'Bàn gần cửa sổ, 18-19h đến' , 'B3'),
('DDB2', '2024-10-17 12:00:00', 10, N'Bàn rộng 12h đến' , 'B20'),
('DDB3', '2024-10-17 12:00:00', 4, N'10h đến' , 'B5'),
('DDB4', '2024-10-17 12:00:00', 4, N'20h đến' , 'B6'),
('DDB5', '2024-10-17 12:00:00', 4, N'19h đến' , 'B7');


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