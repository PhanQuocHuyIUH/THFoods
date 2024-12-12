package Gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.util.List;
import java.time.format.DateTimeFormatter;
import Entity.HoaDon;
import Entity.ChiTietHoaDon;

public class HoaDonPDFExporter {

    public void exportHoaDon(HoaDon hoaDon, String filePath, String ban) {
        try {
            // Tạo tài liệu PDF
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Sử dụng font Tahoma hoặc font khác hỗ trợ Unicode
            BaseFont bf = BaseFont.createFont("C:\\Windows\\Fonts\\Tahoma.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bf, 12);

            // Thêm tiêu đề hóa đơn
            Paragraph title = new Paragraph("HÓA ĐƠN", new Font(bf, 24, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            // Thêm thông tin cửa hàng
            Paragraph storeInfo = new Paragraph("Số 4 Đường Nguyễn Văn Bảo, Gò Vấp SĐT: 0906766000", font);
            storeInfo.setAlignment(Element.ALIGN_CENTER);
            storeInfo.setSpacingAfter(20);
            document.add(storeInfo);

            // Khu vực thông tin hóa đơn
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String ngayTao = hoaDon.getNgayTao().format(dateFormatter);
            String gioTao = hoaDon.getNgayTao().format(timeFormatter);

            Paragraph maHDLabel = new Paragraph("Mã hóa đơn: " + hoaDon.getMaHD(), font);
            Paragraph ngayTaoLabel = new Paragraph("Ngày tạo: " + ngayTao, font);
            Paragraph gioTaoLabel = new Paragraph("Giờ xuất: " + gioTao, font);
            Paragraph banLabel = new Paragraph("Bàn: " + ban, font); // Giả định thông tin bàn

            maHDLabel.setAlignment(Element.ALIGN_CENTER);
            ngayTaoLabel.setAlignment(Element.ALIGN_CENTER);
            gioTaoLabel.setAlignment(Element.ALIGN_CENTER);
            banLabel.setAlignment(Element.ALIGN_CENTER);

            document.add(maHDLabel);
            document.add(ngayTaoLabel);
            document.add(gioTaoLabel);
            document.add(banLabel);

            // Bảng sản phẩm
            PdfPTable table = new PdfPTable(4); // 4 cột: Món ăn, Số lượng, Đơn giá, Tổng
            table.setWidthPercentage(100);
            table.setSpacingBefore(20);

            table.addCell(createCell("Món ăn", font, Element.ALIGN_CENTER));
            table.addCell(createCell("SL", font, Element.ALIGN_CENTER));
            table.addCell(createCell("Đơn giá", font, Element.ALIGN_CENTER));
            table.addCell(createCell("Tổng", font, Element.ALIGN_CENTER));

            double tongTien = 0;
            List<ChiTietHoaDon> chiTietHoaDons = hoaDon.getChiTietHoaDon();
            for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
                table.addCell(createCell(chiTiet.getMonAn(), font, Element.ALIGN_LEFT));
                table.addCell(createCell(String.valueOf(chiTiet.getSoLuong()), font, Element.ALIGN_CENTER));
                table.addCell(createCell(String.format("%.2f", chiTiet.getDonGia()), font, Element.ALIGN_RIGHT));
                table.addCell(createCell(String.format("%.2f", chiTiet.getSoLuong() * chiTiet.getDonGia()), font, Element.ALIGN_RIGHT));

                tongTien += chiTiet.getSoLuong() * chiTiet.getDonGia();
            }

            document.add(table);

            // Thêm tổng tiền
            Paragraph totalLabel = new Paragraph("Tổng tiền: " + String.format("%.2f", tongTien) + " VND", new Font(bf, 16, Font.BOLD));
            totalLabel.setAlignment(Element.ALIGN_RIGHT);
            totalLabel.setSpacingBefore(10);
            totalLabel.setSpacingAfter(10);
            totalLabel.setFont(new Font(bf, 16, Font.BOLD));
            document.add(totalLabel);

            // Cảm ơn khách hàng
            Paragraph thankYouLabel = new Paragraph("CẢM ƠN QUÝ KHÁCH!", new Font(bf, 16, Font.BOLD));
            thankYouLabel.setAlignment(Element.ALIGN_CENTER);
            thankYouLabel.setSpacingBefore(10);
            document.add(thankYouLabel);

            // Đóng tài liệu
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xuất hóa đơn: " + e.getMessage());
        }
    }

    // Phương thức tiện ích để tạo ô cho bảng
    private PdfPCell createCell(String content, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5);
        return cell;
    }
}
