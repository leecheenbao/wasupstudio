package com.wasupstudio.model.dto;

import com.wasupstudio.model.entity.ScriptEntity;
import lombok.Data;

@Data
public class ScriptDetailDTO {

    private Integer scriptDetilId;

    private ScriptEntity scriptId;

    private Integer advisoryTime;

    private String methodDescription;

    private String todayScript;

    private String additionalInfo;

    private String teachingMaterials;

}
