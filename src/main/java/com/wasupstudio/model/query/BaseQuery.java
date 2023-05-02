package com.wasupstudio.model.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseQuery implements Serializable {


    private static final long serialVersionUID = -7853311936256880540L;

    private int page = 1;

    private int size = 10;
    // 系统类型
    private Integer os_type = 0;

    private String orderBy;

    private String version;

    private String device_id;

    private String ip;

    private String url;

}