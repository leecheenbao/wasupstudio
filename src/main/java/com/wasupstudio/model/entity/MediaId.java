package com.wasupstudio.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MediaId implements Serializable {

    private Integer scriptId;
    private String description;

}
