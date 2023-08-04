package com.wasupstudio.model.query;

import com.wasupstudio.model.dto.MediaDTO;
import com.wasupstudio.model.dto.ParentConfiglDTO;
import com.wasupstudio.model.dto.ScriptDetailDTO;
import com.wasupstudio.model.entity.ScriptDetailEntity;
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
public class ScriptQuery implements Serializable {
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

    private List<MediaDTO> mediaDTO;

    private List<ParentConfiglDTO> parentConfig;

    private List<ScriptDetailDTO> scriptDetail;

}

