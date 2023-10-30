package com.wasupstudio.util;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.enums.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;


@Slf4j
public class FileUtils {


    public static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB;
    public static final long MAX_VIDEO_SIZE = 1024 * 1024 * 1024; // 1G;
    public static final long MAX_PDF_SIZE = 5 * 1024 * 1024; // 5M;

    // 定義有效的影音文件副檔名
    private static final String[] VALID_VIDEO_TYPES = {"mp4", "avi", "mkv", "wmv"};
    // 定義有效的圖片文件副檔名
    private static final String[] VALID_IMAGE_TYPES = {"jpg", "png", "gif", "jpeg", "bmp", "gif"};
    // 定義有效的文件副檔名
    private static final String[] VALID_DOCS_TYPES = {"doc", "txt", "docx", "ppt", "pptx"};

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
            case ProjectConstant.FileType.PDF: {
                return file.getSize() >= MAX_PDF_SIZE;
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
            return FileTypeEnum.VIDEO.getType();
        } else if (isImageFile(fileExtension, VALID_IMAGE_TYPES)) {
            return FileTypeEnum.IMAGE.getType();
        } else if (isPdfFile(fileExtension, VALID_DOCS_TYPES)) {
            return FileTypeEnum.DOCS.getType();
        } else if (isPdfFile(fileExtension, VALID_PDF_TYPES)) {
            return FileTypeEnum.PDF.getType();
        }
        return FileTypeEnum.PDF.getType();
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
}


