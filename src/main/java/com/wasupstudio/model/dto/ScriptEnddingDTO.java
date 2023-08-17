package com.wasupstudio.model.dto;

import com.wasupstudio.model.entity.ScriptEntity;
import lombok.Data;

import java.util.List;

@Data
public class ScriptDetailDTO {

    private Integer scriptDetilId;

    private Integer scriptId;

    private Integer advisoryTime;

    private Integer period;

    private String description;

    private String todayScript;

    private List<String> additionalInfo;

    private String teachingUrl;

    private String stuContent;
    private String parContent;

    private List<ParentConfiglDTO> parentConfigs;

    private List<StudentConfigDTO> studentConfigs;

}
