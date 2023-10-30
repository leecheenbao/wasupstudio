package com.wasupstudio.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wasupstudio.enums.FileTypeEnum;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.wasupstudio.constant.ProjectConstant.FilePath.BUCKET_NAME;

@Slf4j
public class PdfWithQrCodeUtils {

    public static String mixPdfAndQrCode(String qrCodeContent, String pdfUrl) {
        String filePath = "";
        try {
            int qrCodeSize = 98;

            // Create QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeContent, BarcodeFormat.QR_CODE, qrCodeSize - 40, qrCodeSize - 40, getQRCodeHints());
            BufferedImage qrCodeImage = createQRCodeImage(bitMatrix);

            File file = convertPDFToJavaFile(pdfUrl);
            // Load PDF file
            InputStream inputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            PDDocument document = PDDocument.load(bufferedInputStream);

            PDPage page = document.getPage(0); // Get the first page

            // Create an image object from the QR Code
            PDImageXObject qrCodePDImageXObject = PDImageXObject.createFromByteArray(document, toByteArray(qrCodeImage), "QR Code");

            // Get the width and height of the PDF page
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.drawImage(qrCodePDImageXObject, (int) (pageWidth - qrCodeSize - 25), (int) (pageHeight - qrCodeSize - 15), qrCodeSize, qrCodeSize);
            }

            // Save the modified PDF document to a ByteArrayOutputStream
            try (ByteArrayOutputStream modifiedPdfOutputStream = new ByteArrayOutputStream()) {
                document.save(modifiedPdfOutputStream);

                // Upload the modified PDF to GCS
                Storage storage = createStorage();
                Date date = new Date();
                BlobId blobId = BlobId.of(BUCKET_NAME, FileTypeEnum.CACHE_PDF.getPath() + date.getTime());

                // Set the retention policy for the uploaded object
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                        .setContentType(FileTypeEnum.CACHE_PDF.getType())
                        .build();

                Blob blob = storage.create(blobInfo, modifiedPdfOutputStream.toByteArray());
                blob.getBucket();
                filePath = blob.getMediaLink();
            }
            log.info("PDF下載 qrCodeContent:{}, gcsUrl:{}", qrCodeContent, pdfUrl);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("處理 PDF 檔案時出現 IO 錯誤。");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("處理 PDF 檔案時出現未知錯誤。");
        }

        return filePath;
    }

    public static Storage createStorage() throws IOException {
        //讀取本地存儲的服務賬號的json密鑰，拿到該服務賬號的權限
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("./gcloud-key.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
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