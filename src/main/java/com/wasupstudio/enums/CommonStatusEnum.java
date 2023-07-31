package com.wasupstudio.enums;

import lombok.Getter;

@Getter
public enum CommonStatusEnum {

    DISABLE(0,"未啟用"),
    ENABLE(1,"啓用"),
    DELETED(2,"刪除");

    Integer type;
    String name;

    CommonStatusEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

}
