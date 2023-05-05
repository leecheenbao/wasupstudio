package com.wasupstudio.model.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LicenseDTO {
    Integer id;
    String customerName;
    String customerEmail;
    Integer activated;
    Date startTime;
    Date endTime;
}
