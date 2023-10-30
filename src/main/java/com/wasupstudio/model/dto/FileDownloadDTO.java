package com.wasupstudio.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDownloadDTO {

//    Integer scriptId;

    Integer taskId;
    String sheet;

    String media;

}
