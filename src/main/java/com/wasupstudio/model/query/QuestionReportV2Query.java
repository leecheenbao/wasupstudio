package com.wasupstudio.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 劇本結局統計
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReportV2Query implements Serializable {
    private Integer taskId;
    private String taskName;
    private Integer scriptId;
    private String title;
    private Integer orderly;
    private Integer relation;
    private String result;
}

