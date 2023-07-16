package com.wasupstudio.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 任務資料表
 * @TableName task
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_task")
public class TaskEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "script_id", nullable = false)
    private Integer scriptId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "estimated_participants", nullable = false)
    private Integer estimatedParticipants;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", nullable = false, updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "learning", nullable = false)
    private Integer learning;

}

