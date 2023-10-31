package com.wasupstudio.model.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRecordQueryDTO {

    private String startDate;
    private String endDate;
}
