package com.wasupstudio.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScriptEnddingDTO {

    private Integer scriptDetilId;

    private Integer scriptId;

    private Integer advisoryTime;

    private Integer period;

    private String description;

    private String todayScript;

    private List<String> additionalInfo;

    private List<ParentConfiglDTO> parentConfigs;

    private List<StudentConfigDTO> studentConfigs;

}
