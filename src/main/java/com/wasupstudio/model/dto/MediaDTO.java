package com.wasupstudio.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MediaDTO {
//    private int mediaId;
    private Integer scriptId;
    private String mediaType;
    private String filePath;
    private Date createTime;
    private Date updateTime;
    private String description;
    private String fileExtension;
}
