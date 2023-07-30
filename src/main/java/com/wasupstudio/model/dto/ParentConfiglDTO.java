package com.wasupstudio.model.dto;

import com.wasupstudio.model.entity.ScriptEntity;
import lombok.Data;

import javax.persistence.*;

@Data
public class ParentConfiglDTO {

    private Integer id;

    private Integer scriptDetailId;

    private String parDescription;

    private Integer parOrderly;

    private Integer parRelation;

}
