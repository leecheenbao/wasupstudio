package com.wasupstudio.service;

import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.util.BasePageInfo;

public interface MemberService {

    String save (MemberDTO memberDTO);

    MemberEntity findOne(Integer id);

    MemberEntity getAdminByEmail(String name);

    BasePageInfo findAllData();

    void update(MemberDTO memberDTO);

    String login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery);

    String login(String mail);
}
