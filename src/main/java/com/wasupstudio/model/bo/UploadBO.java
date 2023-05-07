package com.wasupstudio.model.bo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UploadBO {

    @NotNull(message = "圖片寬度不能為空")
    private Integer width;

    @NotNull(message = "圖片長度不能為空")
    private Integer height;

    private Long size;

    private List<String> imageType;

    @NotNull(message = "必須上傳圖片檔")
    private MultipartFile file;
}

