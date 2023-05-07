package com.wasupstudio.service.Impl;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.enums.FileTypeEnum;
import com.wasupstudio.service.FileService;
import com.wasupstudio.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

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

    private static final String VALID_IMAGE_TYPES = "jpg,jpeg,png,bmp,gif,webp";
    private static final String VALID_VIDEO_TYPES = "avi,mp4,mpg";

    private static final String UPLOAD_DIRECTORY = ProjectConstant.FilePath.MAINPATH;


    @Override
    public String saveFile(MultipartFile file,String type) {
        String originalFilename = file.getName();
        String fileName = System.currentTimeMillis() + "_" + originalFilename;
        FilenameUtils.getExtension(file.getOriginalFilename());
        // 創建上傳目錄
        String filePath = UPLOAD_DIRECTORY + FileTypeEnum.getEnum(type) + fileName;

        // 創建目標目錄（如果不存在）
        File directory = new File(UPLOAD_DIRECTORY + FileTypeEnum.getEnum(type));
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destFile = new File(filePath);
        destFile.getParentFile().mkdirs();
        //把瀏覽器上傳的檔案複製到希望的位置
//        file.transferTo(destFile);
//        file.transferTo(filePath);

        // 返回上傳後的文件路徑
        return filePath;
    }

    @Override
    public boolean removeFile(String id) {
        return false;
    }


    public String uploadFile(Integer indexR, String module, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String os = System.getProperty("os.name");

        File destFile = new File(UPLOAD_DIRECTORY);
        destFile.getParentFile().mkdirs();
        //把瀏覽器上傳的檔案複製到希望的位置
        file.transferTo(destFile);
        String finallPath = UPLOAD_DIRECTORY + File.separator + module + File.separator ;
        return finallPath;
    }

    public void deleteFile(Integer indexR,String module) {
        String fileName = String.valueOf(indexR);
        String filePath = UPLOAD_DIRECTORY + File.separator + module + File.separator + fileName;
        File file = new File(filePath + File.separator);
        file.delete();
    }

    public static void main(String[] args) {
        String fileName = "text.txt";
        System.out.println(UPLOAD_DIRECTORY + ProjectConstant.FilePath.PDF + fileName);
    }
}

