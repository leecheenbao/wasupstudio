package com.wasupstudio.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 媒體資料表
 * @TableName media
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_media")
public class MediaEntity implements Serializable {

    @Column(name = "script_id")
    private Integer scriptId;
    @Id
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "filename")
    private String filename;

}


