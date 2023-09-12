package com.wasupstudio.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 學習單選項表
 * @TableName wa_script_question
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_script_question")
public class ScriptQuestionEntity implements Serializable {
    @Id
    @Column(name = "question_Id", nullable = false)
    private Integer questionId;
    @Column(name = "task_id", nullable = false)
    private Integer taskId;
    @Column(name = "script_id")
    private Integer scriptId;
    @Column(name = "period")
    private Integer period;
    @Column(name = "par_ans", nullable = false)
    private String parAns;
    @Column(name = "stu_ans", nullable = false)
    private String stuAns;

}

