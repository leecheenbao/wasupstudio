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

    @Column(name = "description")
    private String description;

    @Column(name = "orderly")
    private Integer orderly;

    @Column(name = "relation")
    private Integer relation;

}
