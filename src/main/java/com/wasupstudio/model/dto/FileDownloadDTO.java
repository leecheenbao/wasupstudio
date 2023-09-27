package com.wasupstudio.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDownloadDTO {

    Integer scriptId;

    String sheet;

    String media;

}
