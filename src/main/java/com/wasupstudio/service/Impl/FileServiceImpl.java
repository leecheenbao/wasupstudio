package com.wasupstudio.service.Impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.common.collect.Lists;
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final String BUCKET_NAME = "fongff-bucket";

    private static final String UPLOAD_DIRECTORY = ProjectConstant.FilePath.MAINPATH;

    public String uploadFile(byte[] file, String fileName, String mediaType) throws IOException {

        //創建服務帳號對應的操作對象
        Storage storage = createStorage();

        //上傳文件圖片到指定的存儲桶中
        BlobId blobId=BlobId.of(BUCKET_NAME,fileName);
        BlobInfo blobInfo=BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file);

        blob.toBuilder().setContentType(mediaType).build().update();

        //返回公開訪問的地址
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName;

    }
    @Override
    public boolean removeFile(String fileName) throws IOException {
        //創建服務帳號對應的操作對象
        Storage storage = createStorage();
        storage.delete(BUCKET_NAME, fileName);
        return false;
    }

    public Storage createStorage() throws IOException {
        //讀取本地存儲的服務賬號的json密鑰，拿到該服務賬號的權限
        GoogleCredentials credentials= GoogleCredentials.fromStream(new FileInputStream("./gcloud-key.json"))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
    public static void main(String[] args) {
        String fileName = "https://storage.googleapis.com/fongff-bucket/1685174020617.png";
        String[] str = fileName.split(File.separator);
        String lastByte = str[str.length - 1];
        System.out.println(lastByte);
    }
}


