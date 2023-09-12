package com.wasupstudio.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 學習單選項表
 * @TableName wa_script_question_option
 */
@Data
public class ScriptQuestionDTO implements Serializable {
    private Integer questionId;
    private Integer taskId;
    private Integer scriptId;
    private Integer period;
    private String parAns;
    private String stuAns;

}

