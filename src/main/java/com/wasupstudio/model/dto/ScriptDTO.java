package com.wasupstudio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 劇本資料表
 * @TableName script
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScriptDTO implements Serializable {
    private Integer scriptId;

    private String title;

    private String author;

    private String description;

    private Date createTime;

    private Date endTime;

    private Date updateTime;

    private Integer status;

}

