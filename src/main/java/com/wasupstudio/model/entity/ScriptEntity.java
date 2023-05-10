package com.wasupstudio.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 劇本資料表
 * @TableName script
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_script")
public class ScriptEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "script_id")
    private Integer scriptId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @Column(name = "period", nullable = false)
    private Integer period;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    @Column(name = "status")
    private Integer status;

}

