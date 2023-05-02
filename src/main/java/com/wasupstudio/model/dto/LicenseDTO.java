package com.wasupstudio.model.dto;

import lombok.*;

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
    String startTime;
    String endTime;
}
