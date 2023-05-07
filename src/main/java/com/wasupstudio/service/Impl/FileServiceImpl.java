package com.wasupstudio.service.Impl;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.service.FileService;
import com.wasupstudio.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

import static com.wasupstudio.enums.ResultCode.UPLOAD_FORMAT_ERROR;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileService fileService;
    @Value("${img.address}")
    private String imgAddress;

    @Value("${img.maxSize}")
    private long maxSize;

    @Value("${img.imgType}")
    private String imgType;

    @Value("${img.port}")
    private String imgPort;

    private static final long MAX_IMAGE_SIZE = 20971520;
    private static final long MAX_VIDEO_SIZE = 31457280;


    private static final String UPLOAD_DIRECTORY = ProjectConstant.FilePath.MAINPATH;

    public static void main(String[] args) {
        System.out.println(File.separator);
        System.out.println(File.pathSeparator);
        System.out.println(File.separatorChar);
    }
    public String saveFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        String fileName = System.currentTimeMillis()
                + "." + FileUtils.getFileExtension(originalFilename);

        // 創建上傳目錄
        String filePath = UPLOAD_DIRECTORY
                + FileUtils.checkFileType(originalFilename)
                + File.separator + fileName;

        // 創建目標目錄（如果不存在）
        File directory = new File(UPLOAD_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destFile = new File(filePath);
        destFile.getParentFile().mkdirs();

        // 將瀏覽器上傳的文件複製到目標位置
        file.transferTo(destFile);

        // 返回上傳後的文件路徑
        return filePath;
    }

    @Override
    public boolean removeFile(String filePath) {
        File file = new File(filePath);
        file.delete();
        return false;
    }


}


