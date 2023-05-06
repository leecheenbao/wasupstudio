package com.wasupstudio.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MediaDTO {
    private int mediaId;
    private int scriptId;
    private String mediaType;
    private String filePath;
    private Date createTime;
    private Date updateTime;
}
