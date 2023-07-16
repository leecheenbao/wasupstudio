package com.wasupstudio.model.dto;

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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO implements Serializable {

    private Integer taskId;

    private String taskName;

    private String description;

    private Integer priority;

    private Integer scriptId;

    private Integer estimatedParticipants;

    private Date createTime;

    private Date endTime;

    private Integer status;

    private String author;

    private Integer memberId;

    private Integer learning;

}

