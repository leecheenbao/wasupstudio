package com.wasupstudio.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 劇本結局統計
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EndingReportQuery implements Serializable {
    private Integer scriptId;

    private List<ResultEndingQuery> result;

    private List<QuestionReportV2Query> detail;
}

