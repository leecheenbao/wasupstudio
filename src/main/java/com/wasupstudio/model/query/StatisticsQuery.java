package com.wasupstudio.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 統計含詳情
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsQuery<T> implements Serializable {
    private Integer id;
    private String result;
    private Integer count;

    private List<T> detail;
}

