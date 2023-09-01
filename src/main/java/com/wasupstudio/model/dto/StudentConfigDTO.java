package com.wasupstudio.model.dto;

import com.wasupstudio.model.entity.ScriptEntity;
import lombok.Data;

import javax.persistence.*;

@Data
public class StudentConfigDTO {

    private Integer id;

    private Integer scriptDetailId;

    private String stuDescription;

    private Integer stuOrderly;

    private Integer stuRelation;

    private Integer score;

}
