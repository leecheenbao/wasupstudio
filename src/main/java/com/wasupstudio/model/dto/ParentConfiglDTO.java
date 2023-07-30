package com.wasupstudio.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "wa_script_parent_config")
public class ParentConfiglEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "script_detail_id")
    private ScriptEntity scriptDetailId;

    @Column(name = "par_description")
    private String parDescription;

    @Column(name = "par_orderly")
    private Integer parOrderly;

    @Column(name = "par_relation")
    private Integer parRelation;

}
