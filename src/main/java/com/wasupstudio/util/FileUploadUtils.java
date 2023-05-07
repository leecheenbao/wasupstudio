package com.wasupstudio.util;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

public class FileUploadUtils {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB
    private static final String[] ALLOWED_EXTENSIONS = { "jpg", "jpeg", "png" };

    public static boolean validateFileSize(File file) {
        return file.length() <= MAX_FILE_SIZE;
    }

    public static boolean validateFileExtension(String fileName) {
        String extension = getFileExtension(fileName);
        for (String allowedExtension : ALLOWED_EXTENSIONS) {
            if (allowedExtension.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    private static String getFileExtension(String fileName) {
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
        FileUploadUtils.validateFileSize(FileUploadUtils.convertToFile(file));
    }

    private String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"Byte", "KB", "MB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + units[digitGroups];
    }
}

