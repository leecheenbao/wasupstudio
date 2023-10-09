package com.wasupstudio.service;

import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.query.AgeDistributionsQuery;
import com.wasupstudio.model.query.CategoryQuery;

public interface MemberService {

    String save (MemberDTO memberDTO);

    MemberEntity findOne(Integer id);

    MemberEntity getAdminByEmail(String email);

    MemberDTO findByVerificationCode(String verificationCode);

    BasePageInfo<MemberEntity> findAllData();

    BasePageInfo<MemberEntity> findLoginFor7Day();

    AgeDistributionsQuery findAgeDistributions();

    CategoryQuery findCategory();

    void update(MemberDTO memberDTO);

    void updatePwd(MemberDTO memberDTO);

    String login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery);

    String login(String mail);


}
