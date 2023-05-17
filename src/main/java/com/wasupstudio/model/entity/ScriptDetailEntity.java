package com.wasupstudio.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wa_script_detail")
public class ScriptDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "script_detail_id")
    private Integer scriptDetailId;

    @ManyToOne
    @JoinColumn(name = "script_id")
    private ScriptEntity script;

    @Column(name = "advisory_time")
    private Integer advisoryTime;

    @Column(name = "method_description")
    private String methodDescription;

    @Column(name = "today_script")
    private String todayScript;

    @Column(name = "additional_info")
    private String additionalInfo;

    @Column(name = "teaching_materials")
    private String teachingMaterials;

}