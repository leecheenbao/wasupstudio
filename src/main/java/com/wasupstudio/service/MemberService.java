package com.wasupstudio.service;

import com.google.gson.JsonObject;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.util.BasePageInfo;

public interface MemberService {

    void save (MemberDTO memberDTO);

    MemberEntity findOne(Integer id);

    MemberEntity getAdminByEmail(String name);

    BasePageInfo findAllData();

    void update(MemberDTO memberDTO);

    JsonObject login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery);
}
