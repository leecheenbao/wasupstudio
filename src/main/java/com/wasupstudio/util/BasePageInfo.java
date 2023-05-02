package com.wasupstudio.util;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasePageInfo<T> implements Serializable {

    private static final long serialVersionUID = -1560330975871344112L;

    protected List<T> list;

    protected long total;

}