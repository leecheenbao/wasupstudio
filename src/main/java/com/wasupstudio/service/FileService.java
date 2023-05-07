package com.wasupstudio.service;


import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(MultipartFile file, String type);

    boolean removeFile(String id);

}
