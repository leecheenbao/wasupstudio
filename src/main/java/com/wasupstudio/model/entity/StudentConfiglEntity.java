package com.wasupstudio.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wa_script_student_config")
public class StudentConfiglEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "script_detail_id")
    private ScriptEntity scriptDetailId;

    @Column(name = "stu_Description")
    private String stuDescription;

    @Column(name = "stu_orderly")
    private Integer stuOrderly;

    @Column(name = "stu_relation")
    private Integer stuRelation;

}
