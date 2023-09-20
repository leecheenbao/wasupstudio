package com.wasupstudio.service;

import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.model.BasePageInfo;

public interface MemberService {

    String save (MemberDTO memberDTO);

    MemberEntity findOne(Integer id);

    MemberEntity getAdminByEmail(String email);

    MemberDTO findByVerificationCode(String verificationCode);

    BasePageInfo findAllData();

    void update(MemberDTO memberDTO);

    void updatePwd(MemberDTO memberDTO);

    String login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery);

    String login(String mail);
}
