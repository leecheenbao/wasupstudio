package com.wasupstudio.model.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResultEndingQuery implements Serializable {

    private String result;

    private Integer count;
}

