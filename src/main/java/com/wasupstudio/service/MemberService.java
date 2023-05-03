package com.wasupstudio.service;

import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.util.BasePageInfo;

import java.util.Map;

public interface MemberService {

    String save (MemberDTO memberDTO);

    MemberEntity findOne(Integer id);

    MemberEntity getAdminByEmail(String name);

    BasePageInfo findAllData();

    void update(MemberDTO memberDTO);

    Map<String, Object> login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery);
}
