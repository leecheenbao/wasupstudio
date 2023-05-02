package com.wasupstudio.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PdfWithQrCodeUtils {

    String qrCodeContent = "https://www.example.com";
    String outputFilePath = "file/output.pdf";

    public static void main(String[] args) throws IOException, WriterException {
        String pdfFilePath = "file/input.pdf";
        String qrCodeContent = "https://www.example.com";
        int qrCodeSize = 100;
        String outputFilePath = "file/output.pdf";

        // Create QR Code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, getQRCodeHints());
        BufferedImage qrCodeImage = createQRCodeImage(bitMatrix);

        // Load PDF file
        PDDocument document = PDDocument.load(new File(pdfFilePath));
        PDPage page = document.getPage(0); // Get the first page

        // Create an image object from the QR Code
        PDImageXObject qrCodePDImageXObject = PDImageXObject.createFromByteArray(document, toByteArray(qrCodeImage), "QR Code");

        // Get the width and height of the PDF page
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.drawImage(qrCodePDImageXObject, (int) (pageWidth - qrCodeSize - 25), (int) (pageHeight - qrCodeSize - 15), qrCodeSize, qrCodeSize);
        contentStream.close();

        // Save the modified PDF document
        document.save(outputFilePath);
        document.close();
    }

    private static BufferedImage createQRCodeImage(BitMatrix bitMatrix) {
        int qrCodeWidth = bitMatrix.getWidth();
        int qrCodeHeight = bitMatrix.getHeight();
        BufferedImage qrCodeImage = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < qrCodeWidth; x++) {
            for (int y = 0; y < qrCodeHeight; y++) {
                qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }
        }
        return qrCodeImage;
    }

    public String pdfMixQrCode(String pdfFilePath) throws IOException, WriterException {
//        String pdfFilePath = "file/input.pdf";
        int qrCodeSize = 100;

        // Create QR Code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, getQRCodeHints());
        BufferedImage qrCodeImage = createQRCodeImage(bitMatrix);

        // Load PDF file
        PDDocument document = PDDocument.load(new File(pdfFilePath));
        PDPage page = document.getPage(0); // Get the first page

        // Create an image object from the QR Code
        PDImageXObject qrCodePDImageXObject = PDImageXObject.createFromByteArray(document, toByteArray(qrCodeImage), "QR Code");

        // Get the width and height of the PDF page
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.drawImage(qrCodePDImageXObject, (int) (pageWidth - qrCodeSize - 25), (int) (pageHeight - qrCodeSize - 15), qrCodeSize, qrCodeSize);
        contentStream.close();

        // Save the modified PDF document
        document.save(outputFilePath);
        document.close();
        return outputFilePath;
    }
    private static byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        return outputStream.toByteArray();
    }

    private static Map<EncodeHintType, Object> getQRCodeHints() {
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        return hints;
    }
}