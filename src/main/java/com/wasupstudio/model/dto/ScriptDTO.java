package com.wasupstudio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    private Integer scriptPeriod;

    private List<String> goal;

    private List<String> tips;

    private List<String> preamble;

    private Date updateTime;

    private Integer status;

}

