package com.wasupstudio.model.dto;

import lombok.Data;

@Data
public class ScriptEndingDTO {

    private Integer scriptId;
    private Integer advisoryTime;
    private String endingDescription;

    private String endingOne;
    private String endingTwo;
    private String endingThree;
    private String endingFour;

    private Integer orderlyOne;
    private Integer orderlyTwo;
    private Integer orderlyThree;
    private Integer orderlyFour;

    private Integer relationOne;
    private Integer relationTwo;
    private Integer relationThree;
    private Integer relationFour;
}
