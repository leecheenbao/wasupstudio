package com.wasupstudio.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 劇本結局統計
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResultQuery implements Serializable {
    private Integer taskId;
    private Integer scriptId;
    private String title;
    private String stuContent;
    private String parContent;
    private BigDecimal orderlyTotal = BigDecimal.valueOf(0);
    private BigDecimal relationTotal = BigDecimal.valueOf(0);
}

