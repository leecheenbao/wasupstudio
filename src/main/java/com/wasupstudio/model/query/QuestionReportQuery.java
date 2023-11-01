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
public class QuestionReportQuery implements Serializable {
    private Integer taskId;
    private Integer scriptId;
    private String title;
    private Integer period;
    private String stuContent;
    private String stuDescription;
    private Integer stuOrderly;
    private Integer stuRelation;
    private String parContent;
    private String parDescription;
    private Integer parOrderly;
    private Integer parRelation;
}

