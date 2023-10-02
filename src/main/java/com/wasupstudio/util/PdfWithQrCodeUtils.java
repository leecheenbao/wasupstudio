package com.wasupstudio.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PdfWithQrCodeUtils {

    String qrCodeContent = "https://www.example.com";
    String outputFilePath = "file/output.pdf";

    public static void main(String[] args) throws IOException, WriterException {
        String qrCodeContent = "https://www.example.com";
        String gcsUrl = "https://storage.googleapis.com/wasupstudio-bucket/1695690624270.pdf";
        String localFilePath = "file/output.pdf";
//        downloadFileFromGCS(gcsUrl, localFilePath);
//        downloadFileFromGCS(gcsUrl, localFilePath);
//        convertPDFToJavaFile(gcsUrl);
        mixPdfAndQrCode(qrCodeContent, gcsUrl, localFilePath);

    }


    public static void mixPdfAndQrCode(String qrCodeContent, String gcsUrl, String outputFilePath) {
        try {
            int qrCodeSize = 98;
            // Create QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, qrCodeSize-40, qrCodeSize-40, getQRCodeHints());
            BufferedImage qrCodeImage = createQRCodeImage(bitMatrix);

            log.info("PDF下載 qrCodeContent:{}, gcsUrl:{}, outputFilePath:{}",qrCodeContent ,gcsUrl ,outputFilePath);
            File file = convertPDFToJavaFile(gcsUrl);
            // Load PDF file
            PDDocument document = PDDocument.load(file);
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
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("處理 PDF 檔案時出現 IO 錯誤。");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("處理 PDF 檔案時出現未知錯誤。");
        }
    }

    public static File convertPDFToJavaFile(String gcsUrl) throws IOException {
        URL url = new URL(gcsUrl);
        try (InputStream in = url.openStream()) {
            File pdfFile = File.createTempFile("temp_pdf", ".pdf");

            // 使用 Java 的 NIO 複製 InputStream 到 File
            Files.copy(in, pdfFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return pdfFile;
        }
    }

    public static void downloadFileFromGCS(String gcsUrl, String localFilePath) throws IOException {
        URL url = new URL(gcsUrl);
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream out = new FileOutputStream(localFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("File downloaded successfully to: " + localFilePath);
        }
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
        contentStream.drawImage(qrCodePDImageXObject, (int) (pageWidth - qrCodeSize - 5), (int) (pageHeight - qrCodeSize - 5), qrCodeSize, qrCodeSize);
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