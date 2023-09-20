package com.wasupstudio.util;

import com.wasupstudio.constant.ProjectConstant;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;


public class FileUtils {


    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB;
    public static final long MAX_VIDEO_SIZE = 1024 * 1024 * 1024; // 1G;
    // 定義有效的影音文件副檔名
    private static final String[] VALID_VIDEO_TYPES = {"mp4", "avi", "mkv", "wmv"};
    // 定義有效的圖片文件副檔名
    private static final String[] VALID_IMAGE_TYPES = {"jpg", "png", "gif", "jpeg", "bmp", "gif"};
    // 定義有效的文件副檔名
    private static final String[] VALID_DOCS_TYPES = {"doc", "txt", "docx", "ptt", "pttx"};

    private static final String[] VALID_PDF_TYPES = {"pdf"};

    public static boolean validateFileSize(MultipartFile file) {
        String mediaType = checkFileType(file.getOriginalFilename());
        switch (mediaType) {
            case ProjectConstant.FileType.DOCS: {
                return file.getSize() >= MAX_FILE_SIZE;
            }
            case ProjectConstant.FileType.IMAGE: {
                return file.getSize() >= MAX_IMAGE_SIZE;
            }
            case ProjectConstant.FileType.VIDEO: {
                return file.getSize() >= MAX_VIDEO_SIZE;
            }
        }
        return true;
    }

    public static boolean validateFileExtension(String fileName) {
        String[] allValidTypes = mergeArrays(VALID_VIDEO_TYPES, VALID_IMAGE_TYPES, VALID_DOCS_TYPES, VALID_PDF_TYPES);

        String extension = getFileExtension(fileName);
        for (String allowedExtension : allValidTypes) {
            if (allowedExtension.equalsIgnoreCase(extension)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 合併陣列
     */
    public static String[] mergeArrays(String[]... arrays) {
        int totalLength = 0;
        for (String[] array : arrays) {
            totalLength += array.length;
        }

        String[] result = new String[totalLength];
        int destPos = 0;
        for (String[] array : arrays) {
            System.arraycopy(array, 0, result, destPos, array.length);
            destPos += array.length;
        }

        return result;
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static File convertToFile(MultipartFile multipartFile) throws IOException, IOException {
        File file = new File(multipartFile.getOriginalFilename());
        Path filePath = file.toPath();

        // 使用 Files.copy 方法将 MultipartFile 的内容复制到新创建的文件中
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return file;
    }

    public static File convertToFileAlt(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());

        // 使用 FileCopyUtils 类的 copy 方法将 MultipartFile 的内容复制到新创建的文件中
        FileCopyUtils.copy(multipartFile.getBytes(), file);

        return file;
    }

    private void imageVerification(MultipartFile file) throws IOException {
        FileUtils.validateFileSize(file);
    }

    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"Byte", "KB", "MB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + units[digitGroups];
    }

    public static String checkFileType(String originFileName) {

        // 獲取檔案副檔名
        String fileExtension = getFileExtension(originFileName);

        // 判斷檔案類型
        if (isVideoFile(fileExtension, VALID_VIDEO_TYPES)) {
            return ProjectConstant.FileType.VIDEO;
        } else if (isImageFile(fileExtension, VALID_IMAGE_TYPES)) {
            return ProjectConstant.FileType.IMAGE;
        } else if (isPdfFile(fileExtension, VALID_DOCS_TYPES)) {
            return ProjectConstant.FileType.DOCS;
        } else if (isPdfFile(fileExtension, VALID_PDF_TYPES)) {
            return ProjectConstant.FileType.PDF;
        }
        return ProjectConstant.FileType.UNKNOWN;
    }

    // 判斷是否為影音文件
    public static boolean isVideoFile(String fileExtension, String[] videoExtensions) {
        for (String extension : videoExtensions) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    // 判斷是否為圖片文件
    public static boolean isImageFile(String fileExtension, String[] imageExtensions) {
        for (String extension : imageExtensions) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPdfFile(String fileExtension, String[] PDFExtensions) {
        for (String extension : PDFExtensions) {
            if (extension.equalsIgnoreCase(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "/Users/liqingbao/Downloads"; // 替換為要上傳的檔案路徑
        String fileName = "/test.png"; // 替換為要上傳的檔案名稱
        String file = filePath + fileName;

        // 上传文件
        String bucketName = "fongff-bucket";
        String objectName = "input.pdf";

//        String url = uploadFile(file, objectName, bucketName);
//        System.out.println(url);

    }
}


