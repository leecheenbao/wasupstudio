package com.wasupstudio.model.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AdminLoginLogQuery extends BaseQuery {

    private static final long serialVersionUID = -5701374280749572037L;

    private String userAgent;

    private String ip;

    private String url;

    private String address;


}