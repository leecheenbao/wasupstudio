package com.wasupstudio.service;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    String uploadFile(byte[] file, String fileName, String mediaType) throws IOException;

    boolean removeFile(String filePath) throws IOException;

}
